package com.appcenter.marketplace.domain.requestMarket.service.impl;

import com.appcenter.marketplace.domain.requestMarket.RequestMarket;
import com.appcenter.marketplace.domain.requestMarket.dto.req.RequestMarketReq;
import com.appcenter.marketplace.domain.requestMarket.dto.res.RequestMarketRes;
import com.appcenter.marketplace.domain.requestMarket.repository.RequestMarketRepository;
import com.appcenter.marketplace.domain.requestMarket.service.RequestMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class RequestMarketServiceImpl implements RequestMarketService {
    private final RequestMarketRepository requestMarketRepository;


    @Override
    @Transactional
    public RequestMarketRes createRequestMarket(RequestMarketReq requestMarketReq) {
        if(requestMarketRepository.existsByName(requestMarketReq.getName())){
            RequestMarket requestMarket=requestMarketRepository.findRequestMarketByName(requestMarketReq.getName());
            requestMarket.plusCount();
            return RequestMarketRes.from(requestMarket);
        }
        else{
            RequestMarket requestMarket=requestMarketRepository.save(requestMarketReq.toEntity());
            return RequestMarketRes.from(requestMarket);
        }
    }
}
