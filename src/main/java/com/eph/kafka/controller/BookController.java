package com.eph.kafka.controller;

import com.eph.kafka.entity.Book;
import com.eph.kafka.service.BookProducerService;
import com.eph.kafka.service.impl.BookProducerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequestMapping(value = "/book")
public class BookController {
    @Value("${kafka.topic.topic1}")
    String myTopic;

    private final BookProducerService producer;
    private AtomicLong atomicLong = new AtomicLong();

    BookController(BookProducerServiceImpl producer) {
        this.producer = producer;
    }

    @GetMapping
    public String sendMessageToKafkaTopic(@RequestParam("name") String name) {
        this.producer.sendMessage(myTopic, new Book(atomicLong.addAndGet(1), name));
        return "ok";
    }
}
