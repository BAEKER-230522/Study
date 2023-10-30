package com.baeker.study.testUtil.global.integration;

import org.springframework.beans.factory.annotation.Value;

public class TestData {

    @Value("${custom.mapping.study.web_usr}")
    public String STUDY_USR_URL;

    @Value("${custom.mapping.study.web_pub}")
    public String STUDY_PUB_URL;

    @Value("${custom.mapping.my-study.web_usr}")
    public String MY_STUDY_USR_URL;

    @Value("${custom.mapping.my-study.web_pub}")
    public String MY_STUDY_PUB_URL;

    @Value("${custom.jwt.test1}")
    public String jwt1;

    @Value("${custom.jwt.test2}")
    public String jwt2;

    @Value("${custom.jwt.test3}")
    public String jwt3;
}
