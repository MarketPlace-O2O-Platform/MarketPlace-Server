package com.appcenter.marketplace.domain.member_payback.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class MemberReceiptHistoryRes {
    private final Long memberId;
    private final String account;
    private final String accountNumber;
    private final List<ReceiptItemRes> receipts;

    public MemberReceiptHistoryRes(Long memberId, String account, String accountNumber, List<ReceiptItemRes> receipts) {
        this.memberId = memberId;
        this.account = account;
        this.accountNumber = accountNumber;
        this.receipts = receipts;
    }
}
