package com.baeker.study.global.feign;

import com.baeker.study.global.config.FeignConfiguration;
import com.baeker.study.global.feign.dto.PostRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "community", url = "${custom.feign.community}", configuration = FeignConfiguration.class)
public interface CommunityClient {
    @PostMapping("/posts/v1/setting")
    String createPost(@RequestBody PostRequest postRequest);
}
