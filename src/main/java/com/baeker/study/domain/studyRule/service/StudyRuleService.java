package com.baeker.study.domain.studyRule.service;

import com.baeker.study.base.exception.NotFoundException;
import com.baeker.study.base.rsdata.RsData;
import com.baeker.study.domain.email.EmailService;
import com.baeker.study.domain.email.MailDto;
import com.baeker.study.domain.studyRule.dto.ProblemNumberDto;
import com.baeker.study.domain.studyRule.dto.query.StudyRuleQueryDto;
import com.baeker.study.domain.studyRule.dto.request.CreateStudyRuleRequest;
import com.baeker.study.domain.studyRule.dto.request.ModifyStudyRuleRequest;
import com.baeker.study.domain.studyRule.entity.Mission;
import com.baeker.study.domain.studyRule.entity.Status;
import com.baeker.study.domain.studyRule.entity.StudyRule;
import com.baeker.study.domain.studyRule.repository.StudyRuleDslRepositoryImp;
import com.baeker.study.domain.studyRule.repository.StudyRuleRepository;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problem.Problem;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problem.ProblemService;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.ProblemStatus;
import com.baeker.study.domain.studyRule.studyRuleRelationship.problemStatus.ProblemStatusRepository;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.PersonalStudyRule;
import com.baeker.study.domain.studyRule.studyRuleRelationship.studyRuleStatus.PersonalStudyRuleRepository;
import com.baeker.study.global.feign.MemberClient;
import com.baeker.study.global.feign.RuleClient;
import com.baeker.study.global.feign.dto.MemberDto;
import com.baeker.study.global.feign.dto.RuleDto;
import com.baeker.study.myStudy.domain.entity.MyStudy;
import com.baeker.study.study.domain.entity.Study;
import com.baeker.study.study.domain.service.StudyService;
import com.baeker.study.study.out.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private final ProblemStatusRepository problemStatusRepository;
    private final PersonalStudyRuleRepository personalStudyRuleRepository;

    /**
     * 생성
     */

    @Transactional
    public Long create(CreateStudyRuleRequest request) {
        Study study = studyService.findById(request.getStudyId());
        //studyRule 생성
        StudyRule studyRule = StudyRule.create(request);
        LocalDate now = LocalDate.now();
        studyRule.setMission(now);
        studyRule.setStudy(studyRule, study);

        // Problem 생성
        List<Long> problemIds = problemService.createProblem(request.getCreateProblemList());
        //PersonalStudyRule 생성
        List<PersonalStudyRule> personalStudyRule = createPersonalStudyRule(study.getMyStudies(), studyRule);
        // ProblemStatus 생성
        List<ProblemStatus> problemStatus = createProblemStatus(personalStudyRule, problemIds);
        /**
         * 관계 맵핑
         * problem 엔 status 넣기
         * personalStudyRule problemStatus 넣기
         */

        studyRuleRepository.save(studyRule);
        return studyRule.getId();
    }

    @Transactional
    public List<PersonalStudyRule> createPersonalStudyRule(List<MyStudy> myStudies, StudyRule studyRule) {
        List<PersonalStudyRule> personalStudyRules = new ArrayList<>();
        for (MyStudy myStudy : myStudies) {
            PersonalStudyRule personalStudyRule = PersonalStudyRule.create(myStudy.getMember(), studyRule);
            personalStudyRules.add(personalStudyRule);
            personalStudyRule.addStudyRule(personalStudyRule);
//            personalStudyRuleRepository.save(personalStudyRule);
        }
        return personalStudyRules;
    }

    @Transactional
    public List<ProblemStatus> createProblemStatus(List<PersonalStudyRule> personalStudyRules, List<Long> problemIds) {
        List<ProblemStatus> problemStatuses = new ArrayList<>();
        List<Problem> problems = new ArrayList<>();
        for (Long problemId : problemIds) {
            Problem problem = problemService.getProblem(problemId);
            problems.add(problem);
        }
        for (Problem problem : problems) {
            for (PersonalStudyRule personalStudyRule : personalStudyRules) {
                ProblemStatus problemStatus = ProblemStatus.create(personalStudyRule, problem);
                problemStatuses.add(problemStatus);
                problemStatus.addProblem();
                problemStatus.addPersonalStudyRule();
//                problemStatusRepository.save(problemStatus);
            }
        }
        return problemStatuses;
    }


    /**
     * 수정
     */

    @Transactional
    public void modify(StudyRule studyRule, ModifyStudyRuleRequest request) {
        StudyRule modifyRule = studyRule.toBuilder()
                .name(request.getName())
                .about(request.getAbout())
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
                .orElseThrow(() -> new NotFoundException("아이디를 확인해주세요"));
    }
    public StudyRuleQueryDto getStudyRuleQueryDto(Long studyRuleId) {
        return studyRuleDslRepositoryImp.findStudyRule(studyRuleId);
    }


    public StudyRule getStudyRule(String name) {
        return studyRuleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("이름을 확인해주세요"));
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
//    public Integer getXp(Long id) {
//        StudyRule studyRule = getStudyRule(id);
//        Long ruleId = studyRule.getRuleId();
//        RuleDto rule = getRule(ruleId);
//        return rule.getXp();
//    }


