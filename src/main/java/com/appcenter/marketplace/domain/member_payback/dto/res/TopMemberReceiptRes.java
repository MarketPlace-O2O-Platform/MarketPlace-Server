package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TopMemberReceiptRes {
    private final Long memberId;
    private final Long receiptCount;

    @QueryProjection
    public TopMemberReceiptRes(Long memberId, Long receiptCount) {
        this.memberId = memberId;
        this.receiptCount = receiptCount;
    }
}
