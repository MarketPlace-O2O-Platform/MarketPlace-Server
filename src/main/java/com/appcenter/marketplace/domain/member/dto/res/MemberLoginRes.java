package com.appcenter.marketplace.domain.member.dto.res;


import com.appcenter.marketplace.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginRes {
    private final Long studentId;
    private final Integer cheerTicket;

    @Builder
    public MemberLoginRes(Long studentId, Integer cheerTicket) {
        this.studentId = studentId;
        this.cheerTicket = cheerTicket;
    }

    public static MemberLoginRes toDto(Member member) {
        return MemberLoginRes.builder()
                .studentId(member.getId())
                .cheerTicket(member.getCheerTicket())
                .build();
    }
}
