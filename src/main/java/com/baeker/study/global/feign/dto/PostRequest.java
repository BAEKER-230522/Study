package com.baeker.study.global.feign.dto;

import java.util.List;

public record PostRequest(Long missionId, List<Long> memberIdList, List<String> titleList) {
}
