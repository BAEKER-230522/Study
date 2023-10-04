package com.baeker.study.testUtil.global;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class MockMvcRequest {

    public static ResultActions postReq(MockMvc mvc, String url, String jwt, String reqDto) throws Exception {
        return mvc.perform(post(url)
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt)
                .content(reqDto)
        ).andDo(print());
    }

    public static ResultActions patchReq(MockMvc mvc, String url, String jwt, String reqDto) throws Exception {
        return mvc.perform(patch(url)
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt)
                .content(reqDto)
        ).andDo(print());
    }
}
