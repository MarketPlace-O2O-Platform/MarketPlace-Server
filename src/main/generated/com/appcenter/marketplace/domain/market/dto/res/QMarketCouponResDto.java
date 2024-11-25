package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QMarketCouponResDto is a Querydsl Projection type for MarketCouponResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMarketCouponResDto extends ConstructorExpression<MarketCouponResDto> {

    private static final long serialVersionUID = 1167997486L;

    public QMarketCouponResDto(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<String> marketDescription, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifiedAt) {
        super(MarketCouponResDto.class, new Class<?>[]{long.class, long.class, String.class, String.class, String.class, java.time.LocalDateTime.class}, marketId, couponId, marketName, marketDescription, thumbnail, modifiedAt);
    }

}

