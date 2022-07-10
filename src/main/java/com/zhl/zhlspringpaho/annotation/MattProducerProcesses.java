package com.zhl.zhlspringpaho.annotation;

import com.zhl.zhlspringpaho.core.Mqttv5MessagePublishAdapter;
import com.zhl.zhlspringpaho.constant.QosType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName MqttProducerProcesser
 * @Author ZHL
 * @Date 2022/7/10 - 19:09
 * @Description
 */
@Component
@Aspect
public class MattProducerProcesses implements ApplicationContextAware {

    @Pointcut("@annotation(com.zhl.zhlspringpaho.annotation.MqttProducer)")
    public void producerPoint() {
    }

    @AfterReturning(value = "producerPoint()", returning = "result")
    public Object sendMessage(JoinPoint joinPoint, Object result) {
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final MqttProducer producerAnn = signature.getMethod().getAnnotation(MqttProducer.class);
        String topic = producerAnn.topic();
        final QosType qos = producerAnn.qos();
        final boolean retain = producerAnn.retain();
        final int delay = producerAnn.delay();
        final String connection = producerAnn.connection();
        Mqttv5MessagePublishAdapter mqttv5PahoMessageHandler;
        try {
            mqttv5PahoMessageHandler = applicationContext.getBean(connection, Mqttv5MessagePublishAdapter.class);
        } catch (BeansException e) {
            throw new RuntimeException("无法找到链接", e);
        }
        if (delay != 0) {
           topic = String.format("$delayed/%d/%s", delay, topic);
        }
        final MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(result.toString().getBytes(StandardCharsets.UTF_8));
        mqttMessage.setRetained(retain);
        mqttMessage.setQos(qos.value());
        mqttv5PahoMessageHandler.publishMessage(topic, mqttMessage, null);
        return result;
    }


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private MqttListener findListenerAnnotations(Method method) {

        return AnnotatedElementUtils.findMergedAnnotation(method, MqttListener.class);

    }

    private MqttProducer findListenerAnnotations(Class<?> clazz) {
        return AnnotatedElementUtils.findMergedAnnotation(clazz, MqttProducer.class);
    }
}
