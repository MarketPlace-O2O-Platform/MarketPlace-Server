package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QMarketDetailsRes is a Querydsl Projection type for MarketDetailsRes
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMarketDetailsRes extends ConstructorExpression<MarketDetailsRes> {

    private static final long serialVersionUID = 1484744327L;

    public QMarketDetailsRes(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> operationHours, com.querydsl.core.types.Expression<String> closedDays, com.querydsl.core.types.Expression<String> phoneNumber, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<? extends java.util.List<com.appcenter.marketplace.domain.image.dto.res.ImageRes>> imageResList, com.querydsl.core.types.Expression<com.appcenter.marketplace.global.common.Major> major) {
        super(MarketDetailsRes.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, java.util.List.class, com.appcenter.marketplace.global.common.Major.class}, marketId, name, description, operationHours, closedDays, phoneNumber, address, imageResList, major);
    }

}

