package com.appcenter.marketplace.domain.coupon.repository;

import com.appcenter.marketplace.domain.coupon.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {
    List<Coupon> findByMarketId(Long marketId);

    @Modifying
    @Query(value = "DELETE FROM coupon WHERE market_id = :marketId", nativeQuery = true)
    void deleteAllByMarketId(Long marketId);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // X-Lock 설정
    @Query("SELECT c FROM Coupon c WHERE c.id = :couponId")
    Optional<Coupon> findCouponByIdWithLock(@Param("couponId") Long couponId);
}
