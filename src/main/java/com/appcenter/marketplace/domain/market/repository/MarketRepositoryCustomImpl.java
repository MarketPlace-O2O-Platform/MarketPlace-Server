package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.image.dto.res.QImageResDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import com.appcenter.marketplace.domain.market.dto.res.QMarketResDto;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.appcenter.marketplace.domain.image.QImage.image;
import static com.querydsl.core.group.GroupBy.list;


@RequiredArgsConstructor
public class MarketRepositoryCustomImpl implements MarketRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public MarketResDto findMarketResDtoById(Long marketId) {
        MarketResDto marketResDto= jpaQueryFactory
                .select(new QMarketResDto(
                        market.id,
                        market.name,
                        market.description,
                        market.operationHours,
                        market.closedDays,
                        market.phoneNumber,
                        market.address,
                        list(new QImageResDto(image.id, image.order, image.name)) // 이미지를 DTO에 매핑
                ))
                .from(market)
                .leftJoin(image).on(image.market.id.eq(market.id))
                .where(market.id.eq(marketId)) // 매장 ID로 필터링
                .orderBy(image.order.asc())
                .fetchOne();

        if(marketResDto==null)
            throw new CustomException(StatusCode.MARKET_NOT_EXIST);

        return marketResDto;
    }
}
