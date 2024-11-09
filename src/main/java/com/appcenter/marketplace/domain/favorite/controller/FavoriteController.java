package com.appcenter.marketplace.domain.favorite.controller;


import com.appcenter.marketplace.domain.favorite.service.FavoriteService;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageResDto;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.appcenter.marketplace.global.common.StatusCode.FAVORITE_CREATE;
import static com.appcenter.marketplace.global.common.StatusCode.MARKET_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Operation(summary = "매장 찜하기", description = "매장 찜이 없으면 생성합니다. 있으면 삭제합니다.<br>" +
        "소프트 딜리트를 적용합니다.")
    @PostMapping
    public ResponseEntity<CommonResponse<Object>> createCoupon(@RequestParam Long memberId, @RequestParam Long marketId) {
        favoriteService.createOrDeleteFavorite(memberId,marketId);
        return ResponseEntity.status(FAVORITE_CREATE.getStatus()).body(CommonResponse.from(FAVORITE_CREATE.getMessage()));
    }

    @Operation(summary = "찜한 매장 리스트 조회",
            description = "찜한 매장 정보 리스트를 반환합니다. 무한 스크롤 방식으로 구현하였고, " +
                    "처음 요청 시엔 pageSize만 보내주면 됩니다. <br>" +
                    "그 이후론 매장 정보 리스트에서 마지막 요소의 marketId를 lastPageIndex에 추가해주세요. <br>" +
                    "JWT 구현 전 까지는 memberId를 추가해야합니다. <br>" +
                    "최신순으로 보여줍니다. 또한 pageSize의 기본값은 5입니다.")
    @GetMapping("/markets")
    public ResponseEntity<CommonResponse<MarketPageResDto>> getFavoriteMarketList(
            @RequestParam Long memberId,
            @RequestParam(required = false, name = "lastPageIndex") Long marketId,
            @RequestParam(defaultValue = "5", name = "pageSize") Integer size) {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,favoriteService.getFavoriteMarketPage(memberId,marketId,size)));
    }
}
