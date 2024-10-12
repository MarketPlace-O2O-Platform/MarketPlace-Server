package com.appcenter.marketplace.domain.member.dto.res;


import com.appcenter.marketplace.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginResDto {
    private final Long studentId;

    @Builder
    public MemberLoginResDto(Long studentId) {
        this.studentId = studentId;
    }

    public static MemberLoginResDto toDto(Member member) {
        return MemberLoginResDto.builder()
                .studentId(member.getId())
                .build();
    }
}
