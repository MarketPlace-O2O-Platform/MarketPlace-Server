package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QTopClosingCouponRes is a Querydsl Projection type for TopClosingCouponRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTopClosingCouponRes extends ConstructorExpression<TopClosingCouponRes> {

    private static final long serialVersionUID = -1548977033L;

    public QTopClosingCouponRes(com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<java.time.LocalDateTime> deadline, com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> thumbnail) {
        super(TopClosingCouponRes.class, new Class<?>[]{long.class, String.class, java.time.LocalDateTime.class, long.class, String.class, String.class}, couponId, couponName, deadline, marketId, marketName, thumbnail);
    }

}

