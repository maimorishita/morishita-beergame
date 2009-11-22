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

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
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
	
	public Long getWeek(String transactionType) {
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), this));
		extractor.add(Condition.eq(new Property(TradeTransaction.TRANSACTION_TYPE), transactionType));
		extractor.addOrder(Order.desc(new Property(TradeTransaction.WEEK)));
		extractor.setLimit(1);
		List<TradeTransaction> list = BasicService.getService().findByExtractor(extractor);
		return (list.size() == 0) ? 1L : list.get(0).getWeek() + 1;
	}

	public Long getCurrentWeek(String transactionType) {
		return this.getWeek(transactionType);
	}

	public void send(TransactionType type, String message) throws JMSException {
		// Connectionオブジェクトの作成
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection connection = factory.createQueueConnection();
		// セッションの作成
		QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		log.info("メッセージを送信します。Queue: " + this.getQueueName(type));
		Queue queue = session.createQueue(this.getQueueName(type));
		// MessageProducerオブジェクトの作成
		QueueSender sender = session.createSender(queue);
		// 市場から小売へのメッセージを作成
		Message msg = session.createTextMessage(message);
		// メッセージの送信
		sender.send(msg);
		sender.close();
		session.close();
		connection.close();
	}

	public String receive(TransactionType type) throws JMSException {
		// Connectionオブジェクトの作成
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection connection = factory.createQueueConnection();
		// セッションの作成
		QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		log.info("メッセージを受信します。Queue: " + this.getQueueName(type));
		Queue queue = session.createQueue(this.getQueueName(type));
		//MessageConsumerオブジェクトの作成（Queueと関連付け）
		QueueReceiver receiver = session.createReceiver(queue);
		connection.start();
		// メッセージの受信
		TextMessage ret = (TextMessage)receiver.receive(10);
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
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(amount);
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.発注.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.発注.name())));
		tradeTransaction.save();
		this.send(TransactionType.発注, amount.toString());
	}

	public void acceptOrder() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(this.getOrderCount());
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.受注.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.受注.name())));
		tradeTransaction.save();		
	}

	public Long getOrderCount() throws JMSException {
		return Long.parseLong(this.receive(TransactionType.受注));
	}

	public void inbound() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(this.getInboundCount());
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.入荷.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.入荷.name())));
		tradeTransaction.save();
	}

	public Long getInboundCount() throws JMSException {
		return Long.parseLong(this.receive(TransactionType.入荷));
	}

	public void outbound() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(this.getOutboundCount());
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.出荷.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.出荷.name())));
		tradeTransaction.save();
		this.send(TransactionType.出荷, tradeTransaction.getAmount().toString());
	}

	public Long getOutboundCount() {
		// TODO 2009/10/31 R.Y & T.I 出荷 = 受注 + 注残をやってまへん
		// 現在は、Role=9Lを使っていて、在庫 > 受注 + 注残のため、受注をそのまま返す
		return 5L;
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
		while(true) {
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
		//MessageConsumerオブジェクトの作成（Queueと関連付け）
		QueueReceiver receiver = session.createReceiver(queue);
		connection.start();
		// メッセージの受信
		TextMessage ret = (TextMessage)receiver.receive(1);
		receiver.close();
		session.close();
		connection.close();
		return (ret == null) ? null : ret.getText();
	}

	public void initAmount(long l,long m) throws VersionUnmuchException, MessagesIncludingException {
		TradeTransaction tradeTransactionStock = new TradeTransaction();
		tradeTransactionStock.setAmount(l);
		tradeTransactionStock.setRole(this);
		tradeTransactionStock.setTransactionType(TransactionType.在庫.name());
		tradeTransactionStock.setWeek(0L);
		tradeTransactionStock.save();
		
		TradeTransaction tradeTransactionOrder = new TradeTransaction();
		tradeTransactionOrder.setAmount(m);
		tradeTransactionOrder.setRole(this);
		tradeTransactionOrder.setTransactionType(TransactionType.発注.name());
		tradeTransactionOrder.setWeek(0L);
		tradeTransactionOrder.save();
	}

	public Role getUpper() {
		BasicService service = BasicService.getService();
		Extractor e = new Extractor(Role.class);
		e.add(Condition.eq(new Property(Role.PLAYER + "." + Player.GAME), this.getPlayer().getGame()));
		RoleType type = RoleType.getRoleTypeByName(this.getName());
		e.add(Condition.eq(new Property(Role.NAME), type.getUpper().name()));
		List<Role> roles = service.findByExtractor(e);
		return (roles.size() == 0 ? null : roles.get(0));
	}
	
	public Role getDowner() {
		BasicService service = BasicService.getService();
		Extractor e = new Extractor(Role.class);
		e.add(Condition.eq(new Property(Role.PLAYER + "." + Player.GAME), this.getPlayer().getGame()));
		RoleType type = RoleType.getRoleTypeByName(this.getName());
		e.add(Condition.eq(new Property(Role.NAME), type.getDowner().name()));
		List<Role> roles = service.findByExtractor(e);
		return (roles.size() == 0 ? null : roles.get(0));
	}
}
