package com.zhl.zhlspringpaho.impl;

import com.zhl.zhlspringpaho.annotation.MqttProducer;
import com.zhl.zhlspringpaho.constant.QosType;
import org.springframework.stereotype.Component;

/**
 * @ClassName ProducerTest
 * @Author ZHL
 * @Date 2022/7/10 - 19:28
 * @Description
 */
@Component
public class ProducerTest {

    @MqttProducer(connection ="messagePublishAdapter" ,topic = "test/zhl", qos = QosType.QOS1,delay = 3)
    public String sendMessage(String message) {
        return System.currentTimeMillis()+"";
    }
}
