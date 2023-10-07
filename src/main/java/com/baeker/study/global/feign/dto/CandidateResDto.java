package com.baeker.study.global.feign.dto;

import com.baeker.study.study.in.resDto.MemberResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class CandidateResDto {

    private int pendingSize;
    private int inviteSize;

    private List<MemberResDto> pending;
    private List<MemberResDto> inviting;

    public void addSize(int pendingSize, int inviteSize) {
        this.inviteSize = inviteSize;
        this.pendingSize = pendingSize;
    }

    public CandidateResDto(List<MemberResDto> pending, List<MemberResDto> inviting) {
        this.pending = pending;
        this.inviting = inviting;
        this.pendingSize = pending.size();
        this.inviteSize = inviting.size();
    }
}
