package com.appcenter.marketplace.domain.cheer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CheerRepository  extends JpaRepository<Cheer, Long> {
    Optional<Cheer> findByMemberIdAndTempMarketId(Long memberId, Long tempMarketId);

    @Modifying
    @Query("DELETE FROM Cheer c WHERE c.tempMarket.id = :tempMarketId")
    void deleteByTempMarketId(@Param("tempMarketId") Long tempMarketId);
}
