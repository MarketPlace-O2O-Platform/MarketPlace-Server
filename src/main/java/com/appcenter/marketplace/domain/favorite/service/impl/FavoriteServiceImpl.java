package com.appcenter.marketplace.domain.favorite.service.impl;

import com.appcenter.marketplace.domain.favorite.Favorite;
import com.appcenter.marketplace.domain.favorite.repository.FavoriteRepository;
import com.appcenter.marketplace.domain.favorite.service.FavoriteService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.MemberRepository;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final MarketRepository marketRepository;

    @Override
    @Transactional
    public void createOrDeleteFavorite(Long memberId, Long marketId){
        Optional<Favorite> optionalFavorite= favoriteRepository.findByMember_IdAndMarket_Id(memberId,marketId);
        if(optionalFavorite.isEmpty()){
            Member member= memberRepository.findById(memberId).orElseThrow(() -> new CustomException(StatusCode.MEMBER_NOT_EXIST));
            Market market= marketRepository.findById(marketId).orElseThrow(() -> new CustomException(StatusCode.MARKET_NOT_EXIST));;
            Favorite favorite= Favorite.builder()
                    .isDeleted(false)
                    .member(member)
                    .market(market)
                    .build();
            favoriteRepository.save(favorite);
        }
        else{
            Favorite favorite=optionalFavorite.get();
            favorite.toggleIsDeleted();
        }
    }


}

