package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponRes {
    private final Long couponId;
    private final String couponName;
    private final String couponDescription;
    private final LocalDateTime deadLine;
    private int stock;
    private Boolean isHidden;

    @QueryProjection
    @Builder
    public CouponRes(Long couponId, String couponName, String couponDescription, LocalDateTime deadLine, int stock, Boolean isHidden) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.deadLine = deadLine;
        this.stock = stock;
        this.isHidden = isHidden;
    }

    @QueryProjection
    public CouponRes(Long couponId, String couponName, String couponDescription, LocalDateTime deadLine) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.deadLine = deadLine;
    }

    public static CouponRes toDto(Coupon coupon){
        return CouponRes.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getName())
                .couponDescription(coupon.getDescription())
                .deadLine(coupon.getDeadLine())
                .stock(coupon.getStock())
                .isHidden(coupon.getIsHidden())
                .build();
    }

}
