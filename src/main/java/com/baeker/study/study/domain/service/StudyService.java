package com.baeker.study.study.domain.service;

import com.baeker.study.base.exception.InvalidDuplicateException;
import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.domain.entity.StudyStatus;
import com.baeker.study.myStudy.domain.service.MyStudyService;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.event.AddSolvedCountEvent;
import com.baeker.study.study.in.reqDto.*;
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

    /**
     * ** CREATE METHOD **
     * create
     */

    //-- create --//
    @Transactional
    public MyStudy create(CreateReqDto dto) {
        try {
            this.findByName(dto.getName());
            throw new InvalidDuplicateException("이미 존재하는 name 입니다.");
        } catch (NotFoundException e) {
        }

        Study study = Study.createStudy(dto.getName(), dto.getAbout(), dto.getCapacity(), dto.getLeader());
        Study saveStudy = studyRepository.save(study);
        return myStudyService.create(dto.getMember(), saveStudy);
    }


    /**
     * ** UPDATE METHOD **
     * update name, about, capacity
     * update leader
     * add xp
     * event : 해결한 문제 추가
     * update snapshot
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
        Study study = this.findById(dto.getId());
        Study modifyLeader = study.modifyLeader(dto.getLeader());

        return studyRepository.save(modifyLeader);
    }

    //-- add xp --//
    @Transactional
    public Study addXp(AddXpReqDto dto) {
        Study study = this.findById(dto.getId());
        study.xpUp(dto.getXp());

        return study;
    }

    //-- event : 해결한 문제 추가 --//
    public void addSolveCount(AddSolvedCountEvent event) {
        List<Study> studies = studyQueryRepository.findByMember(event.getMember());
        BaekjoonDto dto = new BaekjoonDto(event);

        for (Study study : studies) {
            Study saveStudy = studyRepository.save(study.updateSolvedCount(event));
            this.updateSnapshot(saveStudy, dto);
        }
    }

    // update snapshot //
    private void updateSnapshot(Study study, BaekjoonDto dto) {
        String today = LocalDateTime.now().getDayOfWeek().toString();
        List<StudySnapshot> snapshots = study.getSnapshots();

        if (snapshots.size() == 0 || !snapshots.get(0).getDayOfWeek().equals(today)) {
            StudySnapshot snapshot = StudySnapshot.create(study, dto, today);
            snapshotRepository.save(snapshot);

        }else{
            StudySnapshot snapshot = snapshots.get(0).update(dto);
            snapshotRepository.save(snapshot);
        }

        if (snapshots.size() > 7) {
            StudySnapshot snapshot = snapshots.get(7);
            snapshots.remove(snapshot);
            snapshotRepository.delete(snapshot);
        }
    }


    /**
     * ** SELECT METHOD **
     * find by name
     * find all + page
     * find all
     * find by id
     * find by member
     * find all snapshot by study
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

    //-- find all snapshot by study --//
    public List<StudySnapshot> findAllSnapshot(Study study) {
        return snapshotQueryRepository.findAllByStudy(study);
    }
}
