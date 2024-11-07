package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageResDto;

public interface MarketRepositoryCustom {
    public MarketDetailsResDto findMarketDetailResDtoById(Long marketId);

    public MarketPageResDto findMarketPageResDto(Long marketId, Integer size);
}
