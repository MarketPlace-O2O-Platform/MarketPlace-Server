package com.appcenter.marketplace.domain.market.controller;


import com.appcenter.marketplace.domain.market.dto.res.*;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/markets")
public class MarketController {
    private final MarketService marketService;

    @Operation(summary = "상세 매장 조회", description = "상세 매장 정보 및 이미지 리스트를 조회합니다.")
    @GetMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketDetailsResDto>> getMarket(@PathVariable Long marketId){
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage(),marketService.getMarketDetails(marketId)));
    }

    // size가 null로 들어올 시 기본 값을 지정해주기 위해 integer로 값을 받아온다.
    @Operation(summary = "전체 매장 리스트 조회",
            description = "처음 요청 시, pageSize만 필요합니다. 기본값은 5입니다. <br>" +
                    "카테고리 별 매장을 조회하시려면 category도 필요합니다. <br>" +
                    "최신순으로 보여줍니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<MarketPageResDto<MarketResDto>>> getMarket(
            @RequestParam Long memberId,
            @Parameter(description = "페이지의 마지막 marketId")
            @RequestParam(required = false, name = "lastPageIndex") Long marketId,
            @RequestParam(required = false, name = "category") String major,
            @RequestParam(defaultValue = "5", name = "pageSize") Integer size)
    {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,marketService.getMarketPage(memberId, marketId,size,major)));
    }

    @Operation(summary = "자신이 찜한 매장 리스트 조회",
            description = "자신이 찜한 매장 정보 리스트를 반환합니다. <br>" +
                    "처음 요청 시엔 pageSize만 필요합니다.기본값은 5입니다. <br>" +
                    "JWT 구현 전 까지는 memberId를 추가해야합니다. <br>" +
                    "최신 찜 순으로 보여줍니다.")
    @GetMapping("/my-favorite-markets")
    public ResponseEntity<CommonResponse<MarketPageResDto<MyFavoriteMarketResDto>>> getMemberFavoriteMarketList(
            @RequestParam Long memberId,
            @Parameter(description = "페이지의 마지막 favoriteModifiedAt (e.g. 2024-11-25 11:19:26.378948 )")
            @RequestParam(required = false, name = "lastModifiedAt") LocalDateTime lastModifiedAt,
            @RequestParam(defaultValue = "5", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,marketService.getMemberFavoriteMarketPage(memberId,lastModifiedAt,size)));
    }

    @Operation(summary = "찜 수가 가장 많은 매장 더보기 조회",
            description = "사용자들이 가장 많이 찜한 매장 정보 리스트를 반환합니다. <br>" +
                    "처음 요청 시엔 pageSize만 필요합니다. 기본값은 5입니다. <br>" +
                    "찜 수가 가장 많은 순으로 보여줍니다.")
    @GetMapping("/favorite-markets")
    public ResponseEntity<CommonResponse<MarketPageResDto<FavoriteMarketResDto>>> getTopFavoriteMarketList(
            @RequestParam Long memberId,
            @Parameter(description = "페이지의 마지막 favoriteCount")
            @RequestParam(required = false, name = "lastFavoriteCount") Long count,
            @RequestParam(defaultValue = "5", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,marketService.getFavoriteMarketPage(memberId,count,size)));
    }

    @Operation(summary = "찜 수가 가장 많은 매장 Top 조회",
            description = "사용자들이 가장 많이 찜한 매장 Top을 반환합니다. <br>" +
                    "size의 기본값은 5입니다.")
    @GetMapping("/top-favorite-markets")
    public ResponseEntity<CommonResponse<List<TopFavoriteMarketResDto>>> getTopFavoriteMarketList(
            @RequestParam(defaultValue = "5", name = "size") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,marketService.getTopFavoriteMarkets(size)));
    }
}
