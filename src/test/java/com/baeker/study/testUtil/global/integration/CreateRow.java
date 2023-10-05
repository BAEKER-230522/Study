package com.baeker.study.testUtil.global.integration;

import com.baeker.study.study.adapter.in.reqDto.StudyCreateReqDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;

import static com.baeker.study.testUtil.global.integration.MockMvcRequest.postReq;

public class CreateRow {

    @Value("${custom.mapping.study.web}")
    private static String mapping;

    @Value("${custom.jwt.test}")
    private static String jwt;

    public static void createStudy(MockMvc mvc, Long memberId, int name, Integer capacity) throws Exception {
        StudyCreateReqDto reqDto = new StudyCreateReqDto("study" + name, "", capacity);
        postReq(mvc, mapping + "/v2/study", jwt, reqDto);
    }
}
