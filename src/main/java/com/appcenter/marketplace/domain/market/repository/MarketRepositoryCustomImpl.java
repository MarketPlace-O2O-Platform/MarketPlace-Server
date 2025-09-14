package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.coupon.QCoupon;
import com.appcenter.marketplace.domain.image.dto.res.QImageRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketRes;
import com.appcenter.marketplace.domain.market.dto.res.QMarketDetailsRes;
import com.appcenter.marketplace.domain.market.dto.res.QMarketRes;
import com.appcenter.marketplace.global.common.Major;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.domain.category.QCategory.category;
import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import static com.appcenter.marketplace.domain.favorite.QFavorite.favorite;
import static com.appcenter.marketplace.domain.image.QImage.image;
import static com.appcenter.marketplace.domain.local.QLocal.local;
import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.appcenter.marketplace.domain.metro.QMetro.metro;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


@RequiredArgsConstructor
public class MarketRepositoryCustomImpl implements MarketRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    // 매장 상세 정보 조회
    @Override
    public List<MarketDetailsRes> findMarketDetailList(Long marketId) {
        // market과 image를 조인 하여 매장 정보와 순서에 오름차순인 이미지 리스트를 dto에 매핑한다.
        return  jpaQueryFactory
                .from(market)
                .innerJoin(image).on(image.market.eq(market))
                .where(market.id.eq(marketId)) // 매장 ID로 필터링
                .orderBy(image.sequence.asc())
                // transfrom을 통해 쿼리 결과를 원하는 형태로 변환한다.
                // groupBy(sql의 groupBy가 아니다)를 통해 마켓id를 기준으로 그룹화해 마켓 DTO List로 만든다.
                // 그룹화를 함으로 써 이미지 DTO 리스트를 list()로 받을 수 있게된다.
                .transform(groupBy(market.id).list(new QMarketDetailsRes(
                        market.id,
                        market.name,
                        market.description,
                        market.operationHours,
                        market.closedDays,
                        market.phoneNumber,
                        market.address,
                        list(new QImageRes(image.id, image.sequence, image.name)
                        ))));
    }


    // 매장 페이징 조회
    @Override
    public List<MarketRes> findMarketList(Long memberId, Long marketId, Integer size) {
        return jpaQueryFactory
                .selectDistinct(new QMarketRes(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        memberId != null ?
                                favorite.id.isNotNull() :
                                Expressions.FALSE,
                        coupon.id.isNotNull()))
                .from(market)
                .leftJoin(favorite).on(market.eq(favorite.market)
                        .and(favorite.isDeleted.eq(false)
                        .and(memberId != null ? favorite.member.id.eq(memberId) : null ))) // 자신이 찜한 매장
                .leftJoin(coupon).on(coupon.market.eq(market)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.createdAt.goe(LocalDateTime.now().minusDays(7)))) // 7일 전 보다 크거나 같은 쿠폰
                .innerJoin(local).on(market.local.eq(local))
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(ltMarketId(marketId)
                        .and(market.isDeleted.eq(false)))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }

    // 카테고리 별 매장 페이징 조회
    @Override
    public List<MarketRes> findMarketListByCategory(Long memberId, Long marketId, Integer size, String major) {
        return jpaQueryFactory
                .selectDistinct(new QMarketRes(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        memberId != null ?
                                favorite.id.isNotNull() :
                                Expressions.FALSE,
                        coupon.id.isNotNull()))
                .from(market)
                .leftJoin(favorite).on(market.eq(favorite.market)
                        .and(favorite.isDeleted.eq(false) // 자신이 찜한 매장
                                .and(memberId != null ? favorite.member.id.eq(memberId) : null )))
                .leftJoin(coupon).on(coupon.market.eq(market)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.createdAt.goe(LocalDateTime.now().minusDays(7)))) // 7일 전 보다 크거나 같은 쿠폰
                .innerJoin(category).on(market.category.eq(category))
                .innerJoin(local).on(market.local.eq(local))
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(ltMarketId(marketId)
                        .and(market.isDeleted.eq(false))
                        .and(category.major.eq(Major.valueOf(major))))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }

    // 주소별 매장 페이징 조회
    @Override
    public List<MarketRes> findMarketListByAddress(Long memberId, Long marketId, Long localId, Integer size) {
        return jpaQueryFactory
                .selectDistinct(new QMarketRes(
                        market.id,
                        market.name,
                        market.description,
                        market.address, // metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        memberId != null ?
                                favorite.id.isNotNull() :
                                Expressions.FALSE,
                        coupon.id.isNotNull()))
                .from(market)
                .leftJoin(favorite).on(market.eq(favorite.market)
                        .and(favorite.isDeleted.eq(false)
                                .and(memberId != null ? favorite.member.id.eq(memberId) : null )))  // 자신이 찜한 매장
                .leftJoin(coupon).on(coupon.market.eq(market)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.createdAt.goe(LocalDateTime.now().minusDays(7)))) // 7일 전 보다 크거나 같은 쿠폰
                .innerJoin(local).on(market.local.eq(local)
                        .and(market.local.id.eq(localId)))
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(ltMarketId(marketId)
                        .and(market.isDeleted.eq(false)))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }

    // 주소&카테고리 별 매장 페이징 조회
    @Override
    public List<MarketRes> findMarketListByAddressAndCategory(Long memberId, Long marketId, Long localId, Integer size, String major) {
        return jpaQueryFactory
                .selectDistinct(new QMarketRes(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        memberId != null ?
                                favorite.id.isNotNull() :
                                Expressions.FALSE,
                        coupon.id.isNotNull()))
                .from(market)
                .leftJoin(favorite).on(market.eq(favorite.market)
                        .and(favorite.isDeleted.eq(false) // 자신이 찜한 매장
                                .and(memberId != null ? favorite.member.id.eq(memberId) : null )))
                .leftJoin(coupon).on(coupon.market.eq(market)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.createdAt.goe(LocalDateTime.now().minusDays(7)))) // 7일 전 보다 크거나 같은 쿠폰
                .innerJoin(category).on(market.category.eq(category))
                .innerJoin(local).on(market.local.eq(local)
                        .and(market.local.id.eq(localId)))
                .innerJoin(metro).on(local.metro.eq(metro))
                .where(ltMarketId(marketId)
                        .and(market.isDeleted.eq(false))
                        .and(category.major.eq(Major.valueOf(major))))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }


    // 회원이 찜한 매장 페이징 조회
    @Override
    public List<MarketRes> findMyFavoriteMarketList(Long memberId, LocalDateTime lastModifiedAt, Integer size) {
        return jpaQueryFactory
                .select(new QMarketRes(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        favorite.id.isNotNull(),
                        coupon.id.isNotNull(),
                        favorite.modifiedAt))
                .from(favorite)
                .innerJoin(favorite.market, market)
                .innerJoin(market.local, local)
                .innerJoin(local.metro, metro)
                .leftJoin(coupon).on(coupon.market.eq(market)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.createdAt.goe(LocalDateTime.now().minusDays(7))))
                .where(
                        favorite.isDeleted.eq(false)
                                .and(favorite.member.id.eq(memberId))
                                .and(ltFavoriteModifiedAt(lastModifiedAt))
                                .and(market.isDeleted.eq(false))
                )
                .orderBy(favorite.modifiedAt.desc())
                .limit(size + 1)
                .fetch();


