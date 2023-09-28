package com.baeker.study.testUtil.feign;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.study.in.resDto.MemberResDto;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class MemberClientMock {
    private MemberClient memberClient =
            Mockito.mock(MemberClient.class);

    public void memberClientUpdateMyStudyMocking() {
        when(memberClient.updateMyStudy(any())).thenReturn(
                new RsData<>("S-1", "성공", null)
        );
    }

    public void memberClientConnectCheckMocking() {
        when(memberClient.isConnectBaekJoon(anyLong()))
                .thenAnswer(invocation -> {
                    Long memberId = (Long) invocation.getArgument(0);

                    if (memberId == 2L) return false;
                    else return true;
                });
    }
}
