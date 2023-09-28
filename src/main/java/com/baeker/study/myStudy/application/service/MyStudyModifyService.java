package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.myStudy.application.port.in.MyStudyModifyUseCase;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyStudyModifyService implements MyStudyModifyUseCase {

    @Override
    public void msg(Long memberId, MyStudy myStudy, String msg) {
        StudyStatus status = myStudy.getStatus();

        switch (status) {
            case MEMBER -> duplicateCheck();
            case PENDING -> isMember(myStudy, memberId);
            default -> isLeader(myStudy, memberId);
        }

        myStudy.modifyMsg(msg);
    }

    @Override
    public void accept(Long memberId, MyStudy myStudy) {
        StudyStatus status = myStudy.getStatus();

        switch (status) {
            case MEMBER -> duplicateCheck();
            case PENDING -> isLeader(myStudy, memberId);
            default -> isMember(myStudy, memberId);
        }

        myStudy.accept();
    }

    private void duplicateCheck() {
        throw new IllegalStateException("이미 승인된 스터디 맴버입니다.");
    }

    private void isMember(MyStudy myStudy, Long memberId) {
        Long joinMemberId = myStudy.getMember();
        permissionCheck(memberId, joinMemberId);
    }

    private void isLeader(MyStudy myStudy, Long memberId) {
        Long leader = myStudy.getStudy().getLeader();
        permissionCheck(memberId, leader);
    }

    private void permissionCheck(Long memberId, Long target) {
        if (memberId != target)
            throw new NoPermissionException("수정 권한이 없습니다.");
    }
}