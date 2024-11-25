package com.appcenter.marketplace.domain.local.repository;

import com.appcenter.marketplace.domain.local.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local,Long> ,LocalRepositoryCustom{
}
