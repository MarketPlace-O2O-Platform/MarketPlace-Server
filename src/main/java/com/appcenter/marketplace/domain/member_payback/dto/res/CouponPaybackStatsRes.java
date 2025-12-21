package com.appcenter.marketplace.domain.member_payback.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponPaybackStatsRes {
    private final Double avgCouponDownloadPerMember;
    private final Double paybackRate;

    @Builder
    public CouponPaybackStatsRes(Double avgCouponDownloadPerMember, Double paybackRate) {
        this.avgCouponDownloadPerMember = avgCouponDownloadPerMember;
        this.paybackRate = paybackRate;
    }

    public static CouponPaybackStatsRes of(Double avgCouponDownloadPerMember, Double paybackRate) {
        return CouponPaybackStatsRes.builder()
                .avgCouponDownloadPerMember(avgCouponDownloadPerMember)
                .paybackRate(paybackRate)
                .build();
    }
}