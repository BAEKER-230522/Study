package com.baeker.study.study.in.resDto;

import com.baeker.study.myStudy.domain.entity.StudyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.*;

@Data
@NoArgsConstructor
public class MemberStudyResDto {

    private int count;
    private StudyStatus status;
    private List<StudyResDto> studyList;

    public MemberStudyResDto(int count, int status, List<StudyResDto> studyList) {
        this.count = count;
        this.studyList = studyList;

        switch (status) {
            case 1 -> this.status = MEMBER;
            case 2 -> this.status = PENDING;
            default -> this.status = INVITING;
        }
    }
}
