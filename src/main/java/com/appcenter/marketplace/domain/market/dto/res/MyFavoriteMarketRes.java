package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyFavoriteMarketRes {
    private final Long marketId;
    private final String name;
    private final String description;
    private final String address;
    private final String thumbnail;
    private final Boolean isFavorite;
    private final Boolean isNewCoupon;
    private final LocalDateTime favoriteModifiedAt;

    @QueryProjection
    public MyFavoriteMarketRes(Long marketId, String name, String description, String address, String thumbnail, Boolean isFavorite, Boolean isNewCoupon, LocalDateTime favoriteModifiedAt) {
        this.marketId = marketId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
        this.isNewCoupon = isNewCoupon;
        this.favoriteModifiedAt = favoriteModifiedAt;
    }
}
