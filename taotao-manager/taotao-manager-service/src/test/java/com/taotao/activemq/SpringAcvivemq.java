package com.taotao.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * Created by admin on 2017/12/28.
 */
public class SpringAcvivemq {

    @Test
    public void testJmsTemplate() throws Exception{
        //初始化Spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //从容器中获取JmsTemplate
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //从容器中获取Destination
        Destination destination = (Destination) applicationContext.getBean("test-queue");
        //发送消息
        jmsTemplate.send(destination, session -> {
            TextMessage message = session.createTextMessage("spring activemq send queue message");
            return message;
        });
    }
}
