package com.zhl.zhlspringpaho.core;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

public interface MqttListenerTemplate {


 void  messageProcessor(Message message, MessageHeaders headers);
}
