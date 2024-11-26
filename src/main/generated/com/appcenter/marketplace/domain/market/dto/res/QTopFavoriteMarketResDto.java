package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QTopFavoriteMarketResDto is a Querydsl Projection type for TopFavoriteMarketResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QTopFavoriteMarketResDto extends ConstructorExpression<TopFavoriteMarketRes> {

    private static final long serialVersionUID = 832741983L;

    public QTopFavoriteMarketResDto(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> marketName, com.querydsl.core.types.Expression<Long> couponId, com.querydsl.core.types.Expression<String> couponName, com.querydsl.core.types.Expression<String> thumbnail) {
        super(TopFavoriteMarketRes.class, new Class<?>[]{long.class, String.class, long.class, String.class, String.class}, marketId, marketName, couponId, couponName, thumbnail);
    }

}

