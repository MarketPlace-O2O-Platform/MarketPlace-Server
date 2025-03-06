package com.appcenter.marketplace.domain.beta;


import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "beta_market")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BetaMarket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="market_name",nullable = false)
    private String marketName;

    @Column(name="coupon_name",nullable = false)
    private String couponName;

    @Column(name="coupon_detail",nullable = false)
    private String couponDetail;

    @Column(nullable = false)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public BetaMarket(String marketName, String couponName, String couponDetail, LocalDateTime deadLine, Integer stock, Category category) {
        this.marketName = marketName;
        this.couponName = couponName;
        this.couponDetail = couponDetail;
        this.category = category;
    }

    public void updateimage(String image){
        this.image= image;
    }
}
