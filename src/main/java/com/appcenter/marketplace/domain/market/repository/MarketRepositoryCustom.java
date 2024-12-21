package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.dto.res.*;

import java.time.LocalDateTime;
import java.util.List;

public interface MarketRepositoryCustom {
    public List<MarketDetailsRes> findMarketDetailList(Long marketId);

    public List<MarketRes> findMarketList(Long memberId, Long marketId, Integer size);

    public List<MarketRes> findMarketListByCategory(Long memberId, Long marketId, Integer size, String major);

    public List<MyFavoriteMarketRes> findMyFavoriteMarketList(Long memberId, LocalDateTime lastModifiedAt, Integer size);

    public List<FavoriteMarketRes> findFavoriteMarketList(Long memberId,Long marketId, Long count, Integer size);

    public List<TopFavoriteMarketRes> findTopFavoriteMarkets(Integer size);

    List<TopLatestCouponRes> findTopLatestCoupons(Long memberId, Integer size);

    List<LatestCouponRes> findLatestCouponList(Long memberId, LocalDateTime modifiedAt, Long couponId, Integer size);

    List<TopClosingCouponRes> findTopClosingCoupons(Integer size);
}

