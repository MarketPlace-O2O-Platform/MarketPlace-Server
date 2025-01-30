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

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @Column(name="is_expired", nullable = false)
    private Boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Builder
    public MemberCoupon(Member member, Coupon coupon, Boolean isUsed, Boolean isExpired) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = isUsed;
        this.isExpired = isExpired;
    }

    public void usedToggle() {
        this.isUsed = !this.isUsed;
    }
}
