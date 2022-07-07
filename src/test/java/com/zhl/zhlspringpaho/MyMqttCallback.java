package com.zhl.zhlspringpaho;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName MyMqttCallback
 * @Author ZHL
 * @Date 2022/7/7 - 22:33
 * @Description
 */
public class MyMqttCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MyMqttCallback.class);

    @Override
    public void disconnected(MqttDisconnectResponse disconnectResponse) {
        logger.info("[MyMqttCallback][disconnected] disconnectResponse = {}", disconnectResponse);

    }

    @Override
    public void mqttErrorOccurred(MqttException exception) {
        logger.info("[MyMqttCallback][mqttErrorOccurred] exception = {}", exception);

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        logger.info("[MyMqttCallback][messageArrived] topic = {}, message = {}", topic, message);

    }

    @Override
    public void deliveryComplete(IMqttToken token) {
        logger.info("[MyMqttCallback][deliveryComplete] token = {}", token);

    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        logger.info("[MyMqttCallback][connectComplete] reconnect = {}, serverURI = {}", reconnect, serverURI);

    }

    @Override
    public void authPacketArrived(int reasonCode, MqttProperties properties) {
        logger.info("[MyMqttCallback][authPacketArrived] reasonCode = {}, properties = {}", reasonCode, properties);
    }
}
