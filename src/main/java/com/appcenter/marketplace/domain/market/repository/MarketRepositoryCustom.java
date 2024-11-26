package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketRepositoryCustom {
    public List<MarketDetailsResDto> findMarketDetailsResDtoListById(Long marketId);

    public List<MarketResDto> findMarketResDtoList(Long memberId, Long marketId, Integer size);

    public List<MarketResDto> findMarketResDtoListByCategory(Long memberId, Long marketId, Integer size, String major);

    public List<MyFavoriteMarketResDto> findFavoriteMarketResDtoByMemberId(Long memberId, LocalDateTime lastModifiedAt, Integer size);

    public List<FavoriteMarketResDto> findFavoriteMarketResDto(Long memberId, Long count, Integer size);

    public List<TopFavoriteMarketResDto> findTopFavoriteMarketResDto(Integer size);

    List<CouponLatestTopResDto> findLatestTopCouponDtoListByMarket(Integer size);

    List<MarketCouponResDto> findLatestCouponMarketResDtoListByMarket(LocalDateTime modifiedAt, Long couponId, Integer size);

    List<CouponClosingTopResDto> findClosingTopCouponDtoList(Integer size);
}

