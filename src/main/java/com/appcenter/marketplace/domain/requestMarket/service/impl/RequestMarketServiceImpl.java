package com.appcenter.marketplace.domain.requestMarket.service.impl;

import com.appcenter.marketplace.domain.requestMarket.service.RequestMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class RequestMarketServiceImpl implements RequestMarketService {
}
