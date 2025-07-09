package com.appcenter.marketplace.domain.member.dto.res;


import com.appcenter.marketplace.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRes {
    private final Long studentId;
    private final Integer cheerTicket;
    private final String account;
    private final String accountNumber;

    @Builder
    public MemberRes(Long studentId, Integer cheerTicket, String account, String accountNumber) {
        this.studentId = studentId;
        this.cheerTicket = cheerTicket;
        this.account = account;
        this.accountNumber = accountNumber;
    }

    public static MemberRes toDto(Member member) {
        return MemberRes.builder()
                .studentId(member.getId())
                .cheerTicket(member.getCheerTicket())
                .account(member.getAccount())
                .accountNumber(member.getAccountNumber())
                .build();
    }
}
