package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.global.exception.NotFoundException;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.application.port.in.MyStudyDeleteUseCase;
import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.out.reqDto.DeleteMyStudyReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.MEMBER;

@Service
@Transactional
@RequiredArgsConstructor
public class MyStudyDeleteService implements MyStudyDeleteUseCase {

    private final MyStudyRepositoryPort repository;
    private final MemberClient memberClient;

    @Override
    public void myStudy(Long memberId, MyStudy myStudy) {
        if (myStudy.getStatus() != MEMBER)
            pendingPermissionCheck(memberId, myStudy);
        else
            memberPermissionCheck(memberId, myStudy);

        deleteMember(myStudy);
    }

    private void pendingPermissionCheck(Long memberId, MyStudy myStudy) {
        if (
                memberId == myStudy.getMember() ||
                memberId == myStudy.getStudy().getLeader()
        )
            return;

        throw new NoPermissionException("권한이 없습니다.");
    }

    private void memberPermissionCheck(Long memberId, MyStudy myStudy) {
        if (memberId == myStudy.getStudy().getLeader())
            throw new NoPermissionException("스터디 장은 스터디를 탈퇴할 수 없습니다.");

        if (memberId != myStudy.getMember())
            throw new NoPermissionException("권한이 없습니다.");
    }

    @Override
    public void dropOut(Long memberId, MyStudy myStudy) {
        isMember(myStudy);
        isLeader(memberId, myStudy);

        deleteMember(myStudy);
    }

    private void isMember(MyStudy myStudy) {
        if (myStudy.getStatus() != MEMBER)
            throw new NotFoundException("스터디의 정회원이 아닙니다.");
    }

    private void isLeader(Long memberId, MyStudy myStudy) {
        if (memberId != myStudy.getStudy().getLeader())
            throw new NoPermissionException("권한이 없습니다.");
    }

    private void deleteMember(MyStudy myStudy) {
        DeleteMyStudyReqDto dto = new DeleteMyStudyReqDto(
                myStudy.getMember(),
                myStudy.getId()
        );
        memberClient.deleteMyStudy(dto);
        repository.delete(myStudy);
    }
}
