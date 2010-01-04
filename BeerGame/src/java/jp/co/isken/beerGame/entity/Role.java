package jp.co.isken.beerGame.entity;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import jp.rough_diamond.commons.di.DIContainerFactory;
import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.commons.service.annotation.PostPersist;
import jp.rough_diamond.commons.service.annotation.PrePersist;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ロールのHibernateマッピングクラス
 **/
public class Role extends jp.co.isken.beerGame.entity.base.BaseRole {
	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(Role.class);

	public Long getLastWeek(String transactionType) {
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), this));
		extractor.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), transactionType));
		extractor.addOrder(Order.desc(new Property(TradeTransaction.WEEK)));
		extractor.setLimit(1);
		List<TradeTransaction> list = BasicService.getService().findByExtractor(extractor);
		return (list.size() == 0) ? 0L : list.get(0).getWeek();
	}

	public Long getCurrentWeek(String transactionType) {
		return this.getLastWeek(transactionType) + 1L;
	}

	public void send(TransactionType type, String message) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection connection = factory.createQueueConnection();
		QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		log.info("メッセージを送信します。Queue: " + this.getQueueName(type));
		Queue queue = session.createQueue(this.getQueueName(type));
		QueueSender sender = session.createSender(queue);
		Message msg = session.createTextMessage(message);
		sender.send(msg);
		sender.close();
		session.close();
		connection.close();
	}

	public String receive(TransactionType type) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection connection = factory.createQueueConnection();
		QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		log.info("メッセージを受信します。Queue: " + this.getQueueName(type));
		Queue queue = session.createQueue(this.getQueueName(type));
		QueueReceiver receiver = session.createReceiver(queue);
		connection.start();
		TextMessage ret = (TextMessage) receiver.receive();
		receiver.close();
		session.close();
		connection.close();
		if (ret != null) {
			log.info("メッセージを受信しました。内容: " + ret.getText());
		}
		return (ret == null) ? null : ret.getText();
	}

	private String getQueueName(TransactionType type) {
		return this.getPlayer().getGame().getId().toString() + "." + type.getParty(this).getId() + "." + type.getQueue();
	}

	public void order(Long amount) throws VersionUnmuchException, MessagesIncludingException, JMSException {
		this.order(amount, new Long(this.getCurrentWeek(TransactionType.発注.name())));
	}

	public void order(Long amount, Long week) throws VersionUnmuchException, MessagesIncludingException, JMSException {
		log.info("発注します: ロール名 = " + this.getName());
		this.createTransaction(TransactionType.発注, amount, week);
		this.send(TransactionType.発注, amount.toString());
		if (this.getName().equals(RoleType.メーカ.name())) {
			Role factory = this.getUpper();
			factory.send(TransactionType.出荷, amount.toString());
		}
		log.info("発注しました: ロール名 = " + this.getName());
	}

	public void acceptOrder() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		log.info("受注します: ロール名 = " + this.getName());
		this.createTransaction(TransactionType.受注, this.getOrderAmount());
		log.info("受注しました: ロール名 = " + this.getName());
	}

	// TODO 2010/01/03 imai & yoshioka DIContainerから配列を取得できんかったとです。井上夫妻助けて！
	public Long getOrderAmount() throws JMSException {
		if (this.getName().equals(RoleType.小売り.name())) {
			String key = "wholeSallerAcceptOrderAmount" + this.getCurrentWeek(TransactionType.受注.name()).toString();
			return DIContainerFactory.getDIContainer().getObject(Long.class, key);
		} else {
			return Long.parseLong(this.receive(TransactionType.受注));
		}
	}

	public void inbound() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		log.info("入荷します: ロール名 = " + this.getName());
		this.createTransaction(TransactionType.入荷, this.getInboundAmount());
		log.info("入荷しました: ロール名 = " + this.getName());
	}

	public Long getInboundAmount() throws JMSException {
		return Long.parseLong(this.receive(TransactionType.入荷));
	}

	public void outbound() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		log.info("出荷します: ロール名 = " + this.getName());
		Long amount = this.getOutboundAmount();
		this.createTransaction(TransactionType.出荷, amount);
		this.send(TransactionType.出荷, amount.toString());
		log.info("出荷しました: ロール名 = " + this.getName());
	}

	public Long getOutboundAmount() {
		Long lastWeek = this.getLastWeek(TransactionType.出荷.name());
		Long acceptAmount = this.getTransaction(TransactionType.受注, lastWeek).getAmount();
		Long remain = TradeTransaction.calcAmountRemain(lastWeek, this);
		Long sum = acceptAmount + remain;
		Long stock = TradeTransaction.calcAmountStock(this.getCurrentWeek(TransactionType.出荷.name()), this);
		return (sum <= stock) ? sum : stock;
	}

	void createTransaction(TransactionType type, Long amount) throws VersionUnmuchException, MessagesIncludingException, JMSException {
		this.createTransaction(type, amount, new Long(this.getCurrentWeek(type.name())));
	}

	void createTransaction(TransactionType type, Long amount, Long week) throws VersionUnmuchException, MessagesIncludingException {
		TradeTransaction transaction = new TradeTransaction();
		transaction.setAmount(amount);
		transaction.setRole(this);
		transaction.setTransactionType(type.name());
		transaction.setWeek(week);
		transaction.save();
	}

	public void disposeAllMessage() throws JMSException {
		if (RoleType.getRoleTypeByName(this.getName()).equals(RoleType.小売り) == false) {
			this.disposeMessage(TransactionType.受注);
		}
		if (RoleType.getRoleTypeByName(this.getName()).equals(RoleType.メーカ) == false) {
			this.disposeMessage(TransactionType.入荷);
		}
	}

	private void disposeMessage(TransactionType type) throws JMSException {
		while (true) {
			String ret = this.receiveNoWait(type);
			if (ret == null) {
				log.info("メッセージはありません。");
				break;
			} else {
				log.info("メッセージを破棄します。内容: " + ret);
			}
		}
	}

	private String receiveNoWait(TransactionType type) throws JMSException {
		// Connectionオブジェクトの作成
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection connection = factory.createQueueConnection();
		// セッションの作成
		QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		log.info("破棄するメッセージを受信します。Queue: " + this.getQueueName(type));
		Queue queue = session.createQueue(this.getQueueName(type));
		// MessageConsumerオブジェクトの作成（Queueと関連付け）
		QueueReceiver receiver = session.createReceiver(queue);
		connection.start();
		// メッセージの受信
		TextMessage ret = (TextMessage) receiver.receive(1);
		receiver.close();
		session.close();
		connection.close();
		return (ret == null) ? null : ret.getText();
	}

	public void initAmount() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		this.inbound();
		this.acceptOrder();
		this.outbound();
	}

	public Role getUpper() {
		return this.getRole(RoleType.getRoleTypeByName(this.getName()).getUpper());
	}

	public Role getDowner() {
		return this.getRole(RoleType.getRoleTypeByName(this.getName()).getDowner());
	}

	private Role getRole(RoleType type) {
		Extractor e = new Extractor(Role.class);
		e.add(Condition.eq(new Property(Role.PLAYER + "." + Player.GAME), this.getPlayer().getGame()));
		e.add(Condition.eq(new Property(Role.NAME), type.name()));
		List<Role> roles = BasicService.getService().findByExtractor(e);
		return (roles.size() == 0 ? null : roles.get(0));
	}

	public static Role getRole(Game game, RoleType type) {
		BasicService service = BasicService.getService();
		Extractor e = new Extractor(Role.class);
		e.add(Condition.eq(new Property(Role.PLAYER + "." + Player.GAME), game));
		e.add(Condition.eq(new Property(Role.NAME), type.name()));
		List<Role> roles = service.findByExtractor(e);
		return (roles.size() == 0 ? null : roles.get(0));
	}

	public TradeTransaction getTransaction(TransactionType type) {
		BasicService service = BasicService.getService();
		Extractor e = new Extractor(TradeTransaction.class);
		e.add(Condition.eq(new Property(TradeTransaction.ROLE), this));
		e.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), type.name()));
		e.addOrder(Order.desc(new Property(TradeTransaction.WEEK)));
		List<TradeTransaction> list = service.findByExtractor(e);
		return (list.size() == 0 ? null : list.get(0));
	}

	public TradeTransaction getTransaction(TransactionType type, Long week) {
		BasicService service = BasicService.getService();
		Extractor e = new Extractor(TradeTransaction.class);
		e.add(Condition.eq(new Property(TradeTransaction.ROLE), this));
		e.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), type.name()));
		e.add(Condition.eq(new Property(TradeTransaction.WEEK), week));
		List<TradeTransaction> list = service.findByExtractor(e);
		return (list.size() == 0 ? null : list.get(0));
	}

	@PrePersist
	public void savePlayer() throws VersionUnmuchException, MessagesIncludingException {
		this.getPlayer().save();
	}

	@PostPersist
	public void addInitialTransaction() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		this.createTransaction(TransactionType.在庫, DIContainerFactory.getDIContainer().getObject(Long.class, "initStockAmount"), 0L);
		this.createTransaction(TransactionType.入荷, DIContainerFactory.getDIContainer().getObject(Long.class, "initInboundAmount"), 0L);
		this.createTransaction(TransactionType.受注, DIContainerFactory.getDIContainer().getObject(Long.class, "initAcceptOrderAmount"), 0L);
		Long outboundAmount = DIContainerFactory.getDIContainer().getObject(Long.class, "initOutboundAmount");
		this.createTransaction(TransactionType.出荷, outboundAmount, 0L);
		this.send(TransactionType.出荷, outboundAmount.toString());
		Long orderAmount = DIContainerFactory.getDIContainer().getObject(Long.class, "initOrderAmount");
		this.createTransaction(TransactionType.発注, orderAmount, 0L);
		this.send(TransactionType.発注, orderAmount.toString());
	}

	public static List<Role> getRoles(Game game) {
		BasicService service = BasicService.getService();
		Extractor extractor = new Extractor(Role.class);	
		extractor.add(Condition.eq(new Property(Role.PLAYER + "." + Player.GAME), game));
		extractor.add(Condition.notEq(new Property(Role.NAME), "市場"));
		extractor.add(Condition.notEq(new Property(Role.NAME), "工場"));
		extractor.addOrder(Order.asc(new Property(Role.ID)));
		List<Role> roles = service.findByExtractor(extractor);
		return roles;
	}

	public boolean isOrder() {
		if (this.getName().equals(RoleType.小売り.name())) return true;
		//　前週かつ下流の発注が取得できる　＝　発注可能
		TradeTransaction t = this.getDowner().getTransaction(TransactionType.発注,this.getLastWeek(TransactionType.受注.name()));
        if (t == null){
        	return false;	
        }else{
        	return true;
        }
	}
}
