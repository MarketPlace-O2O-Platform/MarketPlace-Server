package com.appcenter.marketplace.domain.cheer;

import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "cheer")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cheer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temp_market_id", nullable = false)
    private TempMarket tempMarket;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Builder
    public Cheer(Member member, TempMarket tempMarket, Boolean isDeleted) {
        this.member = member;
        this.tempMarket = tempMarket;
        this.isDeleted = isDeleted;
    }

    public void toggleIsDeleted() {
        this.isDeleted = !this.isDeleted;
    }
}
