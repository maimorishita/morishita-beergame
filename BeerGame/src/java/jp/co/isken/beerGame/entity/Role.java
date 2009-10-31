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

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import jp.rough_diamond.commons.extractor.Condition;
import jp.rough_diamond.commons.extractor.Extractor;
import jp.rough_diamond.commons.extractor.Order;
import jp.rough_diamond.commons.extractor.Property;
import jp.rough_diamond.commons.resource.MessagesIncludingException;
import jp.rough_diamond.commons.service.BasicService;
import jp.rough_diamond.framework.transaction.VersionUnmuchException;

/**
 * ���[����Hibernate�}�b�s���O�N���X
 **/
public class Role extends jp.co.isken.beerGame.entity.base.BaseRole {
	private static final long serialVersionUID = 1L;

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
		QueueConnection senderConnection = factory.createQueueConnection();
		// �Z�b�V�����̍쐬
		QueueSession senderSession = senderConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue senderQueue = senderSession.createQueue(this.getPlayer().getGame().getId().toString() + "," + type.getQueue());
		// MessageProducer�I�u�W�F�N�g�̍쐬
		QueueSender sender = senderSession.createSender(senderQueue);
		// �s�ꂩ�珬���ւ̃��b�Z�[�W���쐬
		Message sendMsg = senderSession.createTextMessage(message);
		// ���b�Z�[�W�̑��M
		sender.send(sendMsg);
		sender.close();
		senderSession.close();
		senderConnection.close();
	}

	public String receive(TransactionType type) throws JMSException {
		// Connection�I�u�W�F�N�g�̍쐬
		ActiveMQConnectionFactory receiverFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection receiverConnection = receiverFactory.createQueueConnection();
		// �Z�b�V�����̍쐬
		QueueSession receiverSession = receiverConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue receiverQueue = receiverSession.createQueue(this.getPlayer().getGame().getId().toString() + ","+ type.getQueue());
		//MessageConsumer�I�u�W�F�N�g�̍쐬�iQueue�Ɗ֘A�t���j
		QueueReceiver receiver = receiverSession.createReceiver(receiverQueue);
		receiverConnection.start();
		// ���b�Z�[�W�̎�M
		TextMessage ret = (TextMessage)receiver.receiveNoWait();
		receiver.close();
		receiverSession.close();
		receiverConnection.close();
		return (ret == null) ? null : ret.getText();
	}

	public void order(Long amount) throws VersionUnmuchException, MessagesIncludingException, JMSException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(amount);
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.����.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.����.name())));
		tradeTransaction.save();

//		this.send(SendType.����, amount.toString());
	}

	public void acceptOrder() throws VersionUnmuchException, MessagesIncludingException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(this.getOrderCount());
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.��.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.��.name())));
		tradeTransaction.save();		
	}

	public Long getOrderCount() {
		// TODO 2009/10/31 R.Y & T.I MQ�Ŏ��̂ŁA����DB����擾�ł��߂�ˁ[���߂�ˁ[�B
		return BasicService.getService().findByPK(TradeTransaction.class, 43L).getAmount();
	}

	public void inbound() throws VersionUnmuchException, MessagesIncludingException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(this.getInboundCount());
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.����.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.����.name())));
		tradeTransaction.save();
	}

	public Long getInboundCount() {
		// TODO 2009/10/31 R.Y & T.I MQ�Ŏ��̂ŁA����DB����擾�ł��߂�ˁ[���߂�ˁ[�B
		return BasicService.getService().findByPK(TradeTransaction.class, 54L).getAmount();
	}

	public void outbound() throws VersionUnmuchException, MessagesIncludingException {
		TradeTransaction tradeTransaction = new TradeTransaction();
		tradeTransaction.setAmount(this.getOutboundCount());
		tradeTransaction.setRole(this);
		tradeTransaction.setTransactionType(TransactionType.�o��.name());
		tradeTransaction.setWeek(new Long(this.getCurrentWeek(TransactionType.�o��.name())));
		tradeTransaction.save();
	}

	public Long getOutboundCount() {
		// TODO 2009/10/31 R.Y & T.I �o�� = �� + ���c������Ă܂ւ�
		// ���݂́ARole=9L���g���Ă��āA�݌� > �� + ���c�̂��߁A�󒍂����̂܂ܕԂ�
		return BasicService.getService().findByPK(TradeTransaction.class, 43L).getAmount();
	}
}
