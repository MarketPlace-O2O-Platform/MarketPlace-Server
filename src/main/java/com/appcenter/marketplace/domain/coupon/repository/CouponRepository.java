package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {
    List<Coupon> findByMarketId(Long marketId);
}
