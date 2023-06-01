package com.baeker.study.myStudy.domain.service;

import com.baeker.study.base.exception.InvalidDuplicateException;
import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.base.exception.OverLimitedException;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.myStudy.in.reqDto.InviteMyStudyReqDto;
import com.baeker.study.myStudy.in.reqDto.JoinMyStudyReqDto;
import com.baeker.study.myStudy.out.MyStudyQueryRepository;
import com.baeker.study.myStudy.out.MyStudyRepository;
import com.baeker.study.study.domain.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.MEMBER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyStudyService {

    private final MyStudyRepository myStudyRepository;
    private final MyStudyQueryRepository myStudyQueryRepository;

    /**
     * ** CREATE METHOD **
     * Study 개설시 create
     * join study
     * invite member
     */

    //-- create 개설시 create --//
    @Transactional
    public MyStudy create(Long id, Study study) {
        MyStudy myStudy = MyStudy.createNewStudy(id, study);
        return myStudyRepository.save(myStudy);
    }

    //-- join study --//
    @Transactional
    public MyStudy join(JoinMyStudyReqDto dto, Study study) {

        invalidCreateMyStudy(dto.getMember(), study);

        return myStudyRepository.save(
                MyStudy.joinStudy(dto.getMember(), study, dto.getMsg())
        );
    }

    //-- invite member --//
    @Transactional
    public MyStudy invite(InviteMyStudyReqDto dto, Study study) {

        invalidInviter(dto.getInviter(), study);
        invalidCreateMyStudy(dto.getInvitee(), study);

        return myStudyRepository.save(
                MyStudy.inviteStudy(dto.getInvitee(), study, dto.getMsg())
        );
    }

    // 최대자 권한 확인 //
    private void invalidInviter(Long inviter, Study study) {
        MyStudy myStudy = this.duplicationCheck(inviter, study);

        if (myStudy.getStatus() != MEMBER)
            throw new IllegalStateException("초대 권한이 없습니다.");
    }

    // my study 생성 가능 여부 확인 //
    private void invalidCreateMyStudy(Long member, Study study) {
        try {
            this.duplicationCheck(member, study);
            throw new InvalidDuplicateException("이미 가입 또는 가입 대기중입니다.");
        } catch (NotFoundException e) {
        }

        if (study.getCapacity() == study.getMyStudies().size())
            throw new OverLimitedException("최대 인원에 도달한 스터디입니다.");
    }


    /**
     * ** SELECT METHOD **
     * find by member & study
     */

    //-- find by member & study --//
    public MyStudy duplicationCheck(Long member, Study study) {
        List<MyStudy> myStudies = myStudyQueryRepository.findByMemberStudy(member, study);

        if (myStudies.size() == 0)
            throw new NotFoundException("MyStudy 가 존재하지 않습니다.");

        return myStudies.get(0);
    }

    public MyStudy findById(Long id) {
        Optional<MyStudy> byId = myStudyRepository.findById(id);

        if (byId.isPresent())
            return byId.get();

        throw  new NotFoundException("MyStudy 가 존재하지 않습니다.");
    }


    /**
     * ** UPDATE METHOD **
     * modify msg
     */

    //-- modify msg --//
    @Transactional
    public void modifyMsg(MyStudy myStudy, String msg) {
        myStudy.modifyMsg(msg);
    }

    @Transactional
    public void accept(MyStudy myStudy) {

        if (myStudy.getStatus() == MEMBER)
            throw new IllegalStateException("이미 승인된 스터디 맴버입니다.");

        myStudy.accept();
    }

    /**
     * ** DELETE METHOD **
     * delete my study
     */

    //-- delete my study --//
    @Transactional
    public void delete(MyStudy myStudy) {
        myStudyRepository.delete(myStudy);
    }

}
