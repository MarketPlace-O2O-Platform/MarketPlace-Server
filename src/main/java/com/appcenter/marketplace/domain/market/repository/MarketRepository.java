package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market,Long>, MarketRepositoryCustom{
}
