package com.appcenter.marketplace.domain.beta.service.impl;

import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponPageRes;
import com.appcenter.marketplace.domain.beta.dto.res.BetaCouponRes;
import com.appcenter.marketplace.domain.beta.repository.BetaCouponRepository;
import com.appcenter.marketplace.domain.beta.service.BetaCouponService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.CATEGORY_NOT_EXIST;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BetaCouponServiceImpl implements BetaCouponService {
    private final BetaCouponRepository betaCouponRepository;

    @Override
    public BetaCouponPageRes<BetaCouponRes> getBetaCouponList(Long memberId, Long betaCouponId, String major, Integer size) {
        List<BetaCouponRes> betaCouponResList;
        if (major == null) {
            betaCouponResList = betaCouponRepository.findAllCoupons(memberId,betaCouponId, size);
        } else if (Major.exists(major)) {
            betaCouponResList = betaCouponRepository.findAllCouponsByCategory(memberId, betaCouponId, major, size);
        } else throw new CustomException(CATEGORY_NOT_EXIST);

        return checkNextPageAndReturn(betaCouponResList, size);
    }


    private <T> BetaCouponPageRes<T> checkNextPageAndReturn(List<T> betaCouponResList, Integer size) {
        boolean hasNext = false;

        if(betaCouponResList.size() > size){
            hasNext = true;
            betaCouponResList.remove(size.intValue());
        }

        return new BetaCouponPageRes<>(betaCouponResList, hasNext);
    }
}
