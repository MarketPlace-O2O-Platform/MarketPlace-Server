package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponRes {
    private final Long couponId;
    private final Long marketId;
    private final String couponName;
    private final String description;
    private final LocalDateTime deadLine;
    private final int stock;
    private final boolean isHidden;

    @QueryProjection
    @Builder
    public CouponRes(Long couponId, Long marketId, String couponName, String description, LocalDateTime deadLine, int stock, boolean isHidden) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.description = description;
        this.deadLine = deadLine;
        this.stock = stock;
        this.isHidden = isHidden;
        this.marketId = marketId;
    }

    public static CouponRes toDto(Coupon coupon){
        return CouponRes.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getName())
                .description(coupon.getDescription())
                .deadLine(coupon.getDeadLine())
                .stock(coupon.getStock())
                .isHidden(coupon.getIsHidden())
                .marketId(coupon.getMarket().getId())
                .build();
    }

}
