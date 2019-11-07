package com.poll.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    @KafkaListener(topics = "result")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }


    @KafkaListener(topics = "result",
            containerFactory = "userKafkaListenerFactory")
    public void consumeJson(String message) {
        System.out.println("Consumed JSON Message: " + message);
    }
}