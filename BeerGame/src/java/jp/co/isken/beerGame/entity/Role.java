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
 * ロールのHibernateマッピングクラス
 **/
public class Role extends jp.co.isken.beerGame.entity.base.BaseRole {
	private static final long serialVersionUID = 1L;

	public int getWeek() {
		Extractor extractor = new Extractor(TradeTransaction.class);
		extractor.add(Condition.eq(new Property(TradeTransaction.ROLE), this));
		extractor.addOrder(Order.desc(new Property(TradeTransaction.WEEK)));
		extractor.setLimit(1);
		List<TradeTransaction> list = BasicService.getService().findByExtractor(extractor);
		return list.get(0).getWeek().intValue();
	}

	public void send(TransactionType type, String message) throws JMSException {
		// Connectionオブジェクトの作成
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection senderConnection = factory.createQueueConnection();
		// セッションの作成
		QueueSession senderSession = senderConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue senderQueue = senderSession.createQueue(this.getPlayer().getGame().getId().toString() + "," + type.getQueue());
		// MessageProducerオブジェクトの作成
		QueueSender sender = senderSession.createSender(senderQueue);
		// 市場から小売へのメッセージを作成
		Message sendMsg = senderSession.createTextMessage(message);
		// メッセージの送信
		sender.send(sendMsg);
		sender.close();
		senderSession.close();
		senderConnection.close();
	}

	public String receive(TransactionType type) throws JMSException {
		// Connectionオブジェクトの作成
		ActiveMQConnectionFactory receiverFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection receiverConnection = receiverFactory.createQueueConnection();
		// セッションの作成
		QueueSession receiverSession = receiverConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue receiverQueue = receiverSession.createQueue(this.getPlayer().getGame().getId().toString() + ","+ type.getQueue());
		//MessageConsumerオブジェクトの作成（Queueと関連付け）
		QueueReceiver receiver = receiverSession.createReceiver(receiverQueue);
		receiverConnection.start();
		// メッセージの受信
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
		tradeTransaction.setTransactionType(TransactionType.発注.name());
		// TODO 2009/10/24 中原 現在週を判断して、セットするように変えること！！
		tradeTransaction.setWeek(1L);
		tradeTransaction.save();

//		this.send(SendType.発注, amount.toString());
	}
}
