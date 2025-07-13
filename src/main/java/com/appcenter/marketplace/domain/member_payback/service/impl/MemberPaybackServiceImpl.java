package com.appcenter.marketplace.domain.member_payback.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.MemberCouponType;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.IssuedCouponRes;
import com.appcenter.marketplace.domain.member_payback.MemberPayback;
import com.appcenter.marketplace.domain.member_payback.repository.MemberPaybackRepository;
import com.appcenter.marketplace.domain.member_payback.service.MemberPaybackService;
import com.appcenter.marketplace.domain.payback.Payback;
import com.appcenter.marketplace.domain.payback.repository.PaybackRepository;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberPaybackServiceImpl implements MemberPaybackService {

    private final MemberPaybackRepository memberPaybackRepository;
    private final MemberRepository memberRepository;
    private final PaybackRepository paybackRepository;

    @Override
    @Transactional
    public void issuedCoupon(Long memberId, Long couponId) {
        Member member = findMemberById(memberId);
        Payback payback = findPaybackById(couponId);

        if(!memberPaybackRepository.existCouponByMemberId(member.getId(), payback.getId())){
            memberPaybackRepository.save(
                    MemberPayback.builder()
                            .member(member)
                            .payback(payback)
                            .isPayback(false)
                            .isExpired(false)
                            .build()
            );
        }else{
            throw new CustomException(COUPON_ALREADY_ISSUED);
        }
    }

    @Override
    public CouponPageRes<IssuedCouponRes> getPaybackCouponList(Long memberId, MemberCouponType type, Long couponId, Integer size) {
        List<IssuedCouponRes> couponList;

        if (type == MemberCouponType.ISSUED){
             couponList = memberPaybackRepository.findIssuedCouponResByMemberId(memberId, couponId, size);
        }else{
            couponList = memberPaybackRepository.findEndedCouponResByMemberId(memberId, couponId, size);
        }

        return checkNextPageAndReturn(couponList, size);
    }

    @Override
    @Transactional
    public CouponHandleRes updateCoupon(Long memberPaybackId) {

       // TODO 영수증 제출 구현 필요

        MemberPayback memberPayback = findMemberPaybackById(memberPaybackId);

        return CouponHandleRes.toDto(memberPayback);
    }

    @Override
    @Transactional
    public void check3DaysCoupons() {
        memberPaybackRepository.check3DaysCoupons();
    }


    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(MEMBER_NOT_EXIST));
    }

    private Payback findPaybackById(Long paybackId) {
        Payback payback = paybackRepository.findById(paybackId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));

        if (!payback.getIsDeleted())
            return payback;
        else throw new CustomException(COUPON_IS_DELETED);
    }

    private MemberPayback findMemberPaybackById(Long paybackId) {
        return memberPaybackRepository.findById(paybackId).orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));
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
