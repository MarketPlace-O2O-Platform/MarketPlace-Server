package com.appcenter.marketplace.domain.favorite.repository;

import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;

import java.util.List;

public interface FavoriteRepositoryCustom {

    public List<MarketResDto> findMarketResDtoByFavorite(Long memberId, Long marketId, Integer size);

    public List<MarketResDto> findTopFavoriteMarketResDto(Long marketId, Integer size);
}
