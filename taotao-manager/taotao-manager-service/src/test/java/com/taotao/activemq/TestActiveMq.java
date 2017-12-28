package com.taotao.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * Created by admin on 2017/12/28.
 */
public class TestActiveMq {
    @Test
    public void testQueueProducer() throws JMSException {
        //1.创建一个连接工厂对象ConnectionFactory对象。需要指定mq服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2.使用ConnectionFactory创建一个连接Connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接。调用Connection对象的start方法
        connection.start();
        //4.使用Connection对象创建一个Session对象
        //第一个参数是是否开启事务，一般不使用事务。保证数据的最终一致，可以使用消息队列实现。
        //如果第一个参数为true，第二个参数自动忽略。如果不开启事务false，第二个参数为消息的应答模式。一般自动应答就可以。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.使用Session对象创建一个Destination对象，两种形式queue、topic。现在应该使用queue
        //参数就是消息队列的名称
        Queue queue = session.createQueue("test-queue2");
        //6.使用Session对象创建一个Producer对象
        MessageProducer producer = session.createProducer(queue);
        //7.创建一个TextMessage对象
		/*TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText("hello activemq");*/
        TextMessage textMessage = session.createTextMessage("hello activemq");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }

}
