package com.appcenter.marketplace.domain.market.dto.res;

import com.appcenter.marketplace.global.common.Major;
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
    private Boolean isFavorite;
    private Boolean isNewCoupon;
    private LocalDateTime favoriteModifiedAt;
    private Major major;

    // 전체/카테고리 매장 조회
    @QueryProjection
    public MarketRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Boolean isFavorite, Boolean isNewCoupon, Major major) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
        this.isNewCoupon = isNewCoupon;
        this.major = major;
    }

//    // 매장 찜순 조회
//    @QueryProjection
//    public MarketRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Boolean isFavorite, Boolean isNewCoupon, Long favoriteCount) {
//        this.marketId = marketId;
//        this.marketName = marketName;
//        this.marketDescription = marketDescription;
//        this.address = address;
//        this.thumbnail = thumbnail;
//        this.isFavorite = isFavorite;
//        this.isNewCoupon = isNewCoupon;
//        this.favoriteCount = favoriteCount;
//    }
//
//    // TOP 5 매장 찜 순 조회
//    @QueryProjection
//    public MarketRes(Long marketId, String marketName, String thumbnail, Boolean isFavorite) {
//        this.marketId = marketId;
//        this.marketName = marketName;
//        this.thumbnail = thumbnail;
//        this.isFavorite = isFavorite;
//    }

    // 사용자가 찜한 매장 조회
    @QueryProjection
    public MarketRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Boolean isFavorite, Boolean isNewCoupon, LocalDateTime favoriteModifiedAt, Major major) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
        this.isNewCoupon = isNewCoupon;
        this.favoriteModifiedAt = favoriteModifiedAt;
        this.major = major;
    }

    // 검색 매장 조회
    public MarketRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Long isNewCoupon, Major major) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.major = major;
        if (isNewCoupon > 0) { // db에서 Boolean값은 존재하지않아 쿼리결과에서 Long 값을 Boolean값으로 변환해줘야한다.
            this.isNewCoupon = true;
        } else {
            this.isNewCoupon = false;
        }
    }
}
