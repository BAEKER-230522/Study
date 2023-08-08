//package com.baeker.study.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RestController
//@RequestMapping("/kafka")
//public class KafkaController {
//
//    private final KafkaProducer producer;
//
//    @Autowired
//    KafkaController(KafkaProducer producer) {
//        this.producer = producer;
//    }
//
//    @PostMapping("/test")
//    public String sendMsg(@RequestParam String msg) {
//        log.info("message : {}", msg);
//        this.producer.sendMessage(msg);
//        return "success";
//    }
//}