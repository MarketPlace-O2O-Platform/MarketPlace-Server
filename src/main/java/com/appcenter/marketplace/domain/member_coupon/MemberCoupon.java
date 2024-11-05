package com.appcenter.marketplace.domain.member_coupon;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "member_coupon")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Builder
    public MemberCoupon(Member member, Coupon coupon, Boolean isUsed) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = isUsed;
    }

    public void usedToggle() {
        this.isUsed = !this.isUsed;
    }

}
