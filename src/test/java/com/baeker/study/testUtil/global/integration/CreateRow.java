package com.baeker.study.testUtil.global.integration;

import com.baeker.study.myStudy.adapter.in.reqDto.JoinReqDto;
import com.baeker.study.study.adapter.in.reqDto.AcceptReqDto;
import com.baeker.study.study.adapter.in.reqDto.StudyCreateReqDto;
import com.baeker.study.study.in.resDto.CreateResDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.baeker.study.testUtil.global.integration.MockMvcRequest.*;

public class CreateRow {

    public static CreateResDto createStudy(MockMvc mvc, int name, Integer capacity, String jwt) throws Exception {
        StudyCreateReqDto reqDto = new StudyCreateReqDto("study" + name, "", capacity);
        ResultActions result = postReq(mvc, "/web/study/v2/study", jwt, reqDto);

        return toResDto(result, CreateResDto.class);
    }

    public static void accept(MockMvc mvc, Long studyId, Long targetId, String jwt) throws Exception {
        AcceptReqDto dto = new AcceptReqDto(studyId, targetId);
        patchReq(mvc, "/web/my-study/v2/accept", jwt, dto);
    }

    public static void joinStudy(MockMvc mvc, Long studyId, String jwt) throws Exception {
        JoinReqDto dto = new JoinReqDto();
        dto.setStudyId(studyId);
        dto.setMsg("");
        postReq(mvc, "/web/my-study/v2/join", jwt, dto);
    }
}