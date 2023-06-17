package com.baeker.study.global.feign;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.myStudy.out.reqDto.CreateMyStudyReqDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Qualifier
@FeignClient(name = "member", url = "${custom.feign.member}")
public interface MemberClient {


    @PostMapping("/v1/my-study")
    RsData updateMyStudy(@RequestBody CreateMyStudyReqDto reqDto);
}
