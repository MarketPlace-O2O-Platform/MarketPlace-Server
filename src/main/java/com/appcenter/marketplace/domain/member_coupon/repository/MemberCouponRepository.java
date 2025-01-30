package com.appcenter.marketplace.domain.member_coupon.repository;

import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> , MemberCouponRepositoryCustom {
    List<MemberCoupon> findAllByCouponIdIn(List<Long> couponIds);
}
