package com.baeker.study.study.application.port.out.apiClient;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.config.FeignConfiguration;
import com.baeker.study.study.in.resDto.MemberResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "member",
        url = "${custom.feign.member}",
        configuration = FeignConfiguration.class
)
public interface MemberClient {

    @GetMapping("/get/v1/id")
    RsData<MemberResDto> byId(@RequestParam(value = "id") Long memberId);
}
