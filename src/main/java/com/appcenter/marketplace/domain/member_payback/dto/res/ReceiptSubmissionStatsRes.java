package com.appcenter.marketplace.domain.member_payback.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReceiptSubmissionStatsRes {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Long totalCount;
    private final List<ReceiptStatsDataPoint> breakdown;
}
