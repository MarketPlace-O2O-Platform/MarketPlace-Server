package com.appcenter.marketplace.domain.tempMarket.repository;

import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempMarketRepository extends JpaRepository<TempMarket, Long>, TempMarketRepositoryCustom {
    Boolean existsByName(String name);
    Page<TempMarket> findByCategoryId(Long categoryId, Pageable pageable);
}
