package com.appcenter.marketplace.domain.member_payback.dto.res;

import lombok.Getter;

@Getter
public class AvgProcessingTimeRes {
    private final Long measuredCount;      // 측정 대상 완료 건수 (receiptSubmittedAt 있는 것)
    private final Double avgHours;         // 평균 처리 시간 (시간)
    private final Double avgDays;          // 평균 처리 시간 (일)

    private AvgProcessingTimeRes(Long measuredCount, Double avgSeconds) {
        this.measuredCount = measuredCount;
        if (avgSeconds == null || avgSeconds <= 0) {
            this.avgHours = 0.0;
            this.avgDays = 0.0;
        } else {
            this.avgHours = Math.round(avgSeconds / 3600.0 * 10.0) / 10.0;
            this.avgDays = Math.round(avgSeconds / 86400.0 * 100.0) / 100.0;
        }
    }

    public static AvgProcessingTimeRes of(Long measuredCount, Double avgSeconds) {
        return new AvgProcessingTimeRes(measuredCount, avgSeconds);
    }
}
