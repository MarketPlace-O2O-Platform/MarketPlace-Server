package com.appcenter.marketplace.domain.payback;

import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.payback.dto.req.PaybackReq;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "payback_coupon")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;

    @Builder
    public Payback(String name, String description, Boolean isHidden, Boolean isDeleted, Market market) {
        this.name = name;
        this.description = description;
        this.isHidden = isHidden;
        this.isDeleted = isDeleted;
        this.market = market;
    }

    public void update(PaybackReq req){
        this.name = req.getCouponName();
        this.description = req.getDescription();
    }

    public void updateHidden() {
        this.isHidden = !this.isHidden;
    }

    public void softDeleteCoupon(){
        this.isDeleted = true;
        this.isHidden = true;
    }
}
