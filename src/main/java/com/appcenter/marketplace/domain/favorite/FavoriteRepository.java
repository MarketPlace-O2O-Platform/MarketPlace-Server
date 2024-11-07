package com.appcenter.marketplace.domain.favorite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {

    Optional<Favorite> findByMember_IdAndMarket_Id(Long memberId, Long marketId);

    boolean existsByMember_IdAndMarket_Id(Long memberId, Long marketId);

}