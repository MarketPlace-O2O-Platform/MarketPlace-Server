package com.appcenter.marketplace.domain.coupon;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponUpdateReq;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "coupon")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "dead_line",nullable = false)
    private LocalDateTime deadLine;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;

    @Builder
    public Coupon(String name, String description, LocalDateTime deadLine, Integer stock, Market market, Boolean isHidden, Boolean isDeleted) {
        this.name = name;
        this.description = description;
        this.deadLine = deadLine;
        this.stock = stock;
        this.market = market;
        this.isHidden = isHidden;
        this.isDeleted = isDeleted;
    }

    public void update(CouponUpdateReq couponUpdateReq) {
        this.name = couponUpdateReq.getCouponName();
        this.description = couponUpdateReq.getDescription();
        this.deadLine = couponUpdateReq.getDeadLine();
        this.stock = couponUpdateReq.getStock();
    }

    public void updateHidden() {
        this.isHidden = !this.isHidden;
    }

    public void reduce() {
        this.stock--;

        if (this.stock == 0) {
            this.isHidden = true;
        }
    }

    public void deleteCoupon(){
        this.isDeleted = true;
        this.isHidden = true;
    }

}
