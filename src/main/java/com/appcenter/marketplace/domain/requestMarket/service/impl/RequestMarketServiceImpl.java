package com.appcenter.marketplace.domain.requestMarket.service.impl;

import com.appcenter.marketplace.domain.requestMarket.RequestMarket;
import com.appcenter.marketplace.domain.requestMarket.dto.req.RequestMarketCreateReq;
import com.appcenter.marketplace.domain.requestMarket.dto.res.RequestMarketRes;
import com.appcenter.marketplace.domain.requestMarket.repository.RequestMarketRepository;
import com.appcenter.marketplace.domain.requestMarket.service.RequestMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class RequestMarketServiceImpl implements RequestMarketService {
    private final RequestMarketRepository requestMarketRepository;


    @Override
    @Transactional
    public RequestMarketRes createRequestMarket(RequestMarketCreateReq requestMarketCreateReq) {
        if(requestMarketRepository.existsByName(requestMarketCreateReq.getName())){
            RequestMarket requestMarket=requestMarketRepository.findRequestMarketByName(requestMarketCreateReq.getName());
            requestMarket.plusCount();
            return RequestMarketRes.from(requestMarket);
        }
        else{
            RequestMarket requestMarket=requestMarketRepository.save(requestMarketCreateReq.toEntity());
            return RequestMarketRes.from(requestMarket);
        }
    }

    @Override
    public Page<RequestMarket> getRequestMarkets(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("count").descending());
        return requestMarketRepository.findAll(pageable);
    }
}
