package com.appcenter.marketplace.domain.requestMarket.repository;

import com.appcenter.marketplace.domain.requestMarket.RequestMarket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestMarketRepository extends JpaRepository<RequestMarket,Long>,RequestMarketRepositoryCustom{

    boolean existsByName(String name);

    RequestMarket findRequestMarketByName(String name);
}
