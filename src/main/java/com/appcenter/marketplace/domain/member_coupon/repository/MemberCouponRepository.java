package com.appcenter.marketplace.domain.member_coupon.repository;

import com.appcenter.marketplace.domain.member_coupon.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> , MemberCouponRepositoryCustom {
    List<MemberCoupon> findAllByCouponIdIn(List<Long> couponIds);

    @Modifying
    @Query(value = "DELETE FROM member_coupon WHERE coupon_id IN (:couponIds)", nativeQuery = true )
    void deleteAllByCouponIds(List<Long> couponIds);
}
