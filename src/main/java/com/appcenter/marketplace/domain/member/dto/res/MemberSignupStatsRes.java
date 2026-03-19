package com.appcenter.marketplace.domain.member.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSignupStatsRes {
    private final Long totalMemberCount;
    private final Long todaySignupCount;
    private final Long sevenDayChangeCount;

    @Builder
    public MemberSignupStatsRes(Long totalMemberCount, Long todaySignupCount, Long sevenDayChangeCount) {
        this.totalMemberCount = totalMemberCount;
        this.todaySignupCount = todaySignupCount;
        this.sevenDayChangeCount = sevenDayChangeCount;
    }

    public static MemberSignupStatsRes of(Long totalMemberCount, Long todaySignupCount, Long sevenDayChangeCount) {
        return MemberSignupStatsRes.builder()
                .totalMemberCount(totalMemberCount)
                .todaySignupCount(todaySignupCount)
                .sevenDayChangeCount(sevenDayChangeCount)
                .build();
    }
}