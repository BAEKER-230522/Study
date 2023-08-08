//package com.baeker.study.kafka;
//
//import com.baeker.study.base.exception.NotFoundException;
//import com.baeker.study.domain.studyRule.event.StudyRuleEvent;
//import com.baeker.study.kafka.dto.consume.MemberDto;
//import com.baeker.study.kafka.dto.consume.StudyRuleConsumeDto;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.NoSuchElementException;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class KafkaConsumer {
//    private final ApplicationEventPublisher publisher;
//
//    /**
//     * solved message
//     */
//    @KafkaListener(topics = "${message.topic.solved-member}", groupId = ConsumerConfig.GROUP_ID_CONFIG)
//    public void consume(String message) throws IOException {
//        log.debug("#############메시지 대기중 ###############");
//        log.info("message : {}", message);
//        Map<Object, Object> map;
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            map = objectMapper.readValue(message, new TypeReference<Map<Object, Object>>() {
//            });
//        } catch (JsonProcessingException e) {
//            throw new NotFoundException(e.getMessage() + "데이터 없음");
//        }
//        try {
//            Integer memberId = (Integer) map.get("id");
//            Long longId = memberId.longValue();
//            Integer bronze = (Integer) map.get("bronze");
//            Integer silver = (Integer) map.get("silver");
//            Integer gold = (Integer) map.get("gold");
//            Integer platinum = (Integer) map.get("platinum");
//            Integer diamond = (Integer) map.get("diamond");
//            Integer ruby = (Integer) map.get("ruby");
//
//            MemberDto member = new MemberDto(longId, bronze, silver, gold, platinum, diamond, ruby);
//            //TODO: 이벤트리스너
//
//        } catch (NoSuchElementException e) {
//            throw new NotFoundException("Member 데이터 없음");
//        }
//    }
//
//    @KafkaListener(topics = "${message.topic.studyRule}", groupId = ConsumerConfig.GROUP_ID_CONFIG)
//    public void consumeStudyRule(String msg) {
//        Map<Object, Object> map;
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            map = objectMapper.readValue(msg, new TypeReference<Map<Object, Object>>() {
//            });
//        } catch (JsonProcessingException e) {
//            throw new NotFoundException(e.getMessage() + "데이터 없음");
//        }
//        try {
//            Integer studyInt = (Integer) map.get("studyRuleId");
//            Long studyRuleId = studyInt.longValue();
//            //TODO: 필요유무 체크 해야함
////            Integer ruleInt = (Integer) map.get("ruleId");
////            Long ruleId = ruleInt.longValue();
//            StudyRuleConsumeDto dto = new StudyRuleConsumeDto(studyRuleId);
//            publisher.publishEvent(new StudyRuleEvent(this, studyRuleId));
//            //TODO: 이벤트 리스너
//        } catch (NoSuchElementException e) {
//            log.error(e.getMessage());
//        }
//    }
//}
//
