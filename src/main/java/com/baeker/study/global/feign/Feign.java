package com.baeker.study.global.feign;


import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.dto.MemberDto;
import com.baeker.study.global.feign.dto.RuleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Gateway", url = "${custom.server}:9000")
public interface Feign {

    /**
     * 단일 Rule
     * @param ruleId
     * @return
     */

    @RequestMapping(method = RequestMethod.GET, value = "/api/rule/v1/{ruleId}")
    RsData<RuleDto> getRule(@PathVariable("ruleId") Long ruleId);


    /**
     * 단일 Member
     * @param memberId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/api/member/get/v1/{memberId}")
    RsData<MemberDto> getMember(@PathVariable("memberId") Long memberId);
}
