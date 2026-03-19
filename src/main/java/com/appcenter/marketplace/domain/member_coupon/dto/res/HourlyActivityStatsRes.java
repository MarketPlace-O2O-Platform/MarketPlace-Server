package com.appcenter.marketplace.domain.member_coupon.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 시간대별 활동 통계 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HourlyActivityStatsRes {
    private LocalDate date;
    private Integer hour; // 0-23
    private Long couponDownloadCount;
    private Long memberSignupCount;
    private Long paybackDownloadCount;
}
