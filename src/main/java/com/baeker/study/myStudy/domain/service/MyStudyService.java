package com.baeker.study.myStudy.domain.service;

import com.baeker.study.global.exception.InvalidDuplicateException;
import com.baeker.study.global.exception.NoPermissionException;
import com.baeker.study.global.exception.NotFoundException;
import com.baeker.study.global.exception.OverLimitedException;
import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.global.feign.dto.CandidateResDto;
import com.baeker.study.global.feign.dto.MembersReqDto;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.in.reqDto.DropReqDto;
import com.baeker.study.myStudy.in.reqDto.InviteMyStudyReqDto;
import com.baeker.study.myStudy.in.reqDto.JoinMyStudyReqDto;
import com.baeker.study.myStudy.out.MyStudyQueryRepository;
import com.baeker.study.myStudy.out.MyStudyRepository;
import com.baeker.study.myStudy.out.reqDto.CreateMyStudyReqDto;
import com.baeker.study.myStudy.out.reqDto.DeleteMyStudyReqDto;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.resDto.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyStudyService {

    private final MyStudyRepository myStudyRepository;
    private final MyStudyQueryRepository myStudyQueryRepository;
    private final MemberClient memberClient;

    /**
     * ** CREATE METHOD **
     * Study 개설시 create
     * join study
     * invite member
     * member update 요청
     */

    //-- create 개설시 create --//
    @Transactional
    public MyStudy create(Long id, Study study) {
        MyStudy myStudy = MyStudy.createNewStudy(id, study);
        MyStudy saveMyStudy = myStudyRepository.save(myStudy);

        return updateMember(saveMyStudy);
    }

    //-- join study --//
    @Transactional
    public MyStudy join(JoinMyStudyReqDto dto, Study study) {

        MemberResDto memberDto = memberClient.findById(dto.getMember()).getData();

        if (memberDto.getBaekJoonName() == null)
            throw new NoPermissionException("백준 연동이 안된 user 입니다.");

        invalidCreateMyStudy(dto.getMember(), study);

        MyStudy myStudy = myStudyRepository.save(
                MyStudy.joinStudy(dto.getMember(), study, dto.getMsg())
        );

        return updateMember(myStudy);
    }

    //-- invite member --//
    @Transactional
    public MyStudy invite(InviteMyStudyReqDto dto, Study study) {

        invalidInviter(dto.getInviter(), study);
        invalidCreateMyStudy(dto.getInvitee(), study);

        MyStudy myStudy = myStudyRepository.save(
                MyStudy.inviteStudy(dto.getInvitee(), study, dto.getMsg())
        );

        return updateMember(myStudy);
    }

    // 초대자 권한 확인 //
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

    // member update 요청 //
    private MyStudy updateMember(MyStudy myStudy) {
        CreateMyStudyReqDto reqDto = new CreateMyStudyReqDto(myStudy.getMember(), myStudy.getId());

        RsData rsData = memberClient.updateMyStudy(reqDto);

        if (!rsData.isSuccess())
            throw new HttpClientErrorException(BAD_REQUEST);

        return myStudy;
    }

    /**
     * ** SELECT METHOD **
     * find by member & study
     * find by id
     * find by member study
     * find member by member id
     * 정회원 목록 조회
     * 가입 대기 회원 목록 조회
     * find all
     */

    //-- find by member & study --//
    public MyStudy duplicationCheck(Long member, Study study) {
        List<MyStudy> myStudies = myStudyQueryRepository.findByMemberStudy(member, study);

        if (myStudies.size() == 0)
            throw new NotFoundException("MyStudy 가 존재하지 않습니다.");

        return myStudies.get(0);
    }

    //-- find by id --//
    public MyStudy findById(Long id) {
        Optional<MyStudy> byId = myStudyRepository.findById(id);

        if (byId.isPresent())
            return byId.get();

        throw  new NotFoundException("MyStudy 가 존재하지 않습니다.");
    }

    //-- 정회원 목록 조회 --//
    public List<MemberResDto> findMemeberList(Study study) {
        List<Long> memberList = myStudyQueryRepository.findMemberList(study, MEMBER);

        RsData<List<MemberResDto>> rsData = memberClient.findMemberList(new MembersReqDto(memberList, "MEMBER"));

        if (rsData.isFail())
            throw new NotFoundException("가입한 회원이 없습니다.");

        return rsData.getData();
    }

    //-- find member by member id --//
    public MemberResDto findMemberByMemberId(Long memberId) {
        return memberClient.findById(memberId).getData();
    }

    //-- 가입 대기 회원 목록 조회 --//
    public CandidateResDto findCandidate(Study study) {
        List<Long> pendingList = myStudyQueryRepository.findMemberList(study, PENDING);
        List<Long> invitingList = myStudyQueryRepository.findMemberList(study, INVITING);

        List<MemberResDto> pendingDto = memberClient.findMemberList(new MembersReqDto(pendingList, "PENDING")).getData();
        List<MemberResDto> invitingDto = memberClient.findMemberList(new MembersReqDto(invitingList, "INVITING")).getData();

        return new CandidateResDto(pendingDto, invitingDto);
    }

    //-- find all --//
    public List<MyStudy> findAll() {
        return myStudyRepository.findAll();
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
     * member delete 요청
     * member 강퇴
     */

    //-- delete my study --//
    @Transactional
    public void delete(MyStudy myStudy) {
        myStudyRepository.delete(myStudy);
        deleteMember(myStudy);
    }


    // member delete 요청 //
    private void deleteMember(MyStudy myStudy) {
        DeleteMyStudyReqDto reqDto = new DeleteMyStudyReqDto(myStudy.getMember(), myStudy.getId());
        RsData rsData = memberClient.deleteMyStudy(reqDto);

        if (!rsData.isSuccess())
            throw new HttpClientErrorException(BAD_REQUEST);
    }

    //-- member 강퇴 --//
    @Transactional
    public void dropOut(DropReqDto dto, Study study) {

        MyStudy myStudy = this.duplicationCheck(dto.getDropMemberId(), study);
        this.duplicationCheck(dto.getLeaderId(), study);

        MemberResDto memberDto = memberClient.findById(dto.getLeaderId()).getData();
        if (!memberDto.getNickname().equals(study.getLeader()))
            throw new NoPermissionException(dto.getLeaderId() + "는 leader 가 아닙니다.");

        myStudyRepository.delete(myStudy);
        memberClient.deleteMyStudy(new DeleteMyStudyReqDto(myStudy.getMember(), myStudy.getId()));
    }

}
