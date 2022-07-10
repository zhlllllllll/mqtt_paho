package com.zhl.zhlspringpaho.impl;

import com.zhl.zhlspringpaho.annotation.MqttListener;
import com.zhl.zhlspringpaho.constant.QosType;
import com.zhl.zhlspringpaho.core.MqttListenerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @ClassName TestIsComponent
 * @Author ZHL
 * @Date 2022/7/10 - 11:24
 * @Description
 */
@MqttListener(connection = "mqttV5MessageReceiver", topic = "test/zhl", qos = QosType.QOS2)
public class ListenerTest implements MqttListenerTemplate {
    private static final Logger logger = LoggerFactory.getLogger(ListenerTest.class);


    @Override
    public void messageProcessor(Message message, MessageHeaders headers) {
        final String content = new String((byte[]) message.getPayload());
        logger.info("TestIsComponent messageProcessor receive message ,content={} , ,receive timestamp={} ,delay={}"
                , content, headers.getTimestamp(),  headers.getTimestamp() - Long.parseLong(content));
    }
}
