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
            //Connection�I�u�W�F�N�g�̍쐬
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory( ActiveMQConnection.DEFAULT_BROKER_URL) ;
            QueueConnection connection = factory.createQueueConnection() ;

            //�Z�b�V�����̍쐬
            QueueSession session = connection.createQueueSession( false, QueueSession.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue( "TestQueue") ;

            //MessageProducer�I�u�W�F�N�g�̍쐬
            QueueSender sender = session.createSender( queue) ;

            Message msg = session.createTextMessage("message from ��") ;
//            msg.setStringProperty("game","�A�x�x");
//            msg.setStringProperty("role","��");
//            msg.setStringProperty("week","1");
//            msg.setStringProperty("type","����");
            
            Message msg2 = session.createTextMessage("message from ���[�J�[") ;
//            msg.setStringProperty("game","�A�x�x");
//            msg.setStringProperty("role","���[�J�[");
//            msg.setStringProperty("week","1");
//            msg.setStringProperty("type","����");

            //���b�Z�[�W�̑��M
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
