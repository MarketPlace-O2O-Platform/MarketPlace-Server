package com.appcenter.marketplace.domain.tempMarket.repository;

import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;

import java.util.List;

public interface TempMarketRepositoryCustom {
    List<TempMarketRes> findMarketList(Long memberId, Long marketId, Integer size);
    List<TempMarketRes> findMarketListByCategory(Long memberId, Long marketId, Integer size, String category);
    List<TempMarketRes> findUpcomingMarketList(Long memberId, Long marketId, Integer cheerCount, Integer size);
}
