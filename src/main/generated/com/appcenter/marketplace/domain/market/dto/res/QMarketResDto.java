package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.Expression;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.dsl.StringExpression;

import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QMarketResDto is a Querydsl Projection type for MarketResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMarketResDto extends ConstructorExpression<MarketResDto> {

    private static final long serialVersionUID = -832137944L;

    public QMarketResDto(Expression<Long> marketId, Expression<String> name, Expression<String> description, Expression<Long> couponId, Expression<String> couponName, StringExpression address, Expression<String> thumbnail) {
        super(MarketResDto.class, new Class<?>[]{long.class, String.class, String.class, long.class, String.class, String.class, String.class}, marketId, name, description, couponId, couponName, address, thumbnail);
    }

}

