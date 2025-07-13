package com.appcenter.marketplace.domain.payback.dto.res;

import com.appcenter.marketplace.domain.payback.Payback;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaybackRes {
    private final Long couponId;
    private final String couponName;
    private final String description;
    private final Boolean isHidden;

    @Builder
    @QueryProjection
    public PaybackRes(Long couponId, String couponName, String description, Boolean isHidden) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.description = description;
        this.isHidden = isHidden;
    }

    public static PaybackRes toDto(Payback payback) {
        return PaybackRes.builder()
                .couponId(payback.getId())
                .couponName(payback.getName())
                .description(payback.getDescription())
                .isHidden(payback.getIsHidden())
                .build();

    }
}
