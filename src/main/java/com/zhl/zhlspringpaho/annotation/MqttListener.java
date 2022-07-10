package com.zhl.zhlspringpaho.annotation;

import com.zhl.zhlspringpaho.constant.QosType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MqttListener {

    String connection();

    String topic();

    QosType qos() default QosType.QOS1;

/*    // TODO: 2022/7/10
    String groupId() default "";*/

}
