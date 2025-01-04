package com.appcenter.marketplace.domain.tempMarket.service;

import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketPageRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;

public interface TempMarketService {
    TempMarketPageRes<TempMarketRes> getMarketList(Long memberId, Long marketId, Integer size, String category);

    TempMarketPageRes<TempMarketRes> getUpcomingNearMarketList(Long memberId, Long marketId, Long cheerCount, Integer size);
}