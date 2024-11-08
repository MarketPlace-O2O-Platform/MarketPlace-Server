package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;

import java.util.List;

public interface MarketRepositoryCustom {
    public List<MarketDetailsResDto> findMarketDetailResDtoListById(Long marketId);

    public List<MarketResDto> findMarketResDtoList(Long marketId, Integer size);

    public List<MarketResDto> findMarketResDtoListByCategory(Long marketId, Integer size, String major);
}
