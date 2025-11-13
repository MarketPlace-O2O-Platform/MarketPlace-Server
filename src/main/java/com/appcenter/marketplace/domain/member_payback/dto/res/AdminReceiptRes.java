package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminReceiptRes {
    private final Long memberPaybackId;
    private final Long memberId;
    private final String couponName;
    private final LocalDateTime issuedAt;
    private final LocalDateTime receiptSubmittedAt;
    private final String receipt;
    private final Boolean isPayback;
    private final String account;
    private final String accountNumber;

    @QueryProjection
    public AdminReceiptRes(Long memberPaybackId, Long memberId, String couponName,
                          LocalDateTime issuedAt, LocalDateTime receiptSubmittedAt,
                          String receipt, Boolean isPayback, String account, String accountNumber) {
        this.memberPaybackId = memberPaybackId;
        this.memberId = memberId;
        this.couponName = couponName;
        this.issuedAt = issuedAt;
        this.receiptSubmittedAt = receiptSubmittedAt;
        this.receipt = receipt;
        this.isPayback = isPayback;
        this.account = account;
        this.accountNumber = accountNumber;
    }
}