//        return jpaQueryFactory
//                .select(new QMarketRes(
//                        market.id,
//                        market.name,
//                        market.description,
//                        metro.name.concat(" ").concat(local.name),
//                        market.thumbnail,
//                        favorite.id.isNotNull(),
//                        coupon.id.isNotNull(),
//                        favorite.modifiedAt))
//                .from(market)
//                .innerJoin(favorite).on(market.eq(favorite.market)
//                        .and(favorite.isDeleted.eq(false)
//                        .and(favorite.member.id.eq(memberId)))) // 자신이 찜한 매장
//                .leftJoin(coupon).on(coupon.market.eq(market)
//                        .and(coupon.isDeleted.eq(false))
//                        .and(coupon.isHidden.eq(false))
//                        .and(coupon.createdAt.goe(LocalDateTime.now().minusDays(7)))) // 7일 전 보다 크거나 같은 쿠폰
//                .innerJoin(local).on(market.local.eq(local))
//                .innerJoin(metro).on(local.metro.eq(metro))
//                .where(ltFavoriteModifiedAt(lastModifiedAt) // 회원 자신이 동시간대에 찜할 수 없으므로 lt이다.
//                        .and(market.isDeleted.eq(false)))
//                .orderBy(favorite.modifiedAt.desc())
//                .limit(size + 1)
//                .fetch();
    }

