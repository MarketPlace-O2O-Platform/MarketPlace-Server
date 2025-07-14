package com.appcenter.marketplace.domain.payback.repository;

import com.appcenter.marketplace.domain.payback.Payback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaybackRepository extends JpaRepository<Payback, Long>, PaybackRepositoryCustom{
}
