package com.taotao.search.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by admin on 2017/12/28.
 * 接收Activemq发送的消息
 */
public class MyMessageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MyMessageListener.class);
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            logger.info("MyMessageListener.onMessage:{}", text);
            System.out.println("MyMessageListener.onMessage: " + text);
        } catch (JMSException e) {
            logger.error("MyMesssageLisgtener.ERROR:{}",e);
        }
    }
}
