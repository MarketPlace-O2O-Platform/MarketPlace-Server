package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QMyFavoriteMarketRes is a Querydsl Projection type for MyFavoriteMarketRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMyFavoriteMarketRes extends ConstructorExpression<MyFavoriteMarketRes> {

    private static final long serialVersionUID = -204158225L;

    public QMyFavoriteMarketRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> thumbnail, com.querydsl.core.types.Expression<Boolean> isFavorite, com.querydsl.core.types.Expression<Boolean> isNewCoupon, com.querydsl.core.types.Expression<java.time.LocalDateTime> favoriteModifiedAt) {
        super(MyFavoriteMarketRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class, java.time.LocalDateTime.class}, marketId, name, description, address, thumbnail, isFavorite, isNewCoupon, favoriteModifiedAt);
    }

}

