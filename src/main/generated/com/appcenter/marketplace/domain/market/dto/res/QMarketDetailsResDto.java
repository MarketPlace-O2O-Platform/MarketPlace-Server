package com.appcenter.marketplace.domain.market.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.appcenter.marketplace.domain.market.dto.res.QMarketDetailsResDto is a Querydsl Projection type for MarketDetailsResDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMarketDetailsResDto extends ConstructorExpression<MarketDetailsResDto> {

    private static final long serialVersionUID = -1849866792L;

    public QMarketDetailsResDto(com.querydsl.core.types.Expression<Long> marketId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> description, com.querydsl.core.types.Expression<String> operationHours, com.querydsl.core.types.Expression<String> closedDays, com.querydsl.core.types.Expression<String> phoneNumber, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<? extends java.util.List<com.appcenter.marketplace.domain.image.dto.res.ImageResDto>> imageResDtoList) {
        super(MarketDetailsResDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, java.util.List.class}, marketId, name, description, operationHours, closedDays, phoneNumber, address, imageResDtoList);
    }

}

