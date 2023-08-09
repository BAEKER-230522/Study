package com.baeker.study.domain.studyRule.service;

import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.base.exception.NumberInputException;
import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.domain.email.EmailService;
import com.baeker.study.domain.email.MailDto;
import com.baeker.study.domain.problem.ProblemService;
import com.baeker.study.domain.problem.dto.CreateProblem;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.request.ModifyStudyRuleRequest;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.repository.StudyRuleDslRepositoryImp;
import com.baeker.study.domain.studyRule.repository.StudyRuleRepository;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.global.feign.dto.MemberDto;
import com.baeker.study.global.feign.RuleClient;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StudyRuleService {

    private final StudyRuleRepository studyRuleRepository;

    private final StudyService studyService;

    private final EmailService emailService;

    private final SnapshotRepository snapshotRepository;

    private final StudyRuleDslRepositoryImp studyRuleDslRepositoryImp;

    private final MemberClient memberClient;
    private final RuleClient ruleClient;

    private final ProblemService problemService;
    /**
     * 생성
     */

    @Transactional
    public Long create(CreateStudyRuleRequest request) {
        Study study = studyService.findById(request.getStudyId());
        StudyRule studyRule = StudyRule.create(request);
        LocalDate now = LocalDate.now();
        studyRule.setMission(now);
        studyRule.setStudy(studyRule,study);
        addProblem(request.getCreateProblemList(), studyRule);
        studyRuleRepository.save(studyRule);
        return studyRule.getId();
    }

    private void addProblem(List<CreateProblem> createProblems, StudyRule studyRule) {
        problemService.createProblem(createProblems, studyRule);
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

    public StudyRule getStudyRule(Long studyRuleId) {
        return studyRuleRepository.findById(studyRuleId)
                .orElseThrow(() -> new NotFoundException("아이디를 확인해주세요"));}

    public StudyRule getStudyRule(String name) {
        return studyRuleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("이름을 확인해주세요"));}

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

    public void setStatus(Long id, boolean status) {
        StudyRule studyRule = getStudyRule(id);
        studyRule.setStatus(status);
    }

    /**
     * 하루 1회 미션 진행상태 업데이트
     */
    @Scheduled(cron = "0 1 0 * * *")
    @Transactional
    protected void setMission() {
        LocalDate now = LocalDate.now();
        List<StudyRule> all = getAll();
        for (StudyRule studyRule : all) {
            studyRule.setMission(now);
        }
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
    public void updateStudySolved(Long id) throws NotFoundException {
        StudyRule studyRule = getStudyRule(id);
        Study study = studyRule.getStudy();

        String studyName = studyRule.getStudy().getName();
        RuleDto rule = getRule(studyRule.getRuleId());

        int todayCount = 0;
        int ruleCount = rule.getCount();
        String difficulty = rule.getDifficulty();
        List<StudySnapshot> allSnapshot = null;
        try {
            allSnapshot = studyService.findAllSnapshot(study);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("스냅샷이 없습니다.");
        }

        switch (difficulty) {
            case "BRONZE" -> todayCount = allSnapshot.get(0).getBronze();
            case "SILVER" -> todayCount = allSnapshot.get(0).getSilver(); //TODO: 오타 수정되면 다시
            case "GOLD" -> todayCount = allSnapshot.get(0).getGold();
            case "PLATINUM" -> todayCount = allSnapshot.get(0).getPlatinum();
            case "DIAMOND" -> todayCount = allSnapshot.get(0).getDiamond();
            case "RUBY" -> todayCount = allSnapshot.get(0).getRuby();
        }

        if (todayCount >= ruleCount) {
            setStatus(studyRule.getId(), true);
            AddXpReqDto addXpReqDto = new AddXpReqDto();
            addXpReqDto.setId(studyRule.getStudy().getId());
            addXpReqDto.setXp(getXp(studyRule));
            studyService.addXp(addXpReqDto);
            log.debug("study xp ++");
        } else {
            setStatus(studyRule.getId(), false);
            List<MyStudy> myStudies = studyRule.getStudy().getMyStudies();
            for (MyStudy myStudy : myStudies) {
                Long memberId = myStudy.getMember();
                RsData<MemberDto> member = memberClient.getMember(memberId);
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
        RsData<RuleDto> rule = ruleClient.getRule(id);
        return rule.getData();
    }

    public List<StudyRule> getStudyRuleFromStudy(Long studyId) {
        return studyRuleDslRepositoryImp.findStudyRuleFromStudy(studyId);
    }
}

