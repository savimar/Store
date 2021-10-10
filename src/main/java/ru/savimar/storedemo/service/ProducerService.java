package ru.savimar.storedemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.savimar.storedemo.entity.Letter;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, Letter> kafkaTemplate;

    public void produce(Letter letter) {
        System.out.println("Producing the message: " + letter.toString());
        kafkaTemplate.send("messages", letter);
    }
}
