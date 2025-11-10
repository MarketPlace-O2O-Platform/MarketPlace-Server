package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QTopPopularCouponRes is a Querydsl Projection type for TopPopularCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTopPopularCouponRes extends ConstructorExpression<TopPopularCouponRes> {

    private static final long serialVersionUID = -349532461L;

    public QTopPopularCouponRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Long> issuedCount, com.querydsl.core.types.Expression<com.appcenter.marketplace.domain.member_coupon.CouponType> couponType) {
        super(TopPopularCouponRes.class, new Class<?>[]{long.class, String.class, long.class, String.class, String.class, long.class, com.appcenter.marketplace.domain.member_coupon.CouponType.class}, couponId, couponName, marketId, marketName, thumbnail, issuedCount, couponType);
    }

}

