package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.member_coupon.CouponType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponRes {
    private final Long couponId;
    private final String couponName;
    private String couponDescription;
    private LocalDateTime deadLine;
    private Integer stock;
    private Boolean isHidden;
    private Boolean isAvailable;
    private Boolean isMemberIssued;

    // 환급 / 증정 Flag
    private CouponType couponType;

    // 최신,인기 쿠폰 조회 컬럼
    private Long marketId;
    private String marketName;
    private String address;
    private String thumbnail;
    private LocalDateTime couponCreatedAt;
    private Long issuedCount;

    // 사장님 매장 쿠폰 페이징 조회
    @QueryProjection
    @Builder
    public CouponRes(Long couponId, String couponName, String couponDescription, LocalDateTime deadLine, Integer stock, Boolean isHidden) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.deadLine = deadLine;
        this.stock = stock;
        this.isHidden = isHidden;
    }

    // 관리자 전체 쿠폰 조회
    @QueryProjection
    public CouponRes(Long couponId, String couponName, String couponDescription, LocalDateTime deadLine, Integer stock, Boolean isHidden, Long marketId, String marketName) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.deadLine = deadLine;
        this.stock = stock;
        this.isHidden = isHidden;
        this.marketId = marketId;
        this.marketName = marketName;
    }

    // 매장 별 쿠폰 페이징 조회
    @QueryProjection
    public CouponRes(Long couponId, String couponName, String couponDescription, LocalDateTime deadLine, Boolean isAvailable, Boolean isMemberIssued, CouponType couponType) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.deadLine = deadLine;
        this.isAvailable= isAvailable;
        this.isMemberIssued= isMemberIssued;
        this.couponType = couponType;
    }

    // 최신 쿠폰 페이징 조회
    @QueryProjection
    public CouponRes(Long couponId, String couponName, Long marketId, String marketName, String address, String thumbnail, Boolean isAvailable, Boolean isMemberIssued, LocalDateTime couponCreatedAt) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.marketId = marketId;
        this.marketName = marketName;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isAvailable = isAvailable;
        this.isMemberIssued = isMemberIssued;
        this.couponCreatedAt = couponCreatedAt;
    }

    // 인기 쿠폰 페이징 조회
    @QueryProjection
    public CouponRes(Long couponId, String couponName, Long marketId, String marketName, String address, String thumbnail, Boolean isAvailable, Boolean isMemberIssued, Long issuedCount) {
        this.couponId = couponId;
        this.couponName = couponName;
        this.marketId = marketId;
        this.marketName = marketName;
        this.address = address;
        this.thumbnail = thumbnail;
        this.isAvailable = isAvailable;
        this.isMemberIssued = isMemberIssued;
        this.issuedCount = issuedCount;
    }

    public static CouponRes toDto(Coupon coupon){
        return CouponRes.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getName())
                .couponDescription(coupon.getDescription())
                .deadLine(coupon.getDeadLine())
                .stock(coupon.getStock())
                .isHidden(coupon.getIsHidden())
                .build();
    }

}
