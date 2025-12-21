package com.appcenter.marketplace.domain.member.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberSignupDailyStatsRes {
    private final List<DailySignupStats> dailyStats;

    @Builder
    public MemberSignupDailyStatsRes(List<DailySignupStats> dailyStats) {
        this.dailyStats = dailyStats;
    }

    public static MemberSignupDailyStatsRes of(List<DailySignupStats> dailyStats) {
        return MemberSignupDailyStatsRes.builder()
                .dailyStats(dailyStats)
                .build();
    }
}
