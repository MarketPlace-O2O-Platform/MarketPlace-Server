package com.appcenter.marketplace.domain.member_payback.dto.res;

import lombok.Getter;

@Getter
public class RetentionStatsRes {
    private final Long completedMemberCount;  // 환급 완료 경험 회원 수
    private final Long retainedMemberCount;   // 환급 완료 후 재다운로드한 회원 수
    private final Double retentionRate;       // 재다운로드율 (%)

    private RetentionStatsRes(Long completedMemberCount, Long retainedMemberCount) {
        this.completedMemberCount = completedMemberCount;
        this.retainedMemberCount = retainedMemberCount;
        this.retentionRate = completedMemberCount > 0
                ? Math.round((double) retainedMemberCount / completedMemberCount * 1000.0) / 10.0
                : 0.0;
    }

    public static RetentionStatsRes of(Long completedMemberCount, Long retainedMemberCount) {
        return new RetentionStatsRes(completedMemberCount, retainedMemberCount);
    }
}
