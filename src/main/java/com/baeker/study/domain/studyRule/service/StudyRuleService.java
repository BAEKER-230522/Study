package com.baeker.study.domain.studyRule.service;

import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.base.exception.NumberInputException;
import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.domain.email.EmailService;
import com.baeker.study.domain.email.MailDto;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.request.ModifyStudyRuleRequest;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.repository.StudyRuleRepository;
import com.baeker.study.global.feign.Feign;
import com.baeker.study.global.feign.dto.MemberDto;
import com.baeker.study.global.feign.dto.RuleDto;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.entity.StudySnapshot;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.reqDto.AddXpReqDto;
import com.baeker.study.study.out.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StudyRuleService {

    private final StudyRuleRepository studyRuleRepository;

    private final StudyService studyService;

    private final EmailService emailService;

    private final SnapshotRepository snapshotRepository;

    private final Feign feign;
    /**
     * 생성
     */

    @Transactional
    public Long create(CreateStudyRuleRequest request) {
        Study study = studyService.findById(request.getStudyId());
        StudyRule studyRule = StudyRule.create(request);
        studyRule.setStudy(studyRule,study);

        studyRuleRepository.save(studyRule);
        return studyRule.getId();
    }

    /**
     * 수정
     */

    @Transactional
    public void modify(StudyRule studyRule, ModifyStudyRuleRequest request) {
        StudyRule modifyRule = studyRule.toBuilder()
                .name(request.getName())
                .about(request.getAbout())
                .ruleId(request.getRuleId())
                .build();
        studyRuleRepository.save(modifyRule);
        RsData.of("S-1", "수정되었습니다.", modifyRule);
    }

    /**
     * 삭제
     */

    @Transactional
    public void delete(StudyRule studyRule) {
        studyRuleRepository.delete(studyRule);
    }

    /**
     * 조회
     */

    public StudyRule getStudyRule(Long id) {
        Optional<StudyRule> rs = studyRuleRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("아이디를 확인해주세요");
        }
        return rs.get();
    }

    public StudyRule getStudyRule(String name) {
        Optional<StudyRule> rs = studyRuleRepository.findByName(name);
        if (rs.isEmpty()) {
            throw new NotFoundException("이름을 확인해주세요");
        }
        return rs.get();
    }

    // StudyRuleId -> StudyId
    public Long getStudyId(Long id) {
        return getStudyRule(id).getStudy().getId();
    }

    public List<StudyRule> getAll() {
        return studyRuleRepository.findAll();
    }




    /**
     * 검증
     */
//TODO: 세션, jwt, 쿠키 등 확인해야함
//    public RsData<Study> verificationLeader(Rq rq, Long id) {
//        Study rsData = studyService.findById(id);
//        if (rsData.isSuccess()) {
//            if (rsData.getData().getLeader().equals(rq.getMember().getNickName())) {
//                return RsData.of("S-1", "리더 입니다." , rsData.getData());
//            }
//        }
//        return RsData.of("F-1" , "리더가 아닙니다.");
//    }

    /**
     * xp 반환
     * param studyRuleId
     */
    public Integer getXp(Long id) {
        StudyRule studyRule = getStudyRule(id);
        Long ruleId = studyRule.getRuleId();
        RuleDto rule = getRule(ruleId);
        return rule.getXp();
    }

    public Integer getXp(StudyRule studyRule) {
        return getXp(studyRule.getId());
    }

    public void setMission(Long id, boolean mission) {
        StudyRule studyRule = getStudyRule(id);
        studyRule.setMission(mission);
    }

    public void updateStudyRule(Long id, Map<String, String> updates) {
        StudyRule studyRule = getStudyRule(id);
        ModifyStudyRuleRequest request = new ModifyStudyRuleRequest();

        request.setName(studyRule.getName());
        request.setAbout(studyRule.getAbout());
        request.setRuleId(studyRule.getRuleId());

        String name = updates.get("name");
        String about = updates.get("about");
        String ruleId = updates.get("ruleId");

        if (name != null) {
            request.setName(name);
        }
        if (about != null) {
            request.setAbout(about);
        }
        if (ruleId != null) {
            try {
                request.setRuleId(Long.parseLong(ruleId));
            } catch (NumberFormatException e) {
                throw new NumberInputException("숫자로 입력해주세요");
            }
        }
        modify(studyRule, request);
    }

    /**
     *
     * @param id = studyRuleId
     *
     */
    @Transactional
    public void whenstudyEventType(Long id) throws NotFoundException {
        StudyRule studyRule = getStudyRule(id);
        String studyName = studyRule.getStudy().getName();
        RuleDto rule = getRule(studyRule.getRuleId());

        int todayCount = 0;
        int ruleCount = rule.getCount();
        String difficulty = rule.getDifficulty();


        List<StudySnapshot> allSnapshot = studyService.findAllSnapshot(studyRule.getStudy());

        switch (difficulty) {
            case "BRONZE" -> todayCount = allSnapshot.get(0).getBronze();
            case "SILVER" -> todayCount = allSnapshot.get(0).getSliver(); //TODO: 오타 수정되면 다시
            case "GOLD" -> todayCount = allSnapshot.get(0).getGold();
            case "PLATINUM" -> todayCount = allSnapshot.get(0).getPlatinum();
            case "DIAMOND" -> todayCount = allSnapshot.get(0).getDiamond();
            case "RUBY" -> todayCount = allSnapshot.get(0).getRuby();
        }

        if (todayCount >= ruleCount) {
            setMission(studyRule.getId(), true);
            AddXpReqDto addXpReqDto = new AddXpReqDto();
            addXpReqDto.setId(studyRule.getStudy().getId());
            addXpReqDto.setXp(getXp(studyRule));
            studyService.addXp(addXpReqDto);
            log.debug("study xp ++");
        } else {
            setMission(studyRule.getId(), false);
            List<MyStudy> myStudies = studyRule.getStudy().getMyStudies();
            for (MyStudy myStudy : myStudies) {
                Long memberId = myStudy.getMember();
                RsData<MemberDto> member = feign.getMember(memberId);
                emailService.mailSend(new MailDto(member.getData().email(),
                        String.format("%s 미션 실패 메일입니다.", studyName), "오늘 하루도 화이팅 입니다 :)"));
            }
            log.debug("xp 추가안됨 ");
        }
    }

    /**
     * Rule 받아오기
     * @param id
     * @return
     * @throws ParseException
     */
    public RuleDto getRule(Long id) {
        RsData<RuleDto> rule = feign.getRule(id);
        return rule.getData();
    }
}

