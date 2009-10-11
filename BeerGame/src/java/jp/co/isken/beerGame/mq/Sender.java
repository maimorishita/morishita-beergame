package jp.co.isken.beerGame.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;



public class Sender {

    public static void main( String[] args) {

        try {
            //Connectionオブジェクトの作成
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory( ActiveMQConnection.DEFAULT_BROKER_URL) ;
            QueueConnection connection = factory.createQueueConnection() ;

            //セッションの作成
            QueueSession session = connection.createQueueSession( false, QueueSession.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue( "TestQueue") ;

            //MessageProducerオブジェクトの作成
            QueueSender sender = session.createSender( queue) ;

            Message msg = session.createTextMessage("message from 卸") ;
//            msg.setStringProperty("game","アベベ");
//            msg.setStringProperty("role","卸");
//            msg.setStringProperty("week","1");
//            msg.setStringProperty("type","発注");
            
            Message msg2 = session.createTextMessage("message from メーカー") ;
//            msg.setStringProperty("game","アベベ");
//            msg.setStringProperty("role","メーカー");
//            msg.setStringProperty("week","1");
//            msg.setStringProperty("type","発注");

            //メッセージの送信
            sender.send( msg) ;
            sender.send( msg2) ;

            sender.close() ;
            session.close() ;
            connection.close() ;
        }
        catch( JMSException e) {
            e.printStackTrace() ;
        }

    }


	
}
