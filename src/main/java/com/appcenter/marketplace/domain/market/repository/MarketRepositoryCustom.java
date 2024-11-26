package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketRepositoryCustom {
    public List<MarketDetailsResDto> findMarketDetailList(Long marketId);

    public List<MarketResDto> findMarketList(Long memberId, Long marketId, Integer size);

    public List<MarketResDto> findMarketListByCategory(Long memberId, Long marketId, Integer size, String major);

    public List<MyFavoriteMarketResDto> findMyFavoriteMarketList(Long memberId, LocalDateTime lastModifiedAt, Integer size);

    public List<FavoriteMarketResDto> findFavoriteMarketList(Long memberId, Long count, Integer size);

    public List<TopFavoriteMarketResDto> findTopFavoriteMarkets(Integer size);

    List<CouponLatestTopResDto> findTopLatestCoupons(Integer size);

    List<MarketCouponResDto> findLatestCouponList(LocalDateTime modifiedAt, Long couponId, Integer size);

    List<CouponClosingTopResDto> findTopClosingCoupons(Integer size);
}

