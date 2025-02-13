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
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member (Long id, Integer cheerTicket, Role role) {
        this.id = id;
        this.cheerTicket = cheerTicket;
        this.role = role;
    }

    public void reduceTicket() {
        this.cheerTicket--;
    }
}

