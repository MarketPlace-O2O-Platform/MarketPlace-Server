package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageResDto;

public interface MarketService {

    MarketDetailsResDto getMarketDetails(Long marketId);

    MarketPageResDto getMarketResPage(Long marketId, Integer size);
}
