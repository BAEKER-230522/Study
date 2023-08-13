package com.baeker.study.study.in.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/study")
public class StudyMvcController {

    @Hidden
    @GetMapping("/swagger")
    public String swagger() {
        log.info("swagger page 접속 확인");
        return "redirect:http://study:8082/swagger-ui/index.html";
    }
}
