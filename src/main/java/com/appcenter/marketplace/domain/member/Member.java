package com.appcenter.marketplace.domain.member;

import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "fcm_token",nullable = true)
    private String fcmToken;

    @Builder
    public Member (Long id, Integer cheerTicket, String fcmToken) {
        this.id = id;
        this.cheerTicket = cheerTicket;
        this.fcmToken= fcmToken;
    }

    public void reduceTicket() {
        this.cheerTicket--;
    }
}

