package jp.co.isken.beerGame.mq;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {
	 public static void main( String[] args) {
	        try {
	            //Connection���쐬����Factory���쐬
	            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory( ActiveMQConnection.DEFAULT_BROKER_URL) ;
	            QueueConnection connection = factory.createQueueConnection() ;

	            //�Z�b�V�����̍쐬
	            QueueSession session = connection.createQueueSession( false, QueueSession.AUTO_ACKNOWLEDGE) ;
	            Queue queue = session.createQueue( "TestQueue") ;

	            
	            //MessageConsumer�I�u�W�F�N�g�̍쐬�iQueue�Ɗ֘A�t���j
//	            QueueReceiver receiver = session.createReceiver( queue) ;
//	            String selector = "game = '�A�x�x' AND role = '��' AND week = '1' AND type = '����'";

	            QueueReceiver receiver = session.createReceiver(queue);
	            
	            connection.start() ;
	            
	            //���b�Z�[�W�̎�M
	            TextMessage msg = (TextMessage)receiver.receive() ;
	            System.out.println( msg.getText()) ;

	            receiver.close() ;
	            session.close() ;
	            connection.close() ;
	        }
	        catch( JMSException e) {
	            e.printStackTrace() ;
	        }

	    }
	
}
