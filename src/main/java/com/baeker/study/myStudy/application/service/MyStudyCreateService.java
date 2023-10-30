package com.baeker.study.myStudy.application.service;

import com.baeker.study.global.exception.service.InvalidDuplicateException;
import com.baeker.study.global.exception.service.NoPermissionException;
import com.baeker.study.global.exception.service.OverLimitedException;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.global.dto.reqDto.InviteReqDto;
import com.baeker.study.myStudy.application.port.in.MyStudyCreateUseCase;
import com.baeker.study.myStudy.application.port.out.persistence.MyStudyRepositoryPort;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.out.reqDto.CreateMyStudyReqDto;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.domain.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MyStudyCreateService implements MyStudyCreateUseCase {

    private final MyStudyRepositoryPort repository;
    private final StudyQueryUseCase studyQueryUseCase;
    private final MemberClient memberClient;

    @Override
    public Long myStudy(Long memberId, Study study) {
        MyStudy myStudy = repository.save(
                MyStudy.createNewStudy(
                        memberId,
                        study
                ));
        return updateMember(myStudy);
    }

    @Override
    public Long join(Long memberId, Study study, String msg) {
        baekJoonConnectCheck(memberId);
        duplicateCheck(study, memberId);
        capacityCheck(study);

        MyStudy myStudy = repository.save(
                MyStudy.joinStudy(
                        memberId,
                        study,
                        msg
                ));
        return updateMember(myStudy);
    }

    @Override
    public Long invite(Long memberId, Study study, InviteReqDto dto) {
        baekJoonConnectCheck(dto.getInvitee());
        permissionCheck(study.getLeader(), memberId);
        duplicateCheck(study, dto.getInvitee());
        capacityCheck(study);

        MyStudy myStudy = repository.save(
                MyStudy.inviteStudy(
                        dto.getInvitee(),
                        study,
                        dto.getMsg()
                ));
        return updateMember(myStudy);
    }

    private Long updateMember(MyStudy myStudy) {
        CreateMyStudyReqDto reqDto = new CreateMyStudyReqDto(myStudy.getMember(), myStudy.getId());
        memberClient.updateMyStudy(reqDto);
        return myStudy.getId();
    }

    private void permissionCheck(Long leader, Long memberId) {
        if (leader != memberId)
            throw new NoPermissionException("초대 권한이 없습니다.");
    }

    private void baekJoonConnectCheck(Long memberId) {
        if (!memberClient.isConnectBaekJoon(memberId))
            throw new NoPermissionException("백준 연동이 안된 회원입니다.");
    }

    private void duplicateCheck(Study study, Long memberId) {
        for (MyStudy myStudy : study.getMyStudies())
            if (myStudy.getMember() == memberId)
                throw new InvalidDuplicateException("이미 가입 또는 가입 대기 중입니다.");
    }

    private void capacityCheck(Study study) {
        Integer capacity = study.getCapacity();
        int studyMemberCount = studyQueryUseCase.byMemberList(study).size();
        if (capacity == studyMemberCount)
            throw new OverLimitedException("최대 인원에 도달한 스터디입니다.");
    }
}