//    public Integer getXp(StudyRule studyRule) {
//        return getXp(studyRule.getId());
//    }

    /**
     * study 에있는 멤버들 status 확인하여
     * StudyStatus 갱신
     * @param studyRuleId
     */
    public boolean isPersonalMissionStatus(Long studyRuleId) {
        boolean status = true;
        StudyRule studyRule = getStudyRule(studyRuleId);
        List<PersonalStudyRule> personalStudyRules = studyRule.getPersonalStudyRules();
        for (PersonalStudyRule personalStudyRule : personalStudyRules) {
            if (personalStudyRule.getStatus().equals(Status.FAIL)) {
                status = false;
                break;
            }
        }
        return status;
    }
    public void setStatus(Long studyRuleId) {
        StudyRule studyRule = getStudyRule(studyRuleId);
        studyRule.setStatus(isPersonalMissionStatus(studyRuleId));
    }

    /**
     * 하루 1회 미션 진행상태 업데이트
     */
    @Scheduled(cron = "0 1 0 * * *")
    @Transactional
    public void setMission() {
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

        String name = updates.get("name");
        String about = updates.get("about");

        if (name != null) {
            request.setName(name);
        }
        if (about != null) {
            request.setAbout(about);
        }
        modify(studyRule, request);
    }

    /**
     * @param studyRuleId = studyRuleId
     */
//    public void updateStudySolved(Long studyRuleId) throws NotFoundException {
//        StudyRule studyRule = getStudyRule(studyRuleId);
//        Study study = studyRule.getStudy();
//
//        String studyName = studyRule.getStudy().getName();
//
//        int todayCount = 0;
//        int ruleCount = rule.getCount();
//        String difficulty = rule.getDifficulty();
//        List<StudySnapshot> allSnapshot = allSnapshot = studyService.findAllSnapshot(study);
//        StudySnapshot studySnapshot = null;
//        try {
//            studySnapshot = allSnapshot.get(0);
//        } catch (IndexOutOfBoundsException e) {
//            throw new NotFoundException("스냅샷이 없습니다.");
//        }
//
//        switch (difficulty) {
//            case "BRONZE" -> todayCount = studySnapshot.getBronze();
//            case "SILVER" -> todayCount = studySnapshot.getSilver(); //TODO: 오타 수정되면 다시
//            case "GOLD" -> todayCount = studySnapshot.getGold();
//            case "PLATINUM" -> todayCount = studySnapshot.getPlatinum();
//            case "DIAMOND" -> todayCount = studySnapshot.getDiamond();
//            case "RUBY" -> todayCount = studySnapshot.getRuby();
//        }
//
//        if (todayCount >= ruleCount) {
//            setStatus(studyRule.getId());
//            AddXpReqDto addXpReqDto = new AddXpReqDto();
//            addXpReqDto.setId(studyRule.getStudy().getId());
//            addXpReqDto.setXp(getXp(studyRule));
//            studyService.addXp(addXpReqDto);
//            log.debug("study xp ++");
//        } else {
//            setStatus(studyRule.getId());
//            List<MyStudy> myStudies = studyRule.getStudy().getMyStudies();
//            for (MyStudy myStudy : myStudies) {
//                Long memberId = myStudy.getMember();
//                RsData<MemberDto> member = memberClient.getMember(memberId);
//                emailService.mailSend(new MailDto(member.getData().email(),
//                        String.format("%s 미션 실패 메일입니다.", studyName), "오늘 하루도 화이팅 입니다 :)"));
//            }
//            log.debug("xp 추가안됨 ");
//        }
//    }

    /**
     * Rule 받아오기
     *
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

    /**
     * member -> studyId -> studyRule -> personalStudyRule -> problemStatus
     * 위 순서로 get 하고 개인별 문제 갱신
     * @param studyId
     */
    public void updateProblemStatus(Long studyId, List<ProblemNumberDto> problemNumberDtos) {
        List<StudyRule> studyRuleFromStudy = getStudyRuleFromStudy(studyId);
        for (StudyRule studyRule : studyRuleFromStudy) {
            Mission mission = studyRule.getMission();
            // 미션 상태가 종료되고 메일이 아직 안보내졌다면 메일 보내고 무시
            if (mission.equals(Mission.DONE) && studyRule.getStatus().equals(Status.FAIL) && !studyRule.isSendMail()) {
                sendMail(studyRule);
                continue;
            }
            else if (!mission.equals(Mission.ACTIVE)) continue; // 활성화 상태가 아니라면 무시

            for (PersonalStudyRule personalStudyRule : studyRule.getPersonalStudyRules()) {
                for (ProblemStatus problemStatus : personalStudyRule.getProblemStatuses()) {
                    for (ProblemNumberDto problemNumberDto : problemNumberDtos) {
                        if (setProblemStatus(problemStatus, problemNumberDto)) break;
                    }
                }
            }
            setStatus(studyRule.getId());
        }
    }

    private boolean setProblemStatus(ProblemStatus problemStatus, ProblemNumberDto problemNumberDto) {
        if (problemStatus.getProblem().getProblemNumber() == Integer.parseInt(problemNumberDto.problemNumber())) {
            problemStatus.addMemory(Integer.parseInt(problemNumberDto.memory()));
            problemStatus.addTime(Integer.parseInt(problemNumberDto.time()));
            problemStatus.updateStatus(true);
            return true;
        }
        return false;
    }

    private void sendMail(StudyRule studyRule) {
        List<MyStudy> myStudies = studyRule.getStudy().getMyStudies();
        String studyName = studyRule.getName();
        for (MyStudy myStudy : myStudies) {
            Long memberId = myStudy.getMember();
            RsData<MemberDto> member = memberClient.getMember(memberId);
            emailService.mailSend(new MailDto(member.getData().email(),
                    String.format("%s 미션 실패 메일입니다.", studyName), "오늘 하루도 화이팅 입니다 :)"));
        }
        studyRule.setSendMail();
    }

}

