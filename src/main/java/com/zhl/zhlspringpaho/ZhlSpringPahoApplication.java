package com.zhl.zhlspringpaho;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ZhlSpringPahoApplication implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ZhlSpringPahoApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(ZhlSpringPahoApplication.class, args);
/*        final ProducerTest producerTest = applicationContext.getBean("producerTest", ProducerTest.class);
        for (int i = 0; i <10; i++) {
            producerTest.sendMessage(String.format("message %d ", i));
        }
        try {
            Thread.sleep(9999999);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }






    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }



}
