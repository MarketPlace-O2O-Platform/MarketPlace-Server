package com.appcenter.marketplace.domain.coupon.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 매장별 쿠폰 통계 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketCouponStatsRes {
    private Long marketId;
    private String marketName;
    private String categoryName;
    private Long totalCouponCount; // 총 쿠폰 수
    private Long downloadedCouponCount; // 다운로드된 쿠폰 수
    private Long usedCouponCount; // 사용된 쿠폰 수
    private Double usageRate; // 사용률 (사용/다운로드 * 100)
}
