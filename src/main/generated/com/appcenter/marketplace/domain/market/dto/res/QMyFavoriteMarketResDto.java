package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QMyFavoriteMarketResDto is a Querydsl Projection type for MyFavoriteMarketResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMyFavoriteMarketResDto extends ConstructorExpression<MyFavoriteMarketResDto> {

    private static final long serialVersionUID = -403920784L;

    public QMyFavoriteMarketResDto(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isFavorite, com.querydsl.core.types.Expression<java.time.LocalDateTime> favoriteModifiedAt) {
        super(MyFavoriteMarketResDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, java.time.LocalDateTime.class}, marketId, name, description, address, thumbnail, isFavorite, favoriteModifiedAt);
    }

}

