package com.baeker.study.study.in.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudyControllerTest {

    @Autowired MockMvc mvc;


    @Test
    void no1() throws Exception {
        mvc.perform(
                        patch("/api/study/v1/ranking"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}