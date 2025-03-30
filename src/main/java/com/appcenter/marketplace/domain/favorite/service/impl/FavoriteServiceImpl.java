package com.appcenter.marketplace.domain.favorite.service.impl;

import com.appcenter.marketplace.domain.favorite.Favorite;
import com.appcenter.marketplace.domain.favorite.repository.FavoriteRepository;
import com.appcenter.marketplace.domain.favorite.service.FavoriteService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.global.common.StatusCode;
import com.appcenter.marketplace.global.config.AsyncConfig;
import com.appcenter.marketplace.global.exception.CustomException;
import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final MarketRepository marketRepository;
    private final AsyncConfig asyncConfig;

    @Override
    @Transactional
    public void createOrDeleteFavorite(Long memberId, Long marketId){
        Optional<Favorite> optionalFavorite= favoriteRepository.findByMember_IdAndMarket_Id(memberId,marketId);
        Member member= memberRepository.findById(memberId).orElseThrow(() -> new CustomException(StatusCode.MEMBER_NOT_EXIST));
        Market market= marketRepository.findById(marketId).orElseThrow(() -> new CustomException(StatusCode.MARKET_NOT_EXIST));

        if(optionalFavorite.isEmpty()){
            Favorite favorite= Favorite.builder()
                    .isDeleted(false)
                    .member(member)
                    .market(market)
                    .build();
            favoriteRepository.save(favorite);

            ApiFuture<TopicManagementResponse> apiFuture = FirebaseMessaging
                    .getInstance()
                    .subscribeToTopicAsync(Collections.singletonList(member.getFcmToken())
                            ,("market-"+ market.getId().toString()));

            apiFuture.addListener(() ->{
                try{
                    TopicManagementResponse response = apiFuture.get();
                    log.info("토픽 구독 성공: {}", response.getSuccessCount());
                } catch (ExecutionException | InterruptedException e) {
                    log.error("토픽 구독 관련 예외 발생: {}", e.getMessage());
                    throw new CustomException(StatusCode.FCM_SUBSCRIBE_FAIL);
                }
            }, asyncConfig.getFcmExecutor());
        }
        else{
            Favorite favorite=optionalFavorite.get();
            favorite.toggleIsDeleted();

            if(favorite.getIsDeleted()==true){
                ApiFuture<TopicManagementResponse> apiFuture = FirebaseMessaging
                        .getInstance()
                        .unsubscribeFromTopicAsync(Collections.singletonList(member.getFcmToken())
                                ,("market-"+ market.getId().toString()));

                apiFuture.addListener(() ->{
                    try{
                        TopicManagementResponse response = apiFuture.get();
                        log.info("토픽 구독취소 성공: {}", response.getSuccessCount());
                    } catch (ExecutionException | InterruptedException e) {
                        log.error("토픽 구독취소 관련 예외 발생: {}", e.getMessage());
                        throw new CustomException(StatusCode.FCM_UNSUBSCRIBE_FAIL);
                    }
                }, asyncConfig.getFcmExecutor());
            }
            else {
                ApiFuture<TopicManagementResponse> apiFuture = FirebaseMessaging
                        .getInstance()
                        .subscribeToTopicAsync(Collections.singletonList(member.getFcmToken())
                                ,("market-"+ market.getId().toString()));

                apiFuture.addListener(() ->{
                    try{
                        TopicManagementResponse response = apiFuture.get();
                        log.info("토픽 구독 성공: {}", response.getSuccessCount());
                    } catch (ExecutionException | InterruptedException e) {
                        log.error("토픽 구독 관련 예외 발생: {}", e.getMessage());
                        throw new CustomException(StatusCode.FCM_SUBSCRIBE_FAIL);
                    }
                }, asyncConfig.getFcmExecutor());
            }


        }
    }


}

