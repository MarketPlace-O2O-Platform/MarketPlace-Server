package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QMarketDetailResDto is a Querydsl Projection type for MarketDetailResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMarketDetailResDto extends ConstructorExpression<MarketDetailResDto> {

    private static final long serialVersionUID = -16683495L;

    public QMarketDetailResDto(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> operationHours, com.querydsl.core.types.Expression<String> closedDays, com.querydsl.core.types.Expression<String> phoneNumber, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<? extends java.util.List<com.appcenter.marketplace.domain.image.dto.res.ImageResDto>> imageResDtoList) {
        super(MarketDetailResDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, java.util.List.class}, marketId, name, description, operationHours, closedDays, phoneNumber, address, imageResDtoList);
    }

}

