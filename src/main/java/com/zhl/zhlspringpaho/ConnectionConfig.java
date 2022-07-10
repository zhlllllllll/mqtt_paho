package com.zhl.zhlspringpaho;

import com.zhl.zhlspringpaho.core.MqttV5MessageReceiverAdapter;
import com.zhl.zhlspringpaho.core.Mqttv5MessagePublishAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName ConnectionConfig
 * @Author ZHL
 * @Date 2022/7/10 - 21:25
 * @Description
 */
@Configuration
public class ConnectionConfig {

    /**
     * 发消息
     *
     * @return
     */
    @Bean
    public MqttV5MessageReceiverAdapter mqttV5MessageReceiver() {
        MqttV5MessageReceiverAdapter adapter =
                new MqttV5MessageReceiverAdapter("tcp://192.168.2.171:1883", "testClient",
                        "test/abc", "test/123");
        adapter.setCompletionTimeout(5000);
        adapter.setQos(2);
        return adapter;
    }
    // TODO: 2022/7/10 监听注解，需要一个 adapter的bean name。注解属性需要topic qos
    //订阅的实现思路
//    1. 配置一个adapter，可以配置地址、topic、qos等、clientId
//    2. 配置一个注解，注解可以配置topic、qos、clientId?


    @Bean
    public Mqttv5MessagePublishAdapter messagePublishAdapter() {

        final Mqttv5MessagePublishAdapter test = new Mqttv5MessagePublishAdapter("tcp://192.168.2.171:1883", "test");
        test.getConnectionInfo().setPassword("test".getBytes(StandardCharsets.UTF_8));
        test.getConnectionInfo().setUserName("test");
        test.setAsync(true);
        return test;
    }
}
