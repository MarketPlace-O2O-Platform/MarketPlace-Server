package com.appcenter.marketplace.domain.member_coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.member_coupon.dto.res.QIssuedCouponRes is a Querydsl Projection type for IssuedCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QIssuedCouponRes extends ConstructorExpression<IssuedCouponRes> {

    private static final long serialVersionUID = 422510221L;

    public QIssuedCouponRes(com.querydsl.core.types.Expression<Long> memberCouponId, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<java.time.LocalDateTime> deadLine, com.querydsl.core.types.Expression<Boolean> used, com.querydsl.core.types.Expression<Boolean> expired, com.querydsl.core.types.Expression<com.appcenter.marketplace.domain.member_coupon.CouponType> couponType) {
        super(IssuedCouponRes.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, String.class, java.time.LocalDateTime.class, boolean.class, boolean.class, com.appcenter.marketplace.domain.member_coupon.CouponType.class}, memberCouponId, couponId, marketName, thumbnail, couponName, description, deadLine, used, expired, couponType);
    }

    public QIssuedCouponRes(com.querydsl.core.types.Expression<Long> memberCouponId, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<Boolean> used, com.querydsl.core.types.Expression<Boolean> expired, com.querydsl.core.types.Expression<com.appcenter.marketplace.domain.member_coupon.CouponType> couponType) {
        super(IssuedCouponRes.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, com.appcenter.marketplace.domain.member_coupon.CouponType.class}, memberCouponId, couponId, marketName, thumbnail, couponName, description, used, expired, couponType);
    }

}

