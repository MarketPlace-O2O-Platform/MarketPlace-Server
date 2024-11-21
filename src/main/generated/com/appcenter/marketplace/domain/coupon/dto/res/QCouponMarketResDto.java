package com.appcenter.marketplace.domain.coupon.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.coupon.dto.res.QCouponMarketResDto is a Querydsl Projection type for CouponMarketResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCouponMarketResDto extends ConstructorExpression<CouponMarketResDto> {

    private static final long serialVersionUID = -97887100L;

    public QCouponMarketResDto(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> marketDescription, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifiedAt) {
        super(CouponMarketResDto.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, java.time.LocalDateTime.class}, marketId, couponId, marketName, marketDescription, thumbnail, modifiedAt);
    }

}

