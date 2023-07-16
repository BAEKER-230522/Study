package com.baeker.study.global.feign;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.dto.MembersReqDto;
import com.baeker.study.myStudy.out.reqDto.CreateMyStudyReqDto;
import com.baeker.study.myStudy.out.reqDto.DeleteMyStudyReqDto;
import com.baeker.study.study.in.resDto.MemberResDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Qualifier
@FeignClient(name = "member", url = "${custom.feign.member}")
public interface MemberClient {


    //-- my study 등록 --//
    @PostMapping("/v1/my-study")
    RsData updateMyStudy(@RequestBody CreateMyStudyReqDto reqDto);

    //-- my study 삭제 --//
    @DeleteMapping("/v1/my-study")
    RsData deleteMyStudy(@RequestBody DeleteMyStudyReqDto reqDto);

    //-- member list 조회 --//
    @PostMapping("/get/v1/id/list")
    RsData<List<MemberResDto>> findMemberList(@RequestBody MembersReqDto reqDto);

    //--my study 로 member 조회 --//
    @GetMapping("/get/v1/id")
    RsData<MemberResDto> findById(@RequestParam(value = "id") Long id);
}
