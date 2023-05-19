package com.baeker.study.base.configTest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ConfigTestController {

    @Value("${custom.con}")
    private String configStr;

    @GetMapping("test")
    public String test() {
        return configStr;
    }

    @GetMapping("test/2")
    public String test2() {
        return "server run";
    }
}
