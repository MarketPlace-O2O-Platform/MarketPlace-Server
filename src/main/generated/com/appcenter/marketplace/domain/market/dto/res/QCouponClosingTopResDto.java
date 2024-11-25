package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QCouponClosingTopResDto is a Querydsl Projection type for CouponClosingTopResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCouponClosingTopResDto extends ConstructorExpression<CouponClosingTopResDto> {

    private static final long serialVersionUID = 575989330L;

    public QCouponClosingTopResDto(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<java.time.LocalDateTime> deadline, com.querydsl.core.types.Expression<String> thumbnail) {
        super(CouponClosingTopResDto.class, new Class<?>[]{long.class, long.class, String.class, String.class, java.time.LocalDateTime.class, String.class}, marketId, couponId, marketName, couponName, deadline, thumbnail);
    }

}

