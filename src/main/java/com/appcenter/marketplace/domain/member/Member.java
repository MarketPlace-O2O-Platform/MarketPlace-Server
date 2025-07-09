package com.appcenter.marketplace.domain.member;

import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "member")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Member extends BaseEntity {
    @Id
    private Long id;

    @Column(name = "cheer_ticket", nullable = false)
    private Integer cheerTicket;

    @Column(nullable = false)
    private String account;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "fcm_token",nullable = true)
    private String fcmToken;

    @Builder
    public Member (Long id, Integer cheerTicket, Role role, String fcmToken) {
        this.id = id;
        this.cheerTicket = cheerTicket;
        this.role = role;
        this.fcmToken=fcmToken;
    }

    public void reduceTicket() {
        this.cheerTicket--;
    }

    public void denyFcmToken() { this.fcmToken=null; }

    public void permitFcmToken(String fcmToken){
        this.fcmToken=fcmToken;
    }
}

