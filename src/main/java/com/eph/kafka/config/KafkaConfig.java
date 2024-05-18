package com.eph.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;


@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.topic1}")
    private String topic1;

    /**
     * JSON消息转换器
     */
    @Bean
    public RecordMessageConverter jsonConvert() {
        return new StringJsonMessageConverter();
    }

    /**
     * 程序启动时创建 topic
     */
    @Bean
    public NewTopic myTopic() {
        KafkaProperties.Consumer c = null;
        return new NewTopic(topic1, 2, (short) 1);
    }

}
