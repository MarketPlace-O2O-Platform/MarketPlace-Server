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
    private Boolean isNewCoupon; // 신규 쿠폰 태그
    private Boolean isClosingCoupon; // 마감 임박 태그 (검색용)
    private LocalDateTime favoriteModifiedAt;
    private Major major;
    private Integer orderNo;

    // 전체/카테고리 매장 조회
    @QueryProjection
    public MarketRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Boolean isFavorite, Boolean isNewCoupon, Major major, Integer orderNo) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isFavorite = isFavorite;
        this.isNewCoupon = isNewCoupon;
        this.major = major;
        this.orderNo = orderNo;
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
    public MarketRes(Long marketId, String marketName, String marketDescription, String address, String thumbnail, Long isNewCoupon, Long isClosingCoupon, String major, Integer orderNo) {
        this.marketId = marketId;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.address = address;
        this.thumbnail = thumbnail;
        this.major = Major.valueOf(major); // String → Major enum 변환
        this.orderNo = orderNo;
        this.isNewCoupon = (isNewCoupon != null && isNewCoupon > 0);
        this.isClosingCoupon = (isClosingCoupon != null && isClosingCoupon > 0);
    }
}
