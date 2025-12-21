package com.appcenter.marketplace.domain.member.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberSignupStatsRes {
    private final Long todaySignupCount;
    private final Long sevenDayChangeCount;

    @Builder
    public MemberSignupStatsRes(Long todaySignupCount, Long sevenDayChangeCount) {
        this.todaySignupCount = todaySignupCount;
        this.sevenDayChangeCount = sevenDayChangeCount;
    }

    public static MemberSignupStatsRes of(Long todaySignupCount, Long sevenDayChangeCount) {
        return MemberSignupStatsRes.builder()
                .todaySignupCount(todaySignupCount)
                .sevenDayChangeCount(sevenDayChangeCount)
                .build();
    }
}