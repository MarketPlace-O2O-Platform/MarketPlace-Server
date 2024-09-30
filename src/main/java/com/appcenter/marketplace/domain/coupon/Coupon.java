package com.appcenter.marketplace.domain.coupon;

import com.appcenter.marketplace.domain.coupon.dto.req.CouponHiddenReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponReqDto;
import com.appcenter.marketplace.domain.coupon.dto.req.CouponUpdateReqDto;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponHiddenResDto;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private LocalDateTime deadLine;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean isHidden;

    @Column(nullable = false)
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

    public void update(CouponUpdateReqDto couponUpdateReqDto) {
        this.name = couponUpdateReqDto.getCouponName();
        this.description = couponUpdateReqDto.getDescription();
        this.deadLine = couponUpdateReqDto.getDeadLine();
        this.stock = couponUpdateReqDto.getStock();
    }

    public void updateHidden(CouponHiddenReqDto couponHiddenReqDto) {
        this.isHidden = couponHiddenReqDto.getHidden();
    }

    public void deleteCoupon(){
        this.isDeleted = true;
        this.isHidden = true;
    }

}
