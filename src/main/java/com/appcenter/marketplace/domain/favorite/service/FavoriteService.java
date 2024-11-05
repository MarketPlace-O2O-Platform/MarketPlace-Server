package com.appcenter.marketplace.domain.favorite.service;

public interface FavoriteService {

    void createOrDeleteFavorite(Long memberId, Long marketId);
}
