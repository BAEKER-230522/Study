package com.baeker.study.global.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecode implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
//            case 400 -> {
//                log.error("feign client exception : 잘못된 요청입니다.");
//                return new BadRequestException();
//            }
//            case 403 -> {
//                log.error("feign client exception : 권한이 없습니다.");
//                return new ForbiddenException();
//            }
//            case 500 -> {
//                log.error("feign server exception : 서버 오류");
//                return new InternalServerErrorException("서버 내부 오류 발생");
//            }
        }
        return new Exception(response.reason());
    }
}
