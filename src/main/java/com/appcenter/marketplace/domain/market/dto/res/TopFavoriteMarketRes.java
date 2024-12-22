package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TopFavoriteMarketRes {
    private final Long marketId;
    private final String marketName;
    private final String thumbnail;
    private final boolean isFavorite;

    @QueryProjection
    public TopFavoriteMarketRes(Long marketId, String marketName, String thumbnail, boolean isFavorite) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
    }
}
