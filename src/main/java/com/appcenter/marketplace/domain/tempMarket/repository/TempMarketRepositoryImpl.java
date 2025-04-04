package com.appcenter.marketplace.domain.tempMarket.repository;

import com.appcenter.marketplace.domain.tempMarket.dto.res.QTempMarketRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.core.instrument.step.StepMeterRegistry;
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
                        cheer.id.isNotNull(),
                        tempMarket.createdAt))  // queryDsl로 계산 x -> INTERVAL 예약어를 사용하려 했으나, hibernate에서 사용하지 못함.
                .from(tempMarket)
                .leftJoin(cheer).on(tempMarket.eq(cheer.tempMarket)
                        .and(cheer.member.id.eq(memberId)))
                .where(ltMarketId(marketId)
                        .and(tempMarket.isHidden.eq(false)))
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
                        cheer.id.isNotNull(),
                        tempMarket.createdAt))
                .from(tempMarket)
                .leftJoin(cheer).on(tempMarket.eq(cheer.tempMarket)
                                .and(cheer.member.id.eq(memberId)))
                .innerJoin(category).on(tempMarket.category.eq(category))
                .where(ltMarketId(marketId)
                        .and(tempMarket.isHidden.eq(false))
                        .and(category.major.stringValue().eq(major)))
                .orderBy(tempMarket.id.desc())
                .limit(size+1)
                .fetch();
    }

    @Override
    public List<TempMarketRes> findUpcomingMarketList(Long memberId, Long marketId, Integer cheerCount, Integer size) {

        return jpaQueryFactory
                .select(new QTempMarketRes(
                        tempMarket.id,
                        tempMarket.name,
                        tempMarket.thumbnail,
                        tempMarket.cheerCount,
                        cheer.id.isNotNull(),
                        tempMarket.createdAt))
                .from(tempMarket)
                .leftJoin(cheer).on(tempMarket.eq(cheer.tempMarket)
                                .and(cheer.member.id.eq(memberId)))
                .where(ltCheerCountAndMarketId(marketId, cheerCount)
                        .and(tempMarket.cheerCount.goe(14))
                        .and(tempMarket.isHidden.eq(false)))  // 달성 임박에 올라간 후, 공감순으로 보여줘야 함.
                .orderBy(tempMarket.cheerCount.desc(), tempMarket.id.desc())
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

    private BooleanBuilder ltCheerCountAndMarketId(Long marketId, Integer lastCheerCount) {
        BooleanBuilder builder = new BooleanBuilder();

        if (lastCheerCount != null) {
            builder.and(tempMarket.cheerCount.eq(lastCheerCount)
                    .and(tempMarket.id.lt(marketId))
                    .or(tempMarket.cheerCount.lt(lastCheerCount)));
        } else if (marketId != null) {
            builder.and(tempMarket.id.lt(marketId));
        }

        return builder;
    }

}
