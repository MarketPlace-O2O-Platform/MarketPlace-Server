package com.appcenter.marketplace.domain.market.controller;



import com.appcenter.marketplace.domain.market.dto.res.*;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


import static com.appcenter.marketplace.global.common.StatusCode.COUPON_FOUND;
import static com.appcenter.marketplace.global.common.StatusCode.MARKET_FOUND;

@Tag(name = "[매장]", description = "[사장님,회원] 매장 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/markets")
public class MarketController {
    private final MarketService marketService;

    @Operation(summary = "매장 상세 조회", description = "상세 매장 정보 및 이미지 리스트를 조회합니다.")
    @GetMapping("/{marketId}")
    public ResponseEntity<CommonResponse<MarketDetailsRes>> getMarket(@PathVariable Long marketId){
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage(),marketService.getMarketDetails(marketId)));
    }

    // size가 null로 들어올 시 기본 값을 지정해주기 위해 integer로 값을 받아온다.
    @Operation(summary = "전체 매장 조회",
            description = "처음 요청 시, pageSize만 필요합니다. 기본값은 10입니다. <br>" +
                    "카테고리 별 매장을 조회하시려면 category도 필요합니다. <br>" +
                    "최신순으로 보여줍니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<MarketPageRes<MarketRes>>> getMarket(
            @RequestParam Long memberId,
            @Parameter(description = "페이지의 마지막 marketId")
            @RequestParam(required = false, name = "lastPageIndex") Long marketId,
            @RequestParam(required = false, name = "category") String major,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size)
    {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,marketService.getMarketPage(memberId, marketId,size,major)));
    }

    @Operation(summary = "자신이 찜한 매장 조회",
            description = "자신이 찜한 매장 정보 리스트를 반환합니다. <br>" +
                    "처음 요청 시엔 pageSize만 필요합니다.기본값은 10입니다. <br>" +
                    "JWT 구현 전 까지는 memberId를 추가해야합니다. <br>" +
                    "최신 찜 순으로 보여줍니다.")
    @GetMapping("/my-favorite")
    public ResponseEntity<CommonResponse<MarketPageRes<MyFavoriteMarketRes>>> getMemberFavoriteMarketList(
            @RequestParam Long memberId,
            @Parameter(description = "페이지의 마지막 favoriteModifiedAt (e.g. 2024-11-25 11:19:26.378948 )")
            @RequestParam(required = false, name = "lastModifiedAt") LocalDateTime lastModifiedAt,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,marketService.getMyFavoriteMarketPage(memberId,lastModifiedAt,size)));
    }

    @Operation(summary = "찜 수가 가장 많은 매장 더보기 조회",
            description = "사용자들이 가장 많이 찜한 매장 정보 리스트를 반환합니다. <br>" +
                    "처음 요청 시엔 pageSize만 필요합니다. 기본값은 10입니다. <br>" +
                    "찜 수가 가장 많은 순으로 보여줍니다.")
    @GetMapping("/favorite")
    public ResponseEntity<CommonResponse<MarketPageRes<FavoriteMarketRes>>> getTopFavoriteMarketList(
            @RequestParam Long memberId,
            @Parameter(description = "페이지의 마지막 marketId")
            @RequestParam(required = false, name = "lastMarketId") Long marketId,
            @Parameter(description = "페이지의 마지막 favoriteCount")
            @RequestParam(required = false, name = "lastFavoriteCount") Long count,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,marketService.getFavoriteMarketPage(memberId,marketId,count,size)));
    }

    @Operation(summary = "찜 수가 가장 많은 매장 TOP 조회",
            description = "사용자들이 가장 많이 찜한 매장 Top을 반환합니다. <br>" +
                    "size의 기본값은 10입니다.")
    @GetMapping("/top-favorite")
    public ResponseEntity<CommonResponse<List<TopFavoriteMarketRes>>> getTopFavoriteMarketList(
            @RequestParam(defaultValue = "10", name = "size") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,marketService.getTopFavoriteMarkets(size)));
    }

    @Operation(summary = "최신 등록 쿠폰 TOP 조회",
            description = "매장별 최신 등록한 쿠폰을 조회합니다. 기본값은 10개 입니다. 이때, TOP 20으로 변동해야 할 시, count를 20으로 넣어주시면 됩니다. <br>" +
                    "'최신 등록'이란, 수정포함입니다.<br>" +
                    " 즉, 사장님이 쿠폰 내용을 수정하거나, 숨김/보기 처리를 하게 되면, 최신 등록 집계에 포함이 됩니다.")
    @GetMapping("/top-latest-coupon")
    public ResponseEntity<CommonResponse<List<TopLatestCouponRes>>> getLatestTopCouponList(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "10", name = "count") Integer size) {
        return ResponseEntity.ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                marketService.getTopLatestCoupons(memberId, size)));
    }

    @Operation(summary = "최신 등록 쿠폰의 매장 더보기 조회",
            description = "조회 기준은 다음과 같습니다. <br>" +
                    "- 각 매장별 최근에 등록된 쿠폰을 시간순으로 정렬 <br>" +
                    "즉, 가장 최근에 등록된 쿠폰의 매장 순으로 리스트가 조회됩니다. <br> <br>" +
                    "처음 요청 시, pageSize만 보내면 됩니다. (기본값은 10입니다) <br>" +
                    "hasNext가 true일 시, 다음 페이지가 존재합니다.<br>"
    )
    @GetMapping("/latest-coupon")
    public ResponseEntity<CommonResponse<MarketPageRes<LatestCouponRes>>> getLatestMarketByCoupon(
            @RequestParam(required = true) Long memberId,
            @Parameter(description = "각 페이지의 마지막 couponId (e.g. 5)")
            @RequestParam(required = false, name = "lastPageIndex") Long couponId,
            @Parameter(description = "위에 작성한 couponId의 modifiedAt (e.g. 2024-11-20T00:59:33.469  OR  2024-11-20T00:59:33.469664 )")
            @RequestParam(required = false, name = "lastModifiedAt") LocalDateTime lastModifiedAt,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage(),
                        marketService.getLatestCouponPage(memberId, lastModifiedAt, couponId, size)));
    }

    @Operation(summary = "마감 임박 쿠폰 TOP 조회",
            description = "매장별 마감 임박학 쿠폰 1개를 조회하여 보여줍니다. <br>" +
                    "이때, TOP 5으로 변동해야 할 시, count 로 5을 넣어주시면 됩니다. 기본값은 10개 입니다. <br>" +
                    "만약 쿠폰의 마감일자가 같을 시, 최신 등록 매장 순으로 보여지게 됩니다.")
    @GetMapping("/top-closing-coupon")
    public ResponseEntity<CommonResponse<List<TopClosingCouponRes>>> getClosingTopCouponList(
            @RequestParam(defaultValue = "10", name="count") Integer size){
        return ResponseEntity.ok(CommonResponse.from(COUPON_FOUND.getMessage(),
                marketService.getTopClosingCoupons(size)));
    }
}
