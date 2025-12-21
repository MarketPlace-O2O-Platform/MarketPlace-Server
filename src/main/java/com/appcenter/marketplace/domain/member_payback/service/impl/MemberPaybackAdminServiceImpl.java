package com.appcenter.marketplace.domain.member_payback.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_coupon.repository.MemberCouponRepository;
import com.appcenter.marketplace.domain.member_payback.MemberPayback;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;
import com.appcenter.marketplace.domain.member_payback.dto.res.CouponPaybackStatsRes;
import com.appcenter.marketplace.domain.member_payback.repository.MemberPaybackRepository;
import com.appcenter.marketplace.domain.member_payback.service.MemberPaybackAdminService;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.COUPON_NOT_EXIST;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberPaybackAdminServiceImpl implements MemberPaybackAdminService {

    private final MemberPaybackRepository memberPaybackRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Override
    @Transactional
    public CouponHandleRes manageCoupon(Long memberPaybackId) {

        MemberPayback memberPayback = findMemberPaybackById(memberPaybackId);
        memberPayback.completePayback();

        // TODO 추후, 사용완료 처리 후 알림 발송 추가해보기

        return CouponHandleRes.toDto(memberPayback);
    }

    @Override
    public CouponPageRes<AdminReceiptRes> getReceiptsForAdmin(Long memberPaybackId, Long marketId, Integer size) {
        List<AdminReceiptRes> receipts = memberPaybackRepository.findReceiptsForAdmin(memberPaybackId, marketId, size);
        return checkNextPageAndReturn(receipts, size);
    }

    @Override
    public AdminReceiptRes getReceiptDetail(Long memberPaybackId) {
        AdminReceiptRes receipt = memberPaybackRepository.findReceiptDetailForAdmin(memberPaybackId);
        if (receipt == null) {
            throw new CustomException(COUPON_NOT_EXIST);
        }
        return receipt;
    }

    @Override
    public CouponPaybackStatsRes getCouponPaybackStats() {
        // 전체 회원 수
        long totalMemberCount = memberRepository.count();

        // 전체 쿠폰 다운 수 (MemberCoupon 총 개수)
        long totalCouponDownloadCount = memberCouponRepository.count();

        // 한명당 쿠폰 다운수
        double avgCouponDownloadPerMember = totalMemberCount > 0
                ? (double) totalCouponDownloadCount / totalMemberCount
                : 0.0;

        // 전체 환급 쿠폰 수 (MemberPayback 총 개수)
        long totalPaybackCouponCount = memberPaybackRepository.count();

        // 환급 완료된 쿠폰 수
        long completedPaybackCount = memberPaybackRepository.countByIsPayback(true);

        // 쿠폰 다운 대비 환급율
        double paybackRate = totalPaybackCouponCount > 0
                ? (double) completedPaybackCount / totalPaybackCouponCount * 100
                : 0.0;

        return CouponPaybackStatsRes.of(avgCouponDownloadPerMember, paybackRate);
    }

    private MemberPayback findMemberPaybackById(Long couponId) {
        return memberPaybackRepository.findById(couponId)
                .orElseThrow(() -> new CustomException(COUPON_NOT_EXIST));
    }

    private <T> CouponPageRes<T> checkNextPageAndReturn(List<T> list, Integer size) {
        boolean hasNext = list.size() > size;

        if (hasNext) {
            list = list.subList(0,size);
        }

        return new CouponPageRes<>(list, hasNext);
    }
}