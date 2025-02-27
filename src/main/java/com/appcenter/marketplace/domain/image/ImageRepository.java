package com.appcenter.marketplace.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {

    List<Image> findAllByMarket_Id(Long marketId);
}
