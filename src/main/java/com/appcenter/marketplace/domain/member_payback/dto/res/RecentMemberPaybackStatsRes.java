package com.appcenter.marketplace.domain.member_payback.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecentMemberPaybackStatsRes {
    private final Long recentSevenDaysMemberCount;
    private final Double avgPaybackCouponDownloadPerMember;

    @Builder
    public RecentMemberPaybackStatsRes(Long recentSevenDaysMemberCount, Double avgPaybackCouponDownloadPerMember) {
        this.recentSevenDaysMemberCount = recentSevenDaysMemberCount;
        this.avgPaybackCouponDownloadPerMember = avgPaybackCouponDownloadPerMember;
    }

    public static RecentMemberPaybackStatsRes of(Long recentSevenDaysMemberCount, Double avgPaybackCouponDownloadPerMember) {
        return RecentMemberPaybackStatsRes.builder()
                .recentSevenDaysMemberCount(recentSevenDaysMemberCount)
                .avgPaybackCouponDownloadPerMember(avgPaybackCouponDownloadPerMember)
                .build();
    }
}
