package com.appcenter.marketplace.domain.payback.dto.res;

import com.appcenter.marketplace.domain.member_coupon.CouponType;
import com.appcenter.marketplace.domain.payback.Payback;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaybackRes {
    private final Long couponId;
    private final String couponName;
    private final String couponDescription;
    private Boolean isHidden;
    private Boolean isMemberIssued;
    private CouponType couponType;
    private Long marketId;
    private String marketName;


    @Builder
    @QueryProjection
    public PaybackRes(Long couponId, String couponName, String couponDescription, Boolean isHidden, CouponType couponType) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.isHidden = isHidden;
        this.couponType = couponType;
    }

    // 관리자 환급 쿠폰 조회용
    @QueryProjection
    public PaybackRes(Long couponId, String couponName, String couponDescription, Boolean isHidden, CouponType couponType, Long marketId, String marketName) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.isHidden = isHidden;
        this.couponType = couponType;
        this.marketId = marketId;
        this.marketName = marketName;
    }

    // 유저용 ) 매장별 환급 쿠폰 조회
    @QueryProjection
    public PaybackRes(Long couponId, String couponName, String couponDescription, CouponType couponType, Boolean isMemberIssued) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.couponType = couponType;
        this.isMemberIssued = isMemberIssued;
    }

    public static PaybackRes toDto(Payback payback) {
        return new PaybackRes(
                payback.getId(),
                payback.getName(),
                payback.getDescription(),
                payback.getIsHidden(),
                CouponType.PAYBACK,
                payback.getMarket().getId(),
                payback.getMarket().getName()
        );
    }
}
