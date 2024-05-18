package com.eph.kafka.service;


public interface BookProducerService {

    /**
     * 向指定topic发送对象
     * @param topic kafka 主题
     * @param o 消息
     */
    void sendMessage(String topic, Object o);
}
