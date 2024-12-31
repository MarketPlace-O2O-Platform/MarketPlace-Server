package com.appcenter.marketplace.domain.requestMarket.controller;


import com.appcenter.marketplace.domain.requestMarket.dto.req.RequestMarketCreateReq;
import com.appcenter.marketplace.domain.requestMarket.dto.res.RequestMarketRes;
import com.appcenter.marketplace.domain.requestMarket.service.RequestMarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_CREATE;

@Tag(name = "[요청 매장]", description = "[회원,관리자] 요청 매장 생성, 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/request-markets")
public class RequestMarketController {
    private final RequestMarketService requestMarketService;

    @Operation(summary = "요청 매장 생성", description = "1개의 요청 매장을 생성합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<RequestMarketRes>> createRequestMarket(
            @RequestBody @Valid RequestMarketCreateReq requestMarketCreateReq){
        return ResponseEntity
                .status(MARKET_CREATE.getStatus())
                .body(CommonResponse.from(MARKET_CREATE.getMessage()
                        ,requestMarketService.createRequestMarket(requestMarketCreateReq)));
    }
}
