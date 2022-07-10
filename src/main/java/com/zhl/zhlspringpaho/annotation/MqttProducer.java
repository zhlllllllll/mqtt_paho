package com.zhl.zhlspringpaho.annotation;

import com.zhl.zhlspringpaho.constant.QosType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MqttProducer {

    String connection();

    QosType qos() default QosType.QOS1;

    String topic();

    /**
     * 保留消息
     *
     * @return
     */
    boolean retain() default false;

    /**
     * 延迟发布
     *
     * @return
     */
    int delay() default 0;
}
