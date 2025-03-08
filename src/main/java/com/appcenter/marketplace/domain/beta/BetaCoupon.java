package com.appcenter.marketplace.domain.beta;


import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "beta_coupon")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BetaCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beta_market", nullable = false)
    private BetaMarket betaMarket;

    @Builder
    public BetaCoupon(Boolean isUsed, Member member, BetaMarket betaMarket) {
        this.isUsed = isUsed;
        this.member = member;
        this.betaMarket = betaMarket;
    }

    public void useCoupon() {
        this.isUsed = true;
    }
}
