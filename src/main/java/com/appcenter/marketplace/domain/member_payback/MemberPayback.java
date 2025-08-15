package com.appcenter.marketplace.domain.member_payback;


import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.payback.Payback;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "member_payback_coupon")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberPayback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_payback", nullable = false)
    private Boolean isPayback; // 환급 여부

    @Column(name = "is_expired", nullable = false)
    private Boolean isExpired;

    @Column(nullable = true)
    private String receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payback_coupon_id", nullable = false)
    private Payback payback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    private MemberPayback (Boolean isPayback, Boolean isExpired, String receipt, Payback payback, Member member) {
        this.isPayback = isPayback;
        this.isExpired = isExpired;
        this.receipt = receipt;
        this.payback = payback;
        this.member = member;
    }

    public void updateReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void completePayback() {
        this.isPayback = true;
    }
}
