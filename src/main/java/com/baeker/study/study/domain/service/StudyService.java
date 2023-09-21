package com.baeker.study.study.domain.service;

import com.baeker.study.base.exception.InvalidDuplicateException;
import com.baeker.study.base.exception.NoPermissionException;
import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.reqDto.*;
import com.baeker.study.study.in.resDto.MemberResDto;
import com.baeker.study.study.in.resDto.SolvedCountReqDto;
import com.baeker.study.study.in.resDto.StudyResDto;
import com.baeker.study.study.out.SnapshotQueryRepository;
import com.baeker.study.study.out.SnapshotRepository;
import com.baeker.study.study.out.StudyQueryRepository;
import com.baeker.study.study.out.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.baeker.study.myStudy.domain.entity.StudyStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;
    private final StudyQueryRepository studyQueryRepository;
    private final MyStudyService myStudyService;
    private final SnapshotRepository snapshotRepository;
    private final SnapshotQueryRepository snapshotQueryRepository;
    private final MemberClient memberClient;

    /**
     * ** CREATE METHOD **
     * create
     * event : study 생성시 snapshot 생성
     */

    //-- create --//
    @Transactional
    public MyStudy create(CreateReqDto dto) {
        try {
            this.findByName(dto.getName());
            throw new InvalidDuplicateException("이미 존재하는 name 입니다.");
        } catch (NotFoundException e) {
        }

        MemberResDto memberDto = memberClient.findById(dto.getMember()).getData();

        if (memberDto.getBaekJoonName() == null)
            throw new NoPermissionException("백준 연동이 안된 user 입니다.");

        Study study = Study.createStudy(dto.getName(), dto.getAbout(), dto.getCapacity(), memberDto.getId());
        Study saveStudy = studyRepository.save(study);

        return myStudyService.create(dto.getMember(), saveStudy);
    }


    /**
     * ** UPDATE METHOD **
     * update name, about, capacity
     * update leader
     * add xp
     * event : member 의 study 해결한 문제 추가
     * update snapshot
     * update ranking
     */

    //-- update name, about, capacity --//
    @Transactional
    public Study update(UpdateReqDto dto) {

        try {
            this.findByName(dto.getName());
            throw new InvalidDuplicateException("이미 존재하는 name 입니다.");
        } catch (NotFoundException e) {
        }
        
        Study study = this.findById(dto.getId());
        Study modifyStudy = study.modifyStudy(dto.getName(), dto.getAbout(), dto.getCapacity());

        return studyRepository.save(modifyStudy);
    }

    //-- update leader --//
    @Transactional
    public Study updateLeader(UpdateLeaderReqDto dto) {
        Study study = this.findById(dto.getStudyId());

        if (study.getLeader() != dto.getOldLeader())
            throw new NoPermissionException("스터디 장 만 스터디 장을 위임할 수 있습니다.");
        Study modifyLeader = study.modifyLeader(dto.getNewLeader());

        return studyRepository.save(modifyLeader);
    }

    //-- add xp --//
    @Transactional
    public Study addXp(AddXpReqDto dto) {
        Study study = this.findById(dto.getId());
        study.xpUp(dto.getXp());

        return study;
    }

    //-- study 해결한 문제 업데이트 --//
    @Transactional
    public void addSolveCount(SolvedCountReqDto dto) {
        List<Study> studies = studyQueryRepository.findByMember(dto.getId());
        if (studies.size() == 0) return;

        String today = LocalDateTime.now().getDayOfWeek().toString();
        BaekjoonDto resDto = new BaekjoonDto(dto);

        for (Study study : studies) {
            Study saveStudy = studyRepository.save(study.updateSolvedCount(dto));
            this.updateSnapshot(saveStudy, resDto, today);
        }
    }

    public void updateSnapshotTest(Study study, BaekjoonDto dto, String today) {
        updateSnapshot(study, dto, today);
    }

    // update snapshot //
    private void updateSnapshot(Study study, BaekjoonDto dto, String today) {
        List<StudySnapshot> snapshots = study.getSnapshots();

        if (snapshots.size() == 0 || !snapshots.get(snapshots.size() - 1).getDayOfWeek().equals(today)) {
            StudySnapshot snapshot = StudySnapshot.create(study, dto, today);
            snapshotRepository.save(snapshot);

        }else{
            StudySnapshot snapshot = snapshots.get(snapshots.size() - 1).update(dto);
            snapshotRepository.save(snapshot);
        }

        if (snapshots.size() == 8) {
            StudySnapshot snapshot = snapshots.get(0);
            snapshots.remove(snapshot);
            snapshotRepository.delete(snapshot);
        }
    }

    //-- ranking 수동 업데이트 --//
    @Transactional
    public void updateRanking() {
        List<Study> studyList = studyQueryRepository.findStudyRanking();

        for (int i = 0; i < studyList.size(); i++)
            studyList.get(i).updateRanking(i + 1);
    }


    /**
     * ** SELECT METHOD **
     * find by name
     * find all + page
     * find all
     * find by id
     * find by member
     * find all snapshot by study / 삭제 예정
     * find study order by ranking
     * find by input
     */

    //-- find by name --//
    public Study findByName(String name) {
        Optional<Study> byName = studyRepository.findByName(name);

        if (byName.isPresent())
            return byName.get();

        throw new NotFoundException("존재하지 않는 name 입니다.");
    }

    //-- find all + page --//
    public Page<Study> findAll(int page) {
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return studyRepository.findAll(pageable);
    }

    //-- find all --//
    public List<Study> findAll() {
        return studyRepository.findAll();
    }

    //-- find by id --//
    public Study findById(Long id) {
        Optional<Study> byId = studyRepository.findById(id);

        if (byId.isPresent())
            return byId.get();

        throw new NotFoundException("존재하지 않는 id 입니다.");
    }

    //-- find by member --//
    public List<Study> findByMember(Long member, int status) {
        StudyStatus studyStatus;
        switch (status) {
            case 1 -> studyStatus = MEMBER;
            case 2 -> studyStatus = PENDING;
            default -> studyStatus = INVITING;
        }

        return studyQueryRepository.findByMember(member, studyStatus);
    }

    //-- find study order by ranking --//
    public List<StudyResDto> findAllOrderByRanking(int page, int content) {
        return studyQueryRepository.findAllOrderByRanking(page, content);
    }

    //-- find all snapshot by study / 삭제 예정--//
    public List<StudySnapshot> findAllSnapshot(Study study) {
        return snapshotQueryRepository.findAllByStudy(study);
    }


    //-- find by input --//
    public List<StudyResDto> findByInput(String input, int page, int content) {
        return studyQueryRepository.findByInput(input, page, content);
    }

    /**
     * ** BUISINESS METHOD **
     * feign test
     */

    //-- feign test --//
    public MemberResDto feignTest(Long id) {
        return memberClient.findById(id).getData();
    }

    //-- delete study --//
    @Transactional
    public void delete(DeleteStudyReqDto dto) {
        Study study = this.findById(dto.getStudyId());
        isStudyLeader(dto.getMemberId(), study);

        deleteSnapshot(study);
        deleteMyStudy(study);
        studyRepository.delete(study);
    }

    private void isStudyLeader(Long memberId, Study study) {
        if (study.getLeader() != memberId)
            throw new NoPermissionException("권한이 없습니다.");
    }

    private void deleteSnapshot(Study study) {
        List<StudySnapshot> snapshots = study.getSnapshots();
        for (StudySnapshot snapshot : snapshots)
            snapshotRepository.delete(snapshot);

        snapshots.clear();
    }

    private void deleteMyStudy(Study study) {
        List<MyStudy> myStudies = study.getMyStudies();
        for (MyStudy myStudy : myStudies)
            myStudyService.delete(myStudy);
    }
}
