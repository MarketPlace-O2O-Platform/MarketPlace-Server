package com.appcenter.marketplace.domain.market.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MarketRes {
    private final Long marketId;
    private final String marketName;
    private String marketDescription;
    private String address;
    private final String thumbnail;
    private final Boolean isFavorite;
    private Boolean isNewCoupon;
    private Long favoriteCount;
    private LocalDateTime AtParam;

    // 전체/카테고리 매장 조회
    @QueryProjection
    public MarketRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Boolean isFavorite, Boolean isNewCoupon) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
        this.isNewCoupon = isNewCoupon;
    }

    // 매장 찜순 조회
    @QueryProjection
    public MarketRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Boolean isFavorite, Boolean isNewCoupon, Long favoriteCount) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
        this.isNewCoupon = isNewCoupon;
        this.favoriteCount = favoriteCount;
    }

    // TOP 5 매장 찜 순 조회
    @QueryProjection
    public MarketRes(Long marketId, String marketName, String thumbnail, Boolean isFavorite) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
    }

    // 사용자가 찜한 매장 조회/ 최신 쿠폰 순 매장 조회
    @QueryProjection
    public MarketRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Boolean isFavorite, Boolean isNewCoupon, LocalDateTime AtParam) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
        this.isNewCoupon = isNewCoupon;
        this.AtParam = AtParam;
    }


}
