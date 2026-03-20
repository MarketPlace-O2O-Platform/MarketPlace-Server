package com.appcenter.marketplace.domain.member_payback.repository;

import com.appcenter.marketplace.domain.member_payback.MemberPayback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MemberPaybackRepository extends JpaRepository<MemberPayback, Long>, MemberPaybackRepositoryCustom {

    long countByIsPayback(Boolean isPayback);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    long countByReceiptIsNotNullAndModifiedAtBetween(LocalDateTime start, LocalDateTime end);

}
