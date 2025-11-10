package com.appcenter.marketplace.domain.coupon.dto.res;


import com.appcenter.marketplace.domain.member_coupon.CouponType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class TopPopularCouponRes {

    private final Long couponId;
    private final String couponName;
    private final Long marketId;
    private final String marketName;
    private final String thumbnail;
    private Long issuedCount;
    private final CouponType couponType;


    // 인기 쿠폰 TOP 캐싱용 조회
    @QueryProjection
    public TopPopularCouponRes(Long couponId, String couponName, Long marketId, String marketName, String thumbnail, Long issuedCount, CouponType couponType) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.issuedCount = issuedCount;
        this.couponType = couponType;
    }
}
