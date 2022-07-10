package com.zhl.zhlspringpaho;

import com.zhl.zhlspringpaho.impl.ProducerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@SpringBootTest()
@Configuration
public class SpringPahoApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void SpringPahoApplicationTests() throws InterruptedException {

        final ProducerTest producerTest = applicationContext.getBean("producerTest", ProducerTest.class);
        for (int i = 0; ; i++) {
            producerTest.sendMessage(String.format("message %d ", i));
            Thread.sleep(2000);
        }

    }


}
