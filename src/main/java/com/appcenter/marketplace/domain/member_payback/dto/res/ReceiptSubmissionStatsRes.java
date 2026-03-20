package com.appcenter.marketplace.domain.member_payback.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReceiptSubmissionStatsRes {
    private final List<ReceiptStatsDataPoint> dailyBreakdown;   // 최근 7일, 날짜별
    private final List<ReceiptStatsDataPoint> weeklyBreakdown;  // 최근 4주, 주별
}
