package com.appcenter.marketplace.domain.category;

import com.appcenter.marketplace.global.common.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByMajor(Major major);
}
