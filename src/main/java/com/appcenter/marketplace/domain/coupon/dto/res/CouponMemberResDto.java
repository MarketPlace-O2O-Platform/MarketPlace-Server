package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponMemberResDto {
    private final Long couponId;
    private final String couponName;
    private final String description;
    private final LocalDateTime deadLine;

    @QueryProjection
    public CouponMemberResDto(Long couponId, String couponName, String description, LocalDateTime deadLine) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
    }
}
