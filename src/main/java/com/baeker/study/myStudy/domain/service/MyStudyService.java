package com.baeker.study.myStudy.domain.service;

import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.myStudy.in.reqDto.MyStudyJoinReqDto;
import com.baeker.study.myStudy.out.MyStudyQueryRepository;
import com.baeker.study.myStudy.out.MyStudyRepository;
import com.baeker.study.study.domain.entity.Study;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     */

    //-- create 개설시 create --//
    @Transactional
    public MyStudy create(Long id, Study study) {
        MyStudy myStudy = MyStudy.createNewStudy(id, study);
        return myStudyRepository.save(myStudy);
    }

    //-- join study --//
    @Transactional
    public MyStudy join(MyStudyJoinReqDto dto, Study study) {


        MyStudy myStudy = MyStudy.joinStudy(dto.getMember(), study, dto.getMsg());

        return null;
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
}
