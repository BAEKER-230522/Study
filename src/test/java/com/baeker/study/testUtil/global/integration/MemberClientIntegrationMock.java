package com.baeker.study.testUtil.global.integration;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class MemberClientIntegrationMock {

    @MockBean
    private MemberClient memberClient;

    public void updateMyStudyMocking() {
        when(memberClient.updateMyStudy(any())).thenReturn(
                new RsData<>("S-1", "성공", null)
        );
    }

    public void baekjoonConnectCheckMocking() {
        when(memberClient.isConnectBaekJoon(anyLong()))
                .thenAnswer(invocation -> {
                    Long memberId = (Long) invocation.getArgument(0);

                    if (memberId == 2L) return false;
                    else return true;
                });
    }

    public void deleteMemberMocking() {
        when(memberClient.deleteMyStudy(any()))
                .thenReturn(
                        new RsData("S-1", "성공", null)
                );
    }
}
