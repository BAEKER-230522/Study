package com.baeker.study.global.config;

import com.baeker.study.global.feign.FeignErrorDecode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public FeignErrorDecode feignErrorDecode() {
        return new FeignErrorDecode();
    }
}
