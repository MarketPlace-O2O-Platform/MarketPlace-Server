package com.appcenter.marketplace.domain.requestMarket.service.impl;

import com.appcenter.marketplace.domain.requestMarket.RequestMarket;
import com.appcenter.marketplace.domain.requestMarket.dto.req.RequestMarketCreateReq;
import com.appcenter.marketplace.domain.requestMarket.dto.res.RequestMarketRes;
import com.appcenter.marketplace.domain.requestMarket.repository.RequestMarketRepository;
import com.appcenter.marketplace.domain.requestMarket.service.RequestMarketService;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_NOT_EXIST;

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
    public Page<RequestMarketRes> getRequestMarkets(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("count").descending());
        Page<RequestMarket> requestMarketPage= requestMarketRepository.findAll(pageable);

        return requestMarketPage.map(RequestMarketRes::from);
    }

    @Override
    public boolean existRequestMarket(String marketName) {
        return requestMarketRepository.existsByName(marketName);
    }

    @Override
    public void hardDeleteRequestMarket(Long marketId) {
        requestMarketRepository.deleteById(marketId);
    }

    @Override
    public RequestMarket getRequestMarketName(String marketName) {
        return requestMarketRepository.findRequestMarketByName(marketName);
    }

    @Override
    @Transactional
    public RequestMarketRes enrollRequestMarket(Long requestMarketId) {
        RequestMarket requestMarket = requestMarketRepository.findById(requestMarketId)
                .orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));

        requestMarket.completeEnroll();

        return RequestMarketRes.from(requestMarket);
    }
}
