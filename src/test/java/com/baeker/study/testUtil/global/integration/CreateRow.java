package com.baeker.study.testUtil.global.integration;

import com.baeker.study.myStudy.adapter.in.reqDto.JoinReqDto;
import com.baeker.study.study.adapter.in.reqDto.StudyCreateReqDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.baeker.study.testUtil.global.integration.MockMvcRequest.patchReq;
import static com.baeker.study.testUtil.global.integration.MockMvcRequest.postReq;

public class CreateRow {

    public static void createStudy(MockMvc mvc, int name, Integer capacity, String jwt) throws Exception {
        StudyCreateReqDto reqDto = new StudyCreateReqDto("study" + name, "", capacity);
        postReq(mvc, "/web/study/v2/study", jwt, reqDto);
    }

    public static void addMemberToStudy(MockMvc mvc, Long studyId, String jwt) throws Exception {
        JoinReqDto dto = new JoinReqDto();
        dto.setStudyId(studyId);
        dto.setMsg("");
        postReq(mvc, "/web/my-study/v2/join", jwt, dto);

        patchReq(mvc, "/web/my-study/v2/accept/{studyId}", jwt, studyId);
    }

    public static void joinStudy(MockMvc mvc, Long studyId, String jwt) throws Exception {
        JoinReqDto dto = new JoinReqDto();
        dto.setStudyId(studyId);
        dto.setMsg("");
        postReq(mvc, "/web/my-study/v2/join", jwt, dto);
    }
}