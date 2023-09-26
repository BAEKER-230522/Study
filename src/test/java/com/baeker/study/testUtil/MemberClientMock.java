package com.baeker.study.testUtil;

import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.study.in.resDto.MemberResDto;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MemberClientMock {
    private MemberClient memberClient =
            Mockito.mock(MemberClient.class);

    @BeforeEach
    void beforeEach() {
        when(memberClient.updateMyStudy(any())).thenReturn(
                new RsData<>("S-1", "성공", null)
        );

        MemberResDto dto = new MemberResDto("test123", "test123");
        when(memberClient.findById(any())).thenReturn(
                new RsData<>("S-1", "성공", dto)
        );
    }
}
