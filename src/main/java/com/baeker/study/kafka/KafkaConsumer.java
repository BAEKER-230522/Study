package com.baeker.study.kafka;

import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.kafka.dto.MemberDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    /**
     * solved message
     */
    @KafkaListener(topics = "${message.topic.solved-member}", groupId = ConsumerConfig.GROUP_ID_CONFIG)
    public void consume(String message) throws IOException {
        log.debug("#############메시지 대기중 ###############");
        log.info("message : {}", message);
        Map<Object, Object> map;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            map = objectMapper.readValue(message, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new NotFoundException(e + "데이터 없음");
        }
        try {
            Integer memberId = (Integer) map.get("id");
            Long longId = memberId.longValue();
            Integer bronze = (Integer) map.get("bronze");
            Integer silver = (Integer) map.get("silver");
            Integer gold = (Integer) map.get("gold");
            Integer platinum = (Integer) map.get("platinum");
            Integer diamond = (Integer) map.get("diamond");
            Integer ruby = (Integer) map.get("ruby");

            MemberDto member = new MemberDto(longId, bronze, silver, gold, platinum, diamond, ruby);
            //TODO: 이벤트리스너
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Member 데이터 없음");
        }
    }
}

