package com.appcenter.marketplace.domain.cheer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CheerRepository  extends JpaRepository<Cheer, Long> {
    Optional<Cheer> findByMemberIdAndTempMarketId(Long memberId, Long tempMarketId);
}
