package jp.co.isken.beerGame.mq;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import jp.co.isken.beerGame.entity.MasterLoader;
import jp.co.isken.beerGame.entity.NumberingLoader;
import jp.co.isken.beerGame.entity.TransactionLoader;
import jp.rough_diamond.commons.testing.DataLoadingTestCase;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MQTest extends DataLoadingTestCase {

	protected void setUp() throws Exception {
		super.setUp();
		MasterLoader.init();
		TransactionLoader.init();
		NumberingLoader.init();
	}

	public void test市場から小売にメッセージを送信すること() throws Exception {
		// Connectionオブジェクトの作成
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection senderConnection = factory.createQueueConnection();
		// セッションの作成
		QueueSession senderSession = senderConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue senderQueue = senderSession.createQueue("TestQueue");
		// MessageProducerオブジェクトの作成
		QueueSender sender = senderSession.createSender(senderQueue);
		// 市場から小売へのメッセージを作成
		Message sendMsg = senderSession.createTextMessage("Market, 1, 10");
		// メッセージの送信
		sender.send(sendMsg);
		sender.close();
		senderSession.close();
		senderConnection.close();

		// Connectionオブジェクトの作成
		ActiveMQConnectionFactory receiverFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection receiverConnection = receiverFactory.createQueueConnection();
		// セッションの作成
		QueueSession receiverSession = receiverConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue receiverQueue = receiverSession.createQueue("TestQueue");
		//MessageConsumerオブジェクトの作成（Queueと関連付け）
		QueueReceiver receiver = receiverSession.createReceiver(receiverQueue);
		receiverConnection.start();
		// メッセージの受信
		TextMessage receiveMsg = (TextMessage)receiver.receive();
		receiver.close();
		receiverSession.close();
		receiverConnection.close();
		assertEquals("メッセージの内容に誤りがあります", "Market, 1, 10", receiveMsg.getText());
	}
}
