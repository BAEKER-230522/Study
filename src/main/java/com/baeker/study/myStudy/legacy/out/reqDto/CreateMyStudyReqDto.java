package com.baeker.study.myStudy.legacy.out.reqDto;
import lombok.Data;

@Data
public class CreateMyStudyReqDto {

    private Long memberId;
    private Long myStudyId;

    public CreateMyStudyReqDto(Long memberId, Long myStudyId) {
        this.memberId = memberId;
        this.myStudyId = myStudyId;
    }
}