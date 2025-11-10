package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QTopLatestCouponRes is a Querydsl Projection type for TopLatestCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTopLatestCouponRes extends ConstructorExpression<TopLatestCouponRes> {

    private static final long serialVersionUID = -1608315519L;

    public QTopLatestCouponRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<java.time.LocalDateTime> couponCreatedAt, com.querydsl.core.types.Expression<com.appcenter.marketplace.domain.member_coupon.CouponType> couponType) {
        super(TopLatestCouponRes.class, new Class<?>[]{long.class, String.class, long.class, String.class, String.class, java.time.LocalDateTime.class, com.appcenter.marketplace.domain.member_coupon.CouponType.class}, couponId, couponName, marketId, marketName, thumbnail, couponCreatedAt, couponType);
    }

}

