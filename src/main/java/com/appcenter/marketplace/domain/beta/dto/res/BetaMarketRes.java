package com.appcenter.marketplace.domain.beta.dto.res;

import com.appcenter.marketplace.domain.beta.BetaMarket;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BetaMarketRes {
    private final Long betaMarketId;
    private final String marketName;
    private final String couponName;
    private final String couponDetail;
    private final String image;
    private final Boolean isPromise;

    @Builder
    public BetaMarketRes(Long betaMarketId, String marketName, String couponName, String couponDetail, String image, Boolean isPromise) {
        this.betaMarketId = betaMarketId;
        this.marketName = marketName;
        this.couponName = couponName;
        this.couponDetail = couponDetail;
        this.image = image;
        this.isPromise = isPromise;
    }

    public static BetaMarketRes of(BetaMarket betaMarket){
        return BetaMarketRes.builder()
                .betaMarketId(betaMarket.getId())
                .marketName(betaMarket.getMarketName())
                .couponName(betaMarket.getCouponName())
                .couponDetail(betaMarket.getCouponDetail())
                .image(betaMarket.getImage())
                .isPromise(betaMarket.getIsPromise())
                .build();
    }
}
