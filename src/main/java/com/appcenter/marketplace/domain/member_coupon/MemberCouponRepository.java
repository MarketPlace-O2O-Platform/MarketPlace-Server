package com.appcenter.marketplace.domain.member_coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
}
