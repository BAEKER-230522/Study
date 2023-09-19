package com.baeker.study.study.application.service;

import com.baeker.study.global.exception.NotFoundException;
import com.baeker.study.study.application.port.in.StudyQueryUseCase;
import com.baeker.study.study.application.port.out.persistence.StudyQueryRepositoryPort;
import com.baeker.study.study.application.port.out.persistence.StudyRepositoryPort;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.in.resDto.StudyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyQueryService implements StudyQueryUseCase {

    private final StudyRepositoryPort repository;
    private final StudyQueryRepositoryPort queryRepository;

    @Override
    public Study byId(Long id) {
        Optional<Study> byId = repository.findById(id);

        if (byId.isPresent())
            return byId.get();

        throw new NotFoundException("존재하지 않는 id");
    }

    @Override
    public Study byName(String name) {
        Optional<Study> byName = repository.findByName(name);

        if (byName.isPresent())
            return byName.get();

        throw new NotFoundException("존재하지 않는 name");
    }

    @Override
    public List<Study> all() {
        List<Study> all = repository.findAll();

        if (all().size() != 0)
            return all;

        throw new NotFoundException("Study가 존재하지 않습니다.");
    }

    @Override
    public List<StudyResDto> byMemberId(Long memberId, int status) {
        return queryRepository.byMemberId(memberId, status);
    }

    @Override
    public List<StudyResDto> allOrderByRanking(int page, int content) {
        return queryRepository.allOrderByRanking(page, content);
    }

    @Override
    public List<StudyResDto> byInput(String input, int page, int content) {
        return queryRepository.byInput(input, page, content);
    }

    @Override
    public List<StudySnapshot> snapshotByStudy(Study study) {
        return null;
    }
}
