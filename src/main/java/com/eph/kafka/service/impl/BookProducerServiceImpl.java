package com.eph.kafka.service.impl;


import com.eph.kafka.service.BookProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;



@Slf4j
@Service
public class BookProducerServiceImpl implements BookProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void sendMessage(String topic, Object o) {
        // 分区编号最好为 null，交给 kafka 自己去分配
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, null,
                System.currentTimeMillis(), String.valueOf(o.hashCode()), o);

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(producerRecord);

//        future.addCallback(new ListenableFutureCallback<>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.info("消息发送失败: {}", ex.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> result) {
//                log.info("消息发送成功, {}, {}", result.getRecordMetadata().topic(), (result.getRecordMetadata()));
//            }
//        });


        future.addCallback(result ->
                        log.info("成功 send message to topic: {} partition: {}",
                                result.getRecordMetadata().topic(),
                                result.getRecordMetadata().partition()),
                ex -> log.error("生产者发送消失败，原因：{}", ex.getMessage()));
    }
}
