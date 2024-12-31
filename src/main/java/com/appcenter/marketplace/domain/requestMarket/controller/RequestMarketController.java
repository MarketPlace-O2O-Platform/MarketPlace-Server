package com.appcenter.marketplace.domain.requestMarket.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "[요청 매장]", description = "[회원,관리자] 요청 매장 생성, 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/request-markets")
public class RequestMarketController {
}
