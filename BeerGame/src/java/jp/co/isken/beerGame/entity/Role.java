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
 * ���[����Hibernate�}�b�s���O�N���X
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
		// Connection�I�u�W�F�N�g�̍쐬
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection connection = factory.createQueueConnection();
		// �Z�b�V�����̍쐬
		QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		log.info("���b�Z�[�W�𑗐M���܂��BQueue: " + this.getQueueName(type));
		Queue queue = session.createQueue(this.getQueueName(type));
		// MessageProducer�I�u�W�F�N�g�̍쐬
		QueueSender sender = session.createSender(queue);
		// �s�ꂩ�珬���ւ̃��b�Z�[�W���쐬
		Message msg = session.createTextMessage(message);
		// ���b�Z�[�W�̑��M
		sender.send(msg);
		sender.close();
		session.close();
		connection.close();
	}

	public String receive(TransactionType type) throws JMSException {
		// Connection�I�u�W�F�N�g�̍쐬
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection connection = factory.createQueueConnection();
		// �Z�b�V�����̍쐬
		QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		log.info("���b�Z�[�W����M���܂��BQueue: " + this.getQueueName(type));
		Queue queue = session.createQueue(this.getQueueName(type));
		//MessageConsumer�I�u�W�F�N�g�̍쐬�iQueue�Ɗ֘A�t���j
		QueueReceiver receiver = session.createReceiver(queue);
		connection.start();
		// ���b�Z�[�W�̎�M
		TextMessage ret = (TextMessage)receiver.receive(10);
		receiver.close();
		session.close();
		connection.close();
		if (ret != null) {
			log.info("���b�Z�[�W����M���܂����B���e: " + ret.getText());
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
		tradeTransaction.setTransactionType(TransactionType.����.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.����.name())));
		tradeTransaction.save();
		this.send(TransactionType.����, amount.toString());
	}

	public void acceptOrder() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(this.getOrderCount());
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.��.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.��.name())));
		tradeTransaction.save();		
	}

	public Long getOrderCount() throws JMSException {
		return Long.parseLong(this.receive(TransactionType.��));
	}

	public void inbound() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(this.getInboundCount());
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.����.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.����.name())));
		tradeTransaction.save();
	}

	public Long getInboundCount() throws JMSException {
		return Long.parseLong(this.receive(TransactionType.����));
	}

	public void outbound() throws VersionUnmuchException, MessagesIncludingException, JMSException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(this.getOutboundCount());
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.�o��.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.�o��.name())));
		tradeTransaction.save();
		this.send(TransactionType.�o��, tradeTransaction.getAmount().toString());
	}

	public Long getOutboundCount() {
		// TODO 2009/10/31 R.Y & T.I �o�� = �� + ���c������Ă܂ւ�
		// ���݂́ARole=9L���g���Ă��āA�݌� > �� + ���c�̂��߁A�󒍂����̂܂ܕԂ�
		return 5L;
	}

	public void disposeAllMessage() throws JMSException {
		if (RoleType.getRoleTypeByName(this.getName()).equals(RoleType.������) == false) {
			this.disposeMessage(TransactionType.��);
		}
		if (RoleType.getRoleTypeByName(this.getName()).equals(RoleType.���[�J) == false) {
			this.disposeMessage(TransactionType.����);
		}
	}
	
	private void disposeMessage(TransactionType type) throws JMSException {
		while(true) {
			String ret = this.receiveNoWait(type);
			if (ret == null) {
				log.info("���b�Z�[�W�͂���܂���B");
				break;
			} else {
				log.info("���b�Z�[�W��j�����܂��B���e: " + ret);
			}
		}
	}

	private String receiveNoWait(TransactionType type) throws JMSException {
		// Connection�I�u�W�F�N�g�̍쐬
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection connection = factory.createQueueConnection();
		// �Z�b�V�����̍쐬
		QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		log.info("�j�����郁�b�Z�[�W����M���܂��BQueue: " + this.getQueueName(type));
		Queue queue = session.createQueue(this.getQueueName(type));
		//MessageConsumer�I�u�W�F�N�g�̍쐬�iQueue�Ɗ֘A�t���j
		QueueReceiver receiver = session.createReceiver(queue);
		connection.start();
		// ���b�Z�[�W�̎�M
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
		tradeTransactionStock.setTransactionType(TransactionType.�݌�.name());
		tradeTransactionStock.setWeek(0L);
		tradeTransactionStock.save();
		
		TradeTransaction tradeTransactionOrder = new TradeTransaction();
		tradeTransactionOrder.setAmount(m);
		tradeTransactionOrder.setRole(this);
		tradeTransactionOrder.setTransactionType(TransactionType.����.name());
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
