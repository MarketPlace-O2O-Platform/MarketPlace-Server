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
    private Integer stock;
    private Boolean isHidden;
    private Boolean isAvailable;
    private Boolean isMemberIssued;

    // 사장님 매장 쿠폰 페이징 조회
    @QueryProjection
    @Builder
    public CouponRes(Long couponId, String couponName, String couponDescription, LocalDateTime deadLine, Integer stock, Boolean isHidden) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.deadLine = deadLine;
        this.stock = stock;
        this.isHidden = isHidden;
    }

    // 매장 별 쿠폰 페이징 조회
    @QueryProjection
    public CouponRes(Long couponId, String couponName, String couponDescription, LocalDateTime deadLine, Boolean isAvailable, Boolean isMemberIssued) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.deadLine = deadLine;
        this.isAvailable= isAvailable;
        this.isMemberIssued= isMemberIssued;
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
