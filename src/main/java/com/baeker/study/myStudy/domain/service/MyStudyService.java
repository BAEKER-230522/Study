package com.baeker.study.myStudy.domain.service;

import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.out.MyStudyRepository;
import com.baeker.study.study.domain.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyStudyService {

    private final MyStudyRepository myStudyRepository;

    /**
     * ** CREATE METHOD **
     * Study 개설시 create
     */

    //-- create 개설시 create --//
    @Transactional
    public MyStudy create(Long id, Study study) {
        MyStudy myStudy = MyStudy.createNewStudy(id, study);
        return myStudyRepository.save(myStudy);
    }


}
