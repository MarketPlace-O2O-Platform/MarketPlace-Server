package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TopMemberReceiptRes {
    private final Long memberId;
    private final Long receiptCount;
    private final Long completedCount;
    private final Long pendingCount;

    @QueryProjection
    public TopMemberReceiptRes(Long memberId, Long receiptCount, Long completedCount, Long pendingCount) {
        this.memberId = memberId;
        this.receiptCount = receiptCount;
        this.completedCount = completedCount;
        this.pendingCount = pendingCount;
    }
}
