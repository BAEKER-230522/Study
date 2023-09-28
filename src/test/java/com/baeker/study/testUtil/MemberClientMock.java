package com.baeker.study.testUtil;

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

    public void memberClientMocking() {

        MemberResDto dto = new MemberResDto("test123");

        when(memberClient.findById(any()))
                .thenAnswer(invocation -> {
                    Long memberId = (Long) invocation.getArgument(0);

                    if (memberId == 2L)
                        return new RsData<>("S-1", "성공", dto);
                    else if (memberId == 3L)
                        throw new BadRequestException();

                    dto.setBaekJoonName("test123");
                    return new RsData<>("S-1", "성공", dto);
                });
    }

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
