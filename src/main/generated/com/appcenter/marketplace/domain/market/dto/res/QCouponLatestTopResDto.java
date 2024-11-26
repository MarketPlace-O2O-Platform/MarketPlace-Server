package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QCouponLatestTopResDto is a Querydsl Projection type for CouponLatestTopResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCouponLatestTopResDto extends ConstructorExpression<TopLatestCouponRes> {

    private static final long serialVersionUID = 443933690L;

    public QCouponLatestTopResDto(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> thumbnail) {
        super(TopLatestCouponRes.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class}, marketId, couponId, marketName, couponName, thumbnail);
    }

}

