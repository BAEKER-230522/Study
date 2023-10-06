package com.baeker.study.testUtil.global.unit;

import com.baeker.study.global.exception.jwt.InvalidJwtException;
import com.baeker.study.global.jwt.JwtDecrypt;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class JwtDecryptMock {

    @MockBean
    private JwtDecrypt decrypt;

    public void getMemberIdMocking() {
        when(decrypt.getMemberId(anyString()))
                .thenAnswer(invocation -> {
                    String token = (String) invocation.getArgument(0);

                    if (token.equals("0"))
                        throw new InvalidJwtException("JWT 복호화에 실패");

                    return Long.parseLong(token);
                });
    }
}
