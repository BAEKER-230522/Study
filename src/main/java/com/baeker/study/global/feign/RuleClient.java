package com.baeker.study.global.feign;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.dto.RuleDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rule"/*, url = "${custom.feign.rule}"*/)
@Qualifier
public interface RuleClient {

    @GetMapping(value = "api/rule/v1/search/{ruleId}")
    RsData<RuleDto> getRule(@PathVariable("ruleId") Long ruleId);
}
