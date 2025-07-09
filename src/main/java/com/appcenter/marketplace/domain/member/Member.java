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

    @Column(nullable = true)
    private String account;

    @Column(name = "account_number", nullable = true)
    private String accountNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "fcm_token",nullable = true)
    private String fcmToken;

    @Builder
    public Member (Long id, Integer cheerTicket, String account, String accountNumber, Role role, String fcmToken) {
        this.id = id;
        this.cheerTicket = cheerTicket;
        this.account=account;
        this.accountNumber=accountNumber;
        this.role = role;
        this.fcmToken=fcmToken;
    }

    public void reduceTicket() {
        this.cheerTicket--;
    }

    public void saveAccount(String account, String accountNumber){
        this.account=account;
        this.accountNumber=accountNumber;
    }

    public void deleteAccount(){
        this.account=null;
        this.accountNumber=null;
    }

    public void denyFcmToken() { this.fcmToken=null; }

    public void permitFcmToken(String fcmToken){
        this.fcmToken=fcmToken;
    }

    public void upgradePermission(){ this.role=Role.ROLE_ADMIN;}
}

