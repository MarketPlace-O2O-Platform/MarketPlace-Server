package com.appcenter.marketplace.domain.member_payback.repository;

import com.appcenter.marketplace.domain.member_coupon.CouponType;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.QIssuedCouponRes;
import com.appcenter.marketplace.domain.member_payback.MemberPayback;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.QAdminReceiptRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.QReceiptRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.ReceiptRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.appcenter.marketplace.domain.market.QMarket.market;
import static com.appcenter.marketplace.domain.member.QMember.member;
import static com.appcenter.marketplace.domain.member_payback.QMemberPayback.memberPayback;
import static com.appcenter.marketplace.domain.payback.QPayback.payback;

@RequiredArgsConstructor
public class MemberPaybackRepositoryCustomImpl implements MemberPaybackRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existCouponByMemberId(Long memberId, Long paybackId) {

        return jpaQueryFactory.selectFrom(memberPayback)
                .where(memberPayback.member.id.eq(memberId)
                        .and(memberPayback.payback.id.eq(paybackId))
                        .and(memberPayback.isPayback.eq(false)) // 사용하지 않은 쿠폰 체크
                        .and(memberPayback.isExpired.eq(false))) // 만료되지 않은 쿠폰 체크
                .fetchFirst() != null;
    }

    @Override
    public Optional<MemberPayback> findByCouponIdAndMemberId(Long memberId, Long paybackId) {
        MemberPayback found = jpaQueryFactory.selectFrom(memberPayback)
                .where(memberPayback.id.eq(paybackId)
                        .and(memberPayback.member.id.eq(memberId)))
                .fetchOne();
        return Optional.ofNullable(found);
    }

    @Override
    public List<IssuedCouponRes> findIssuedCouponResByMemberId(Long memberId, Long memberPaybackId, Integer size) {
        return jpaQueryFactory.select(new QIssuedCouponRes(memberPayback.id,
                    payback.id,
                    payback.market.name,
                    payback.market.thumbnail,
                    payback.name,
                    payback.description,
                    memberPayback.isPayback, // payback 완료 여부
                    memberPayback.isExpired,
                    Expressions.constant(CouponType.PAYBACK),
                    memberPayback.receipt.isNotNull()))
                .from(payback)
                .join(memberPayback).on(memberPayback.payback.id.eq(payback.id))
                .join(market).on(market.id.eq(payback.market.id))
                .where(ltMemberPaybackId(memberPaybackId)
                        .and(memberPayback.member.id.eq(memberId))
                        .and(memberPayback.isPayback.eq(false))
                                .and(memberPayback.isExpired.eq(false)))
                .orderBy(memberPayback.id.desc())
                .limit(size+1)
                .fetch();
    }

    @Override
    public List<IssuedCouponRes> findEndedCouponResByMemberId(Long memberId, Long memberPaybackId, Integer size) {
        return jpaQueryFactory.select(new QIssuedCouponRes(memberPayback.id,
                        payback.id,
                        payback.market.name,
                        payback.market.thumbnail,
                        payback.name,
                        payback.description,
                        memberPayback.isPayback,
                        memberPayback.isExpired,
                        Expressions.constant(CouponType.PAYBACK),
                        memberPayback.receipt.isNotNull()))
                .from(payback)
                .join(memberPayback).on(memberPayback.payback.id.eq(payback.id))
                .join(market).on(market.id.eq(payback.market.id))
                .where(ltMemberPaybackId(memberPaybackId)
                        .and(memberPayback.member.id.eq(memberId))
                        .and(memberPayback.isPayback.eq(true)
                        .or(memberPayback.isExpired.eq(true))))
                .orderBy(memberPayback.id.desc())
                .limit(size+1)
                .fetch();
    }

    @Override
    public ReceiptRes findReceiptByMemberId(Long memberId, Long memberPaybackId) {
        return jpaQueryFactory
                .select(new QReceiptRes(
                        memberPayback.id,
                        memberPayback.receipt,
                        member.account,
                        member.accountNumber,
                        memberPayback.isPayback))
                .from(memberPayback)
                .join(member).on(memberPayback.member.id.eq(member.id))
                .where(memberPayback.member.id.eq(memberId)
                        .and(memberPayback.id.eq(memberPaybackId)))
                .fetchOne();
    }

    // 3일 후 유효한지 체크
    @Override
    public void check3DaysCoupons(){
        jpaQueryFactory
                .update(memberPayback)
                .set(memberPayback.isExpired, true)
                .where(memberPayback.createdAt.before(LocalDateTime.now().minusDays(3))
                        .and(memberPayback.isPayback.eq(false)))
                .execute();
    }


    private BooleanBuilder ltMemberPaybackId(Long memberPaybackId){
        BooleanBuilder builder = new BooleanBuilder();
        if( memberPaybackId != null){
            builder.and(memberPayback.id.lt(memberPaybackId));
        }
        return builder;
    }

    @Override
    public List<MemberPayback> findMemberPaybacksByMarketId(Long marketId) {
        return jpaQueryFactory
                .select(memberPayback)
                .from(memberPayback)
                .join(memberPayback.payback, payback)
                .join(payback.market, market)
                .where(market.id.eq(marketId))
                .fetch();
    }

    // 관리자용 영수증 제출 내역 조회 (receipt이 있는 것만)
    @Override
    public List<AdminReceiptRes> findReceiptsForAdmin(Long memberPaybackId, Long marketId, Integer size) {
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(memberPayback.receipt.isNotNull());

        if (memberPaybackId != null) {
            whereClause.and(ltMemberPaybackId(memberPaybackId));
        }

        if (marketId != null) {
            whereClause.and(market.id.eq(marketId));
        }

        return jpaQueryFactory
                .select(new QAdminReceiptRes(
                        memberPayback.id,
                        member.id,
                        payback.name,
                        memberPayback.createdAt,
                        memberPayback.modifiedAt,
                        memberPayback.receipt,
                        memberPayback.isPayback,
                        member.account,
                        member.accountNumber))
                .from(memberPayback)
                .innerJoin(memberPayback.payback, payback)
                .innerJoin(memberPayback.member, member)
                .innerJoin(payback.market, market)
                .where(whereClause)
                .orderBy(memberPayback.id.desc())
                .limit(size + 1)
                .fetch();
    }

    // 관리자용 영수증 상세 조회
    @Override
    public AdminReceiptRes findReceiptDetailForAdmin(Long memberPaybackId) {
        return jpaQueryFactory
                .select(new QAdminReceiptRes(
                        memberPayback.id,
                        member.id,
                        payback.name,
                        memberPayback.createdAt,
                        memberPayback.modifiedAt,
                        memberPayback.receipt,
                        memberPayback.isPayback,
                        member.account,
                        member.accountNumber))
                .from(memberPayback)
                .innerJoin(memberPayback.payback, payback)
                .innerJoin(memberPayback.member, member)
                .innerJoin(payback.market, market)
                .where(memberPayback.id.eq(memberPaybackId))
                .fetchOne();
    }

}
