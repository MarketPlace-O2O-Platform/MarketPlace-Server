package com.appcenter.marketplace.domain.member_payback.dto.res;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member_payback.MemberPayback;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReceiptRes {
    private final Long memberCouponId;
    private final String receipt;
    private final String account;
    private final String accountNumber;
    private final Boolean isUsed; // ==  isPayback

    @Builder
    @QueryProjection
    public ReceiptRes(Long memberCouponId, String receipt, String account, String accountNumber, Boolean isUsed) {
        this.memberCouponId = memberCouponId;
        this.receipt = receipt;
        this.account = account;
        this.accountNumber = accountNumber;
        this.isUsed = isUsed;
    }

    public static ReceiptRes toDto(MemberPayback memberPayback, Member member) {
        return ReceiptRes.builder()
                .memberCouponId(memberPayback.getId())
                .receipt(memberPayback.getReceipt())
                .account(member.getAccount())
                .accountNumber(member.getAccountNumber())
                .isUsed(memberPayback.getIsPayback())
                .build();
    }
}
