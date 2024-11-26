package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyFavoriteMarketResDto {
    private final Long marketId;
    private final String name;
    private final String description;
    private final String address;
    private final String thumbnail;
    private final Boolean isFavorite;
    private final LocalDateTime favoriteModifiedAt;

    @QueryProjection
    public MyFavoriteMarketResDto(Long marketId, String name, String description, String address, String thumbnail, Boolean isFavorite, LocalDateTime favoriteModifiedAt) {
        this.marketId = marketId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
        this.favoriteModifiedAt = favoriteModifiedAt;
    }
}
