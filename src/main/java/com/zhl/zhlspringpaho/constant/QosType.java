package com.zhl.zhlspringpaho.constant;

public enum QosType {
    QOS0(0),
    QOS1(1),
    QOS2(2),
    ;

    QosType(int value) {
        this.value = value;
    }

    private int value;

    public int value() {
        return value;
    }
}
