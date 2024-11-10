package com.appcenter.marketplace.domain.market.repository;

import com.appcenter.marketplace.domain.market.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market,Long>, MarketRepositoryCustom{
}
