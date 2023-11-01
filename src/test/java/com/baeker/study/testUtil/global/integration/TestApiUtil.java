package com.baeker.study.testUtil.global.integration;

import com.baeker.study.global.dto.reqDto.InviteReqDto;
import com.baeker.study.global.dto.reqDto.JoinReqDto;
import com.baeker.study.global.dto.reqDto.AcceptReqDto;
import com.baeker.study.global.dto.reqDto.StudyCreateReqDto;
import com.baeker.study.study.legacy.in.resDto.CreateResDto;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.baeker.study.testUtil.global.integration.MockMvcRequest.*;

public class TestApiUtil {

    public static CreateResDto createStudy(MockMvc mvc, String url, int name, String jwt) throws Exception {
        StudyCreateReqDto reqDto = new StudyCreateReqDto("study" + name, "", 10);
        ResultActions result = postReq(mvc, url + "/v2/study", jwt, reqDto);

        return toResDto(result, CreateResDto.class);
    }

    public static void accept(MockMvc mvc, String url, Long studyId, Long targetId, String jwt) throws Exception {
        AcceptReqDto dto = new AcceptReqDto(studyId, targetId);
        patchReq(mvc, url + "/v2/accept", jwt, dto);
    }

    public static void joinStudy(MockMvc mvc, String url, Long studyId, String jwt) throws Exception {
        JoinReqDto dto = new JoinReqDto();
        dto.setStudyId(studyId);
        dto.setMsg("");
        postReq(mvc, url + "/v2/join", jwt, dto);
    }

    public static void inviteStudy(MockMvc mvc, String url, Long studyId, Long invitee, String jwt) throws Exception {
        InviteReqDto dto = new InviteReqDto();
        dto.setStudyId(studyId);
        dto.setInvitee(invitee);
        dto.setMsg("");
        postReq(mvc, url + "/v2/invite", jwt, dto);
    }
}