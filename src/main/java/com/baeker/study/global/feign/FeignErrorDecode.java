package com.baeker.study.global.feign;

import com.baeker.study.global.exception.feign.FeignClientException;
import com.baeker.study.global.exception.feign.FeignException;
import com.baeker.study.global.exception.feign.FeignServerException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecode implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400 -> {
                throw new FeignClientException("잘못된 요청입니다.", response.status());
            }
            case 403 -> {
                throw new FeignClientException("권한이 없습니다.", response.status());
            }
            case 500 -> {
                throw new FeignServerException("서버 오류");
            }
            default -> {
                throw new FeignException("기타 오류 발생", response.status());
            }
        }
    }
}
