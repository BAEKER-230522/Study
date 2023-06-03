package com.baeker.study.domain.studyRule.service;

import com.baeker.study.base.address.Address;
import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.base.exception.NumberInputException;
import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.domain.studyRule.dto.RuleDto;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.request.ModifyStudyRuleRequest;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.repository.StudyRuleRepository;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.in.reqDto.AddXpReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
    public Integer getXp(Long id) throws ParseException {
        StudyRule studyRule = getStudyRule(id);
        Long ruleId = studyRule.getRuleId();
        RuleDto rule = getRule(ruleId);
        return rule.getXp();
    }

    public Integer getXp(StudyRule studyRule) throws ParseException {
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
    public void whenstudyEventType(Long id) throws ParseException {
        StudyRule studyRule = getStudyRule(id);
        String studyName = studyRule.getStudy().getName();
        RuleDto rule = getRule(studyRule.getRuleId());
        int todayCount = 0;
        int ruleCount = rule.getCount();
        String difficulty = rule.getDifficulty();

        int yesterdayCount = studyRule.getStudy().solvedCount(); // 스터디 어제 푼 문제 수


        switch (difficulty) {
            //TODO: 오늘 값 - 내일 값
//            case "BRONZE" -> todayCount = studySnapShotRepository.findByStudyName(studyName).get(6).getBronze();
//            case "SILVER" -> todayCount = studySnapShotRepository.findByStudyName(studyName).get(6).getSliver();
//            case "GOLD" -> todayCount = studySnapShotRepository.findByStudyName(studyName).get(6).getGold();
//            case "PLATINUM" -> todayCount = studySnapShotRepository.findByStudyName(studyName).get(6).getPlatinum();
//            case "DIAMOND" -> todayCount = studySnapShotRepository.findByStudyName(studyName).get(6).getDiamond();
//            case "RUBY" -> todayCount = studySnapShotRepository.findByStudyName(studyName).get(6).getRuby();
        }

        if (todayCount >= ruleCount) {
            setMission(studyRule.getId(), true);
            AddXpReqDto addXpReqDto = new AddXpReqDto();
            addXpReqDto.setId(studyRule.getStudy().getId());
            addXpReqDto.setXp(getXp(studyRule));
            studyService.addXp(addXpReqDto);
            log.info("study xp ++");
        } else {
            setMission(studyRule.getId(), false);
            log.info("xp 추가안됨 ");
        }
    }

    /**
     * Rule 받아오기
     * @param id
     * @return
     * @throws ParseException
     */
    public RuleDto getRule(Long id) throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        String url = Address.RULE_URL + id;
        String strRule = restTemplate.getForObject(url, String.class);
        JSONParser jsonParser = new JSONParser();
        Object o = jsonParser.parse(strRule);

        JSONObject jo = (JSONObject) o;

        RuleDto ruleDto = new RuleDto();
        JSONObject json = (JSONObject) jo.get("data");
        ruleDto.setId((Long) json.get("id"));
        ruleDto.setName((String) json.get("name"));
        ruleDto.setAbout((String) json.get("about"));
        Long tempXp = (Long) json.get("xp");
        int intXp = tempXp.intValue();
        ruleDto.setXp(intXp);
        Long tempCount = (Long) json.get("count");
        int intCount = tempCount.intValue();
        ruleDto.setCount(intCount);
        ruleDto.setProvider((String) json.get("provider"));
        ruleDto.setDifficulty((String) json.get("difficulty"));
        return ruleDto;
    }
}

