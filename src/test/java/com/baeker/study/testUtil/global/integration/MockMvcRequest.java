package com.baeker.study.testUtil.global.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MockMvcRequest {

    public static ResultActions postReq(MockMvc mvc, String url, String jwt, Object reqDto) throws Exception {
        String dto = toJsonString(reqDto);
        return mvc.perform(post(url)
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt)
                .content(dto)
        ).andDo(print());
    }

    public static ResultActions patchReq(MockMvc mvc, String url, String jwt, Object reqDto) throws Exception {
        String dto = toJsonString(reqDto);
        return mvc.perform(patch(url)
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt)
                .content(dto)
        ).andDo(print());
    }

    public static ResultActions deleteReq(MockMvc mvc, String url, String jwt, Long pathVariable) throws Exception {
        return mvc.perform(delete(url, pathVariable)
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt)
        ).andDo(print());
    }

    public static ResultActions deleteReq(MockMvc mvc, String url, String jwt, Object reqDto) throws Exception {
        String dto = toJsonString(reqDto);
        return mvc.perform(delete(url)
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt)
                .content(dto)
        ).andDo(print());
    }

    private static String toJsonString(Object reqDto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(reqDto);
    }
}
