package com.appcenter.marketplace.domain.member_payback.dto.res;

import lombok.Getter;

@Getter
public class FunnelStatsRes {
    private final Long totalDownloadCount;       // 전체 다운로드 수
    private final Long expiredCount;             // 만료 이탈 수 (영수증 미제출 만료)
    private final Long receiptSubmittedCount;    // 영수증 제출 수
    private final Long paybackCompletedCount;    // 환급 완료 수
    private final Double expiryRate;             // 만료 이탈율 (%)
    private final Double downloadToSubmitRate;   // 다운로드 → 제출 전환율 (%)
    private final Double submitToCompleteRate;   // 제출 → 환급 완료 전환율 (%)
    private final Double overallConversionRate;  // 전체 전환율: 다운로드 → 환급 완료 (%)

    private FunnelStatsRes(Long totalDownloadCount, Long expiredCount, Long receiptSubmittedCount, Long paybackCompletedCount) {
        this.totalDownloadCount = totalDownloadCount;
        this.expiredCount = expiredCount;
        this.receiptSubmittedCount = receiptSubmittedCount;
        this.paybackCompletedCount = paybackCompletedCount;
        this.expiryRate = totalDownloadCount > 0
                ? Math.round((double) expiredCount / totalDownloadCount * 1000.0) / 10.0
                : 0.0;
        this.downloadToSubmitRate = totalDownloadCount > 0
                ? Math.round((double) receiptSubmittedCount / totalDownloadCount * 1000.0) / 10.0
                : 0.0;
        this.submitToCompleteRate = receiptSubmittedCount > 0
                ? Math.round((double) paybackCompletedCount / receiptSubmittedCount * 1000.0) / 10.0
                : 0.0;
        this.overallConversionRate = totalDownloadCount > 0
                ? Math.round((double) paybackCompletedCount / totalDownloadCount * 1000.0) / 10.0
                : 0.0;
    }

    public static FunnelStatsRes of(Long totalDownloadCount, Long expiredCount, Long receiptSubmittedCount, Long paybackCompletedCount) {
        return new FunnelStatsRes(totalDownloadCount, expiredCount, receiptSubmittedCount, paybackCompletedCount);
    }
}
