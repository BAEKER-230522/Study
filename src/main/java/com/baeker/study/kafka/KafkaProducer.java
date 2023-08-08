//package com.baeker.study.kafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class KafkaProducer {
//
//    @Value("${message.topic.name}")
//    private String topicName;
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    public KafkaProducer(KafkaTemplate kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendMessage(String msg) {
//        System.out.println(String.format("Produce message : &s", msg));
//        this.kafkaTemplate.send(topicName, msg);
//    }
//}
