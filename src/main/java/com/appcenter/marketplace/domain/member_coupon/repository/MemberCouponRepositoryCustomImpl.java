package com.appcenter.marketplace.domain.member_coupon.repository;

import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.QIssuedCouponRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.domain.coupon.QCoupon.coupon;
import static com.appcenter.marketplace.domain.member_coupon.QMemberCoupon.memberCoupon;

@RequiredArgsConstructor
public class MemberCouponRepositoryCustomImpl implements MemberCouponRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existCouponByMemberId(Long memberId, Long couponId) {

        // 해당 Id에 coupon이 존재하면 true, 중복되지 않으면 false
        return jpaQueryFactory.selectFrom(memberCoupon)
                .where(memberCoupon.member.id.eq(memberId)
                        .and(memberCoupon.coupon.id.eq(couponId)))
                .fetchFirst() != null;
    }

    @Override
    public List<IssuedCouponRes> findIssuedCouponResDtoByMemberId(Long memberId, Long memberCouponId, Integer size) {
        // 만료되기 전의 쿠폰만 조회가 가능합니다.
        return findCouponsByMemberId(memberId, false, memberCouponId, size);
    }

    @Override
    public List<IssuedCouponRes> findExpiredCouponResDtoByMemberId(Long memberId, Long memberCouponId, Integer size) {
        // 발급 받은 쿠폰 중, 기간이 만료된 쿠폰만 조회합니다.
        return findCouponsByMemberId(memberId, true,memberCouponId, size);
    }

    @Override
    public List<IssuedCouponRes> findUsedMemberCouponResDtoByMemberId(Long memberId, Long memberCouponId, Integer size ) {
        return jpaQueryFactory.select(new QIssuedCouponRes(memberCoupon.id,
                        coupon.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine,
                        memberCoupon.isUsed))
                .from(coupon)
                .join(memberCoupon).on(memberCoupon.coupon.id.eq(coupon.id))
                .where(ltMemberCouponId(memberCouponId)
                        .and(memberCoupon.member.id.eq(memberId))
                        .and(memberCoupon.isUsed.eq(true)))
                .orderBy(memberCoupon.id.desc())
                .limit(size+1)
                .fetch();
    }

    // 3일 후 유효한지 체크
    @Override
    public void check3DaysCoupons(){
        jpaQueryFactory
                .update(memberCoupon)
                .set(memberCoupon.isExpired, true)
                .where(memberCoupon.createdAt.before(LocalDateTime.now().minusDays(3))
                        .and(memberCoupon.isUsed.eq(false)))
                .execute();
    }

    // 쿠폰 기간이 만료되었는지 체크
    @Override
    public void checkExpiredCoupons(){
         jpaQueryFactory
                .update(memberCoupon)
                .set(memberCoupon.isExpired, true)
                .where(memberCoupon.coupon.deadLine.before(LocalDateTime.now())
                        .and(memberCoupon.isUsed.eq(false)))
                .execute();
    }

    private List<IssuedCouponRes> findCouponsByMemberId(Long memberId, boolean isExpired,Long memberCouponId, Integer size) {
        return jpaQueryFactory.select(new QIssuedCouponRes(memberCoupon.id,
                        coupon.id,
                        coupon.name,
                        coupon.description,
                        coupon.deadLine,
                        memberCoupon.isUsed))
                .from(coupon)
                .join(memberCoupon).on(memberCoupon.coupon.id.eq(coupon.id))
                .where(ltMemberCouponId(memberCouponId)
                        .and(memberCoupon.member.id.eq(memberId))
                        .and(memberCoupon.isUsed.eq(false))
                        .and(isExpired
                                ? memberCoupon.coupon.deadLine.before(LocalDateTime.now())
                                : memberCoupon.coupon.deadLine.after(LocalDateTime.now())))
                .orderBy(memberCoupon.id.desc())
                .limit(size+1)
                .fetch();

    }

    private BooleanBuilder ltMemberCouponId(Long memberCouponId){
        BooleanBuilder builder = new BooleanBuilder();
        if( memberCouponId != null){
            builder.and(memberCoupon.id.lt(memberCouponId));
        }
        return builder;
    }
}

