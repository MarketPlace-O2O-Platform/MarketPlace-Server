package com.appcenter.marketplace.domain.favorite.service;

import com.appcenter.marketplace.domain.market.dto.res.MarketPageResDto;

public interface FavoriteService {

    void createOrDeleteFavorite(Long memberId, Long marketId);

    MarketPageResDto getFavoriteMarketPage(Long memberId, Long marketId, Integer size);
}
