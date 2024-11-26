package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QFavoriteMarketResDto is a Querydsl Projection type for FavoriteMarketResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QFavoriteMarketResDto extends ConstructorExpression<FavoriteMarketResDto> {

    private static final long serialVersionUID = -1536873500L;

    public QFavoriteMarketResDto(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isFavorite, com.querydsl.core.types.Expression<Long> favoriteCount) {
        super(FavoriteMarketResDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, long.class}, marketId, name, description, address, thumbnail, isFavorite, favoriteCount);
    }

}

