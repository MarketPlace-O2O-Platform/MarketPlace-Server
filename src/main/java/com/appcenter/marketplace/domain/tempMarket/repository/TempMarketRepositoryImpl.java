package com.appcenter.marketplace.domain.tempMarket.repository;

import com.appcenter.marketplace.domain.category.QCategory;
import com.appcenter.marketplace.domain.tempMarket.dto.res.QTempMarketRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;

import static com.appcenter.marketplace.domain.cheer.QCheer.cheer;
import static com.appcenter.marketplace.domain.category.QCategory.category;
import static com.appcenter.marketplace.domain.tempMarket.QTempMarket.tempMarket;

@RequiredArgsConstructor
public class TempMarketRepositoryImpl implements TempMarketRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TempMarketRes> findMarketList(Long memberId, Long marketId, Integer size) {

        return jpaQueryFactory
                .select(new QTempMarketRes(
                        tempMarket.id,
                        tempMarket.name,
                        tempMarket.thumbnail,
                        tempMarket.cheerCount,
                        cheer.id.isNotNull()))
                .from(tempMarket)
                .leftJoin(cheer).on(tempMarket.eq(cheer.tempMarket)
                        .and(cheer.isDeleted.eq(false)
                        .and(cheer.member.id.eq(memberId))))
                .where(ltMarketId(marketId))
                .orderBy(tempMarket.id.desc())
                .limit(size+1)
                .fetch();
    }

    @Override
    public List<TempMarketRes> findMarketListByCategory(Long memberId, Long marketId, Integer size, String major) {

        return jpaQueryFactory
                .select(new QTempMarketRes(
                        tempMarket.id,
                        tempMarket.name,
                        tempMarket.thumbnail,
                        tempMarket.cheerCount,
                        cheer.id.isNotNull()))
                .from(tempMarket)
                .leftJoin(cheer).on(tempMarket.eq(cheer.tempMarket)
                        .and(cheer.isDeleted.eq(false)
                                .and(cheer.member.id.eq(memberId))))
                .innerJoin(category).on(tempMarket.category.eq(category))
                .where(ltMarketId(marketId)
                        .and(category.major.stringValue().eq(major)))
                .orderBy(tempMarket.id.desc())
                .limit(size+1)
                .fetch();
    }

    private BooleanBuilder ltMarketId(Long marketId){
        BooleanBuilder builder = new BooleanBuilder();
        if (marketId != null) {
            builder.and(tempMarket.id.lt(marketId));
        }
        return builder;

    }
}
