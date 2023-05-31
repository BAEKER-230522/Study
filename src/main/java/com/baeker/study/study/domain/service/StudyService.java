package com.baeker.study.study.domain.service;

import com.baeker.study.base.exception.InvalidDuplicateException;
import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.in.reqDto.UpdateLeaderReqDto;
import com.baeker.study.study.in.reqDto.UpdateReqDto;
import com.baeker.study.study.out.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    /**
     * ** CREATE METHOD **
     * create
     */

    //-- create --//
    @Transactional
    public Study create(CreateReqDto dto) {
        try {
            this.findByName(dto.getName());
            throw new InvalidDuplicateException("이미 존재하는 name 입니다.");
        } catch (NotFoundException e) {
        }

        Study study = Study.createStudy(dto.getName(), dto.getAbout(), dto.getCapacity(), dto.getLeader());
        return studyRepository.save(study);
    }


    /**
     * ** UPDATE METHOD **
     * update name, about, capacity
     * update leader
     */

    //-- update name, about, capacity --//
    @Transactional
    public Study update(UpdateReqDto dto) {

        try {
            this.findByName(dto.getName());
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


    /**
     * ** SELECT METHOD **
     * find by name
     * find all + page
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
        sorts.add(Sort.Order.desc("xp"));

        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return studyRepository.findAll(pageable);
    }

    //-- find by id --//
    public Study findById(Long id) {
        Optional<Study> byId = studyRepository.findById(id);

        if (byId.isPresent())
            return byId.get();

        throw new NotFoundException("존재하지 않는 id 입니다.");
    }
}
