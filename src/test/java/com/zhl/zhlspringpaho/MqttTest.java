package com.zhl.zhlspringpaho;

import com.alibaba.fastjson.JSON;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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


    /**
     *
     */
    @Test
    public void subSyncClient() throws Exception {
        MqttAsyncClient client = new MqttAsyncClient(serverURI, clientId);
        // TODO: 2022/7/7 callback
        final MyMqttCallback callback = new MyMqttCallback();
        client.setCallback(callback);
        //这里可以设置很多东西
        // TODO: 2022/7/7 连接配置
        final MqttConnectionOptions mqttConnectionOptions = new MqttConnectionOptions();
        mqttConnectionOptions.setUserName(userName);
        mqttConnectionOptions.setPassword(password.getBytes(StandardCharsets.UTF_8));
        final IMqttToken connect = client.connect(mqttConnectionOptions);
        logger.info("MqttTest Connect {}", connect);
        connect.waitForCompletion(5000);

        IMqttMessageListener iMqttMessageListener = new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                logger.info("[MqttTest][messageArrived] topic = {}, message = {}",topic, message);


            }
        };
        MqttSubscription subscription = new MqttSubscription(topic, 0);
        MqttProperties subscriptionProperties=new MqttProperties();
        final ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        subscriptionProperties.setSubscriptionIdentifiers(list);

        final MqttActionListener mqttActionListener = new MqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                       logger.info("[MqttTest][onSuccess] asyncActionToken = {}",asyncActionToken);

            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                logger.info("[MqttTest][onFailure] asyncActionToken = {}, exception = {}",asyncActionToken, exception);

            }
        };

        final IMqttMessageListener iMqttMessageListener1 = new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                //这个会覆盖全局的call back
                logger.info("[MqttTest][messageArrived] topic = {}, message = {}",topic, message);

            }
        };

        IMqttToken subscribeToken = client.subscribe(subscription,iMqttMessageListener,null,iMqttMessageListener1,subscriptionProperties);
        subscribeToken.waitForCompletion(5000);
        Thread.sleep(99999999);

    }

    /**
     *
     */
    @Test
    public void subAsyncClient() throws Exception {

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
        MqttSubscription subscription = new MqttSubscription(topic, 0);
        IMqttMessageListener iMqttMessageListener = new IMqttMessageListener() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                          logger.info("[MqttTest][messageArrived] topic = {}, message = {}",topic, message);


            }
        };
        client.subscribe(subscription,iMqttMessageListener);

        Thread.sleep(999999);
    }
}
