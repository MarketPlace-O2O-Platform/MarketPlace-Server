package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReceiptItemRes {
    private final Long memberPaybackId;
    private final String couponName;
    private final LocalDateTime issuedAt;
    private final LocalDateTime receiptSubmittedAt;
    private final String receipt;
    private final Boolean isPayback;

    @QueryProjection
    public ReceiptItemRes(Long memberPaybackId, String couponName,
                          LocalDateTime issuedAt, LocalDateTime receiptSubmittedAt,
                          String receipt, Boolean isPayback) {
        this.memberPaybackId = memberPaybackId;
        this.couponName = couponName;
        this.issuedAt = issuedAt;
        this.receiptSubmittedAt = receiptSubmittedAt;
        this.receipt = receipt;
        this.isPayback = isPayback;
    }
}
