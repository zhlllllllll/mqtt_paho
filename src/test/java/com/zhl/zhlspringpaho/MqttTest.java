package com.zhl.zhlspringpaho;

import com.alibaba.fastjson.JSON;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName MqttTest
 * @Author ZHL
 * @Date 2022/7/7 - 22:32
 * @Description
 */
@SpringBootTest
public class MqttTest {
    private static final Logger logger = LoggerFactory.getLogger(MqttTest.class);


    String serverURI = "tcp://192.168.2.171:1883";
    String clientId = "abc123";
    String userName = "test";
    String password = "test";

    String topic = "test/abc";

    @Test
    void contextLoads() {
    }

    /**
     * 异步连接
     */
    @Test
    public void ConnectAsync() throws Exception {
        MqttAsyncClient client = new MqttAsyncClient(serverURI, clientId);
        // TODO: 2022/7/7 callback
        client.setCallback(new MyMqttCallback());
        //这里可以设置很多东西
        // TODO: 2022/7/7 连接配置 
        final MqttConnectionOptions mqttConnectionOptions = new MqttConnectionOptions();
        mqttConnectionOptions.setUserName(userName);
        mqttConnectionOptions.setPassword(password.getBytes(StandardCharsets.UTF_8));
        final IMqttToken connect = client.connect(mqttConnectionOptions);
        logger.info("MqttTest Connect {}", connect);
        connect.waitForCompletion(5000);
        logger.info("MqttTest Connect client is connected {}", client.isConnected());
        Thread.sleep(5000);
        //主动断开不会调用callBack的disconnected方法
        final IMqttToken disconnect = client.disconnect();
        disconnect.waitForCompletion(5000);
        logger.info("MqttTest Connect client is connected {}", client.isConnected());
        Thread.sleep(99999999999999999l);


    }

    /**
     * 同步连接
     */
    @Test
    public void connectSync() throws Exception {
        final MqttClient client = new MqttClient(serverURI, clientId);
        client.setCallback(new MyMqttCallback());
        final MqttConnectionOptions mqttConnectionOptions = new MqttConnectionOptions();
        mqttConnectionOptions.setUserName(userName);
        mqttConnectionOptions.setPassword(password.getBytes(StandardCharsets.UTF_8));
        mqttConnectionOptions.setKeepAliveInterval(10);
        client.connect(mqttConnectionOptions);
        logger.info("MqttTest connectSync isConnected {}", client.isConnected());
        Thread.sleep(500);
        client.disconnect();
        logger.info("MqttTest connectSync isConnected {}", client.isConnected());
        Thread.sleep(999999);
    }
}
