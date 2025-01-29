package com.appcenter.marketplace.domain.member_coupon.service.impl;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.coupon.repository.CouponRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import com.appcenter.marketplace.domain.member_coupon.MemberCouponType;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_coupon.repository.MemberCouponRepository;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void issuedCoupon(Long memberId, Long couponId) {
        Member member = findMemberById(memberId);
        Coupon coupon = findCouponById(couponId);

        // 쿠폰의 잔여 갯수가 0개일 경우
        if (coupon.getStock() == 0 ){
            throw new CustomException(COUPON_SOLD_OUT);
        }

        // 회원이 이미 해당 쿠폰을 발급받았는지 확인
        if (!memberCouponRepository.existCouponByMemberId(member.getId(), coupon.getId())) {
            memberCouponRepository.save(MemberCoupon.builder()
                    .member(member)
                    .coupon(coupon)
                    .isUsed(false)
                    .isExpired(false)
                    .isDeleted(false)
                    .build());
            coupon.reduce();

        } else {
            throw new CustomException(COUPON_ALREADY_ISSUED);
        }
    }

    @Override
    @Transactional
    public CouponPageRes<IssuedCouponRes> getMemberCouponList(Long memberId, MemberCouponType type, Long memberCouponId, Integer size) {
        List<IssuedCouponRes> couponList;

        switch (type) {
            case EXPIRED ->
                    couponList = memberCouponRepository.findExpiredCouponResDtoByMemberId(memberId, memberCouponId, size);
            case USED ->
                    couponList = memberCouponRepository.findUsedMemberCouponResDtoByMemberId(memberId, memberCouponId, size);
            default ->
                    couponList = memberCouponRepository.findIssuedCouponResDtoByMemberId(memberId, memberCouponId, size);
        };

        return checkNextPageAndReturn(couponList, size);
    }

    // 쿠폰 사용처리
    @Override
    @Transactional
    public CouponHandleRes updateCoupon(Long memberCouponId) {
        MemberCoupon memberCoupon = findMemberCouponById(memberCouponId);
        memberCoupon.usedToggle();
        return CouponHandleRes.toDto(memberCoupon);
    }

    @Override
    public IssuedCouponRes getMemberCoupon(Long memberCouponId) {
        MemberCoupon memberCoupon = findMemberCouponById(memberCouponId);
        return IssuedCouponRes.toDto(memberCoupon);
    }

    // 발급 쿠폰 3일뒤 만료 처리
    @Transactional
    @Override
    public void check3DaysCoupons() {
        memberCouponRepository.check3DaysCoupons();
    }

    @Override
    @Transactional
    public void checkExpiredCoupons() {
        memberCouponRepository.checkExpiredCoupons();
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
    }

    private Coupon findCouponById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));

        if (!coupon.getIsDeleted())
            return coupon;
        else throw new CustomException(COUPON_IS_DELETED);
    }

    private MemberCoupon findMemberCouponById(Long memberCouponId) {
        return memberCouponRepository.findById(memberCouponId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));
    }

    private <T> CouponPageRes<T> checkNextPageAndReturn(List<T> couponList, Integer size) {
        boolean hasNext = false;

        if(couponList.size() > size){
            hasNext = true;
            couponList.remove(size.intValue());
        }

        return new CouponPageRes<>(couponList, hasNext);
    }
}