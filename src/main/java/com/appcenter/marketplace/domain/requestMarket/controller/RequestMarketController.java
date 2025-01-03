package com.appcenter.marketplace.domain.requestMarket.controller;


import com.appcenter.marketplace.domain.requestMarket.RequestMarket;
import com.appcenter.marketplace.domain.requestMarket.dto.req.RequestMarketCreateReq;
import com.appcenter.marketplace.domain.requestMarket.dto.res.RequestMarketRes;
import com.appcenter.marketplace.domain.requestMarket.service.RequestMarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_CREATE;
import static com.appcenter.marketplace.global.common.StatusCode.MARKET_FOUND;

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

    @Operation(summary = "요청 매장 조회", description = "요청 매장을 반환합니다. <br>" +
            "page의 기본값은 1입니다. <br>"+
            "size의 기본값은 10입니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<Page<RequestMarket>>> createRequestMarket(
            @RequestParam(defaultValue = "1", name = "page") Integer page,
            @RequestParam(defaultValue = "10", name = "size") Integer size) {
        return ResponseEntity
                .status(MARKET_FOUND.getStatus())
                .body(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,requestMarketService.getRequestMarkets(page,size)));
    }
}
