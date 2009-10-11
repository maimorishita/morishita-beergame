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

	public void test�s�ꂩ�珬���Ƀ��b�Z�[�W�𑗐M���邱��() throws Exception {
		// Connection�I�u�W�F�N�g�̍쐬
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection senderConnection = factory.createQueueConnection();
		// �Z�b�V�����̍쐬
		QueueSession senderSession = senderConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue senderQueue = senderSession.createQueue("TestQueue");
		// MessageProducer�I�u�W�F�N�g�̍쐬
		QueueSender sender = senderSession.createSender(senderQueue);
		// �s�ꂩ�珬���ւ̃��b�Z�[�W���쐬
		Message sendMsg = senderSession.createTextMessage("Market, 1, 10");
		// ���b�Z�[�W�̑��M
		sender.send(sendMsg);
		sender.close();
		senderSession.close();
		senderConnection.close();

		// Connection�I�u�W�F�N�g�̍쐬
		ActiveMQConnectionFactory receiverFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		QueueConnection receiverConnection = receiverFactory.createQueueConnection();
		// �Z�b�V�����̍쐬
		QueueSession receiverSession = receiverConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		Queue receiverQueue = receiverSession.createQueue("TestQueue");
		//MessageConsumer�I�u�W�F�N�g�̍쐬�iQueue�Ɗ֘A�t���j
		QueueReceiver receiver = receiverSession.createReceiver(receiverQueue);
		receiverConnection.start();
		// ���b�Z�[�W�̎�M
		TextMessage receiveMsg = (TextMessage)receiver.receive();
		receiver.close();
		receiverSession.close();
		receiverConnection.close();
		assertEquals("���b�Z�[�W�̓��e�Ɍ�肪����܂�", "Market, 1, 10", receiveMsg.getText());
	}
}
