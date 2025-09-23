package com.appcenter.marketplace.domain.member.dto.res;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminMemberRes {
    private final Long studentId;
    private final Integer cheerTicket;
    private final String account;
    private final String accountNumber;
    private final Role role;
    private final String fcmToken;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    @Builder
    public AdminMemberRes(Long studentId, Integer cheerTicket, String account, String accountNumber,
                         Role role, String fcmToken, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.studentId = studentId;
        this.cheerTicket = cheerTicket;
        this.account = account;
        this.accountNumber = accountNumber;
        this.role = role;
        this.fcmToken = fcmToken;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static AdminMemberRes toDto(Member member) {
        return AdminMemberRes.builder()
                .studentId(member.getId())
                .cheerTicket(member.getCheerTicket())
                .account(member.getAccount())
                .accountNumber(member.getAccountNumber())
                .role(member.getRole())
                .fcmToken(member.getFcmToken())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build();
    }
}