package com.taotao.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by admin on 2017/12/28.
 */
public class TestSpringActiveMQ {
    @Test
    public void testSpringActiveMQ() throws IOException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");

        System.in.read();
    }
}
