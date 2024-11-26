package com.appcenter.marketplace.domain.member.dto.res;


import com.appcenter.marketplace.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginRes {
    private final Long studentId;

    @Builder
    public MemberLoginRes(Long studentId) {
        this.studentId = studentId;
    }

    public static MemberLoginRes toDto(Member member) {
        return MemberLoginRes.builder()
                .studentId(member.getId())
                .build();
    }
}
