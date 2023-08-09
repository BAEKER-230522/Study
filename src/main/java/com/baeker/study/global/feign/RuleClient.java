package com.baeker.study.global.feign;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.dto.RuleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rule", url = "${custom.feign.rule}")
public interface RuleClient {

    @GetMapping(value = "/v1/search/{ruleId}")
    RsData<RuleDto> getRule(@PathVariable("ruleId") Long ruleId);
}
