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
	            //Connectionを作成するFactoryを作成
	            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory( ActiveMQConnection.DEFAULT_BROKER_URL) ;
	            QueueConnection connection = factory.createQueueConnection() ;

	            //セッションの作成
	            QueueSession session = connection.createQueueSession( false, QueueSession.AUTO_ACKNOWLEDGE) ;
	            Queue queue = session.createQueue( "TestQueue") ;

	            
	            //MessageConsumerオブジェクトの作成（Queueと関連付け）
//	            QueueReceiver receiver = session.createReceiver( queue) ;
//	            String selector = "game = 'アベベ' AND role = '卸' AND week = '1' AND type = '発注'";

	            QueueReceiver receiver = session.createReceiver(queue);
	            
	            connection.start() ;
	            
	            //メッセージの受信
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
