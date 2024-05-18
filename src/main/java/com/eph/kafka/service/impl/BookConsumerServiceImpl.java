package com.eph.kafka.service.impl;

import com.eph.kafka.entity.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookConsumerServiceImpl {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @KafkaListener(topics = {"${kafka.topic.topic1}"}, groupId = "group1")
    public void consumeMessage(ConsumerRecord<String, String> bookConsumerRecord, Acknowledgment ack) {

        try {
            Book book = objectMapper.readValue(bookConsumerRecord.value(), Book.class);
            log.info("消费者消费消息 topic:{} partition:{} -> {}", bookConsumerRecord.topic(), bookConsumerRecord.partition(), book.toString());
            ack.acknowledge();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
