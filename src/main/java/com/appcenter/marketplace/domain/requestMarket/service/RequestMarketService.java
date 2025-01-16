package com.appcenter.marketplace.domain.requestMarket.service;

import com.appcenter.marketplace.domain.requestMarket.dto.req.RequestMarketCreateReq;
import com.appcenter.marketplace.domain.requestMarket.dto.res.RequestMarketRes;
import org.springframework.data.domain.Page;

public interface RequestMarketService {

    RequestMarketRes createRequestMarket(RequestMarketCreateReq requestMarketCreateReq);

    Page<RequestMarketRes> getRequestMarkets(Integer page, Integer size);
}