//    // 찜 수가 가장 많은 매장 페이징 조회
//    @Override
//    public List<MarketRes> findFavoriteMarketList(Long memberId,Long marketId, Long count, Integer size) {
//        QFavorite favoriteMember = new QFavorite("favoriteMember"); // 해당 사용자의 각 매장의 찜 여부 확인을 위한 별칭 생성
//
//        return jpaQueryFactory
//                .select(new QMarketRes(
//                        market.id,
//                        market.name,
//                        market.description,
//                        metro.name.concat(" ").concat(local.name),
//                        market.thumbnail,
//                        favoriteMember.id.isNotNull(),
//                        coupon.id.isNotNull(),
//                        favorite.id.count()))
//                .from(market)
//                // 모든 사용자 기준 찜 데이터 JOIN
//                .leftJoin(favorite).on(market.eq(favorite.market)
//                        .and(favorite.isDeleted.eq(false)))
//                // 특정 사용자의 찜 여부 확인을 위한 JOIN
//                .leftJoin(favoriteMember).on(market.eq(favoriteMember.market)
//                        .and(favoriteMember.isDeleted.eq(false)
//                        .and(favoriteMember.member.id.eq(memberId))))
//                .leftJoin(coupon).on(coupon.market.eq(market)
//                        .and(coupon.isDeleted.eq(false))
//                        .and(coupon.isHidden.eq(false))
//                        .and(coupon.createdAt.goe(LocalDateTime.now().minusDays(7)))) // 7일 전 보다 크거나 같은 쿠폰
//                .innerJoin(local).on(market.local.eq(local))
//                .innerJoin(metro).on(local.metro.eq(metro))
//                .groupBy(market.id, market.name, market.description, metro.name, local.name, market.thumbnail,favoriteMember.id, coupon.id)
//                .having(loeFavoriteCountAndLtMarketId(count,marketId))
//                .orderBy(favorite.id.count().desc(),market.id.desc()) // 찜 수가 많은 순으로 정렬
//                .limit(size+1) // 반환할 리스트 크기 제한
//                .fetch(); // 결과 반환
//    }
//
//    // 찜 수가 가장 많은 매장 Top 조회
//    @Override
//    public List<MarketRes> findTopFavoriteMarkets(Long memberId, Integer size) {
//        QFavorite favoriteMember = new QFavorite("favoriteMember"); // 해당 사용자의 각 매장의 찜 여부 확인을 위한 별칭 생성
//
//        return jpaQueryFactory
//                .select(new QMarketRes(
//                        market.id,
//                        market.name,
//                        market.thumbnail,
//                        favoriteMember.id.isNotNull()))
//                .from(market)
//                .leftJoin(favorite).on(market.eq(favorite.market)
//                        .and(favorite.isDeleted.eq(false)))
//                .leftJoin(favoriteMember).on(market.eq(favoriteMember.market)
//                        .and(favoriteMember.isDeleted.eq(false)
//                                .and(favoriteMember.member.id.eq(memberId))))
//                .groupBy(market.id, market.name, market.thumbnail,favoriteMember.id)
//                .orderBy(favorite.id.count().desc()) // 찜 수가 많은 순으로 정렬
//                .fetch();
//    }


    // BooleanExpression을 반환 시 where의 첫 조건에서 null 예외가 뜰 수 있다.
    // lt= less than = <(~보다 작은)
    private BooleanBuilder ltMarketId(Long marketId){
        BooleanBuilder builder = new BooleanBuilder();  // 조건을 담을 BooleanBuilder 생성
        if (marketId != null) {
            builder.and(market.id.lt(marketId));  // marketId가 존재하면 lt 조건을 추가
        }
        return builder;

    }

//    // loe= less or equal = <=(~보다 작거나 같은)
//    private BooleanBuilder loeFavoriteCountAndLtMarketId(Long count,Long marketId){
//        BooleanBuilder builder = new BooleanBuilder();
//        if (count != null && marketId!=null) {
//            builder.and(favorite.id.count().loe(count));
//
//            // A or B 여서 둘중 하나의 조건만 만족하면 true
//            // count보다 작으면. A가 true이기 때문에 B는 실행되지않음
//            // A가 false이면 market.id 필터링한 행들만 true
//            // 따라서 favorite.id.count()가 count와 같을 때만 market.id 필터링
//            builder.and(favorite.id.count().lt(count).or(market.id.lt(marketId)));
//        }
//        return builder;
//
//    }

    private BooleanBuilder ltFavoriteModifiedAt(LocalDateTime modifiedAt){
        BooleanBuilder builder = new BooleanBuilder();
        if (modifiedAt != null) {
            builder.and(favorite.modifiedAt.lt(modifiedAt));
        }
        return builder;

    }

    // 관리자용 전체 매장 조회 (멤버 ID 없이)
    @Override
    public List<MarketRes> findMarketListForAdmin(Long marketId, Integer size) {
        return jpaQueryFactory
                .select(new QMarketRes(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        Expressions.constant(false), // 관리자는 찜 기능이 없으므로 false
                        coupon.id.isNotNull()))
                .from(market)
                .leftJoin(coupon).on(coupon.market.eq(market)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.createdAt.goe(LocalDateTime.now().minusDays(7))))
                .innerJoin(market.local, local)
                .innerJoin(local.metro, metro)
                .where(ltMarketId(marketId)
                        .and(market.isDeleted.eq(false)))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }

    // 관리자용 카테고리별 매장 조회
    @Override
    public List<MarketRes> findMarketListByCategoryForAdmin(Long marketId, Integer size, String major) {
        return jpaQueryFactory
                .select(new QMarketRes(
                        market.id,
                        market.name,
                        market.description,
                        metro.name.concat(" ").concat(local.name),
                        market.thumbnail,
                        Expressions.constant(false), // 관리자는 찜 기능이 없으므로 false
                        coupon.id.isNotNull()))
                .from(market)
                .innerJoin(market.category, category)
                .leftJoin(coupon).on(coupon.market.eq(market)
                        .and(coupon.isDeleted.eq(false))
                        .and(coupon.isHidden.eq(false))
                        .and(coupon.createdAt.goe(LocalDateTime.now().minusDays(7))))
                .innerJoin(market.local, local)
                .innerJoin(local.metro, metro)
                .where(ltMarketId(marketId)
                        .and(category.major.eq(Major.valueOf(major)))
                        .and(market.isDeleted.eq(false)))
                .orderBy(market.id.desc())
                .limit(size + 1)
                .fetch();
    }
}
