package com.appcenter.marketplace.domain.beta.dto.res;

import com.appcenter.marketplace.domain.beta.BetaMarket;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BetaMarketRes {
    private final Long id;
    private final String marketName;
    private final String couponName;
    private final String couponDetail;
    private final String image;

    @Builder
    public BetaMarketRes(Long id, String marketName, String couponName, String couponDetail, String image) {
        this.id = id;
        this.marketName = marketName;
        this.couponName = couponName;
        this.couponDetail = couponDetail;
        this.image = image;
    }

    public static BetaMarketRes of(BetaMarket betaMarket){
        return BetaMarketRes.builder()
                .id(betaMarket.getId())
                .marketName(betaMarket.getMarketName())
                .couponName(betaMarket.getCouponName())
                .couponDetail(betaMarket.getCouponDetail())
                .image(betaMarket.getImage())
                .build();
    }
}
