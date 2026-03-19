package com.appcenter.marketplace.domain.stats.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 실시간 방문자 통계 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorStatsRes {
    private LocalDate date;
    private Long uniqueVisitors;
    private Long totalPageViews;
    private Long newMemberSignups;
    private Long couponDownloads;
    private Long couponUsages;
    private Long paybackDownloads;
}
