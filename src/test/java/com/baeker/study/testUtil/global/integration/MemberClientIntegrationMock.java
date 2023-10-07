package com.baeker.study.testUtil.global.integration;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.global.feign.dto.MembersReqDto;
import com.baeker.study.study.in.resDto.MemberResDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class MemberClientIntegrationMock {

    @MockBean
    private MemberClient memberClient;

    @Value("${custom.mapping.study.web}")
    public String STUDY_URL;

    @Value("${custom.mapping.my-study.web}")
    public String MY_STUDY_URL;

    @Value("${custom.jwt.test1}")
    public String jwt1;

    @Value("${custom.jwt.test2}")
    public String jwt2;

    @Value("${custom.jwt.test3}")
    public String jwt3;

    public void updateMyStudyMocking() {
        when(memberClient.updateMyStudy(any())).thenReturn(
                new RsData<>("S-1", "성공", null)
        );
    }

    public void baekjoonConnectCheckMocking() {
        when(memberClient.isConnectBaekJoon(anyLong()))
                .thenAnswer(invocation -> {
                    Long memberId = (Long) invocation.getArgument(0);
                    return true;
                });
    }

    public void getMemberListMocking() {
        when(memberClient.findMemberList(any()))
                .thenAnswer(invocation -> {
                    MembersReqDto dto = (MembersReqDto) invocation.getArgument(0);
                    List<Long> members = dto.getMembers();

                    List<MemberResDto> dtos = new ArrayList<>();

                    for (Long memberId : members) {
                        MemberResDto resDto = new MemberResDto();
                        resDto.setId(memberId);
                        dtos.add(resDto);
                    }

                    return RsData.successOf(dtos);
                });
    }

    public void deleteMemberMocking() {
        when(memberClient.deleteMyStudy(any()))
                .thenReturn(
                        new RsData("S-1", "성공", null)
                );
    }
}
