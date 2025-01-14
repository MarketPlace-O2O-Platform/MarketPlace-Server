package com.appcenter.marketplace.domain.market.dto.res;

import lombok.Getter;

@Getter
public class MarketSearchRes {
    private final Long marketId;
    private final String name;
    private final String description;
    private final String address;
    private final String thumbnail;
    private final Boolean isNewCoupon;

    public MarketSearchRes(Long marketId, String name, String description, String address, String thumbnail, Boolean isNewCoupon) {
        this.marketId = marketId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isNewCoupon = isNewCoupon;
    }
}
