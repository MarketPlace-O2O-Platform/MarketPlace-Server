package com.appcenter.marketplace.domain.member_payback.service.impl;

import com.appcenter.marketplace.domain.coupon.dto.res.CouponPageRes;
import com.appcenter.marketplace.domain.member_coupon.dto.res.CouponHandleRes;
import com.appcenter.marketplace.domain.member_payback.MemberPayback;
import com.appcenter.marketplace.domain.member_payback.dto.res.AdminReceiptRes;
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