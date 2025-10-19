package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market,Long>, MarketRepositoryCustom{

    // 마지막 순서 알아내기.
    @Query("SELECT MAX(m.orderNo) FROM Market m WHERE m.isDeleted = false")
    Optional<Integer> findMaxOrderNo();
}
