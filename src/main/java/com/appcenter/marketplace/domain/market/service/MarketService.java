package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageResDto;

public interface MarketService {

    MarketDetailsResDto getMarketDetails(Long marketId);

    MarketPageResDto getMarketPage(Long marketId, Integer size, String major);

    MarketPageResDto getMemberFavoriteMarketPage(Long memberId, Long marketId, Integer size);

    MarketPageResDto getTopFavoriteMarketPage(Long marketId, Integer size);
}
