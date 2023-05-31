package com.baeker.study.study.domain.service;

import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.in.reqDto.CreateReqDto;
import com.baeker.study.study.out.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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
        } catch (NotFoundException e) {
        }

        Study study = Study.createStudy(dto.getName(), dto.getAbout(), dto.getCapacity(), dto.getLeader());
        return studyRepository.save(study);
    }


    /**
     * ** SELECT METHOD **
     * find by name
     */

    //-- find by name --//
    public Study findByName(String name) {
        Optional<Study> byName = studyRepository.findByName(name);

        if (byName.isPresent())
            return byName.get();

        throw new NotFoundException("존재하지 않는 name 입니다.");
    }
}
