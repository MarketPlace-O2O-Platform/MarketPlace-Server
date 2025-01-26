package com.appcenter.marketplace.domain.tempMarket.controller;

import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketPageRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import com.appcenter.marketplace.domain.tempMarket.service.TempMarketService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.appcenter.marketplace.global.common.StatusCode.MARKET_FOUND;

@Tag(name = "[회원 공감매장]", description = "[회원] 공감매장 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tempMarkets")
public class TempMarketController {
    private final TempMarketService tempMarketService;

    @Operation(summary = "매장 기본 조회 (전체/카테고리별 조회)", description = "기본 실행은 매장 전체 조회힙니다. <br>" +
            "'category'를 입력하면 카테고리별 조회가 가능합니다. <br>" +
            "'lastPageIndex를 입력하면 페이징 처리를 통해 무한 스크롤 방식의 매장 조회가 가능힙니다. <br>" +
            "'count'는 기본 10개씩 입니다. API를 조회할 시 불러와지는 매장의 갯수입니다.")
    @GetMapping
    public ResponseEntity<CommonResponse<TempMarketPageRes<TempMarketRes>>> getTempMarket(
            @RequestParam Long memberId,
            @RequestParam(required = false, name = "lastPageIndex") Long tempMarketId,
            @RequestParam(required = false, name = "category") String category,
            @RequestParam(defaultValue = "10", name = "count") Integer size
    ) {
        return ResponseEntity.status(MARKET_FOUND.getStatus())
                .body(CommonResponse.from(MARKET_FOUND.getMessage(), tempMarketService.getMarketList(memberId, tempMarketId, size, category)));
    }

    @Operation(summary = "달성 임박 매장 조회", description = "달성이 임박한 매장을 공감순으로 조회합니다. <br>" +
            "20개가 각 매장의 목표 공감수 입니다. 이중 14개(70%) 이상이 될 경우에만 조회가 가능합니다. 또한, 공감수(cheerCount)가 20개 이상이면, '매장 컨텍 중'으로 보여주시면 됩니다. <br>" +
            "'lastPageIndex', 'lastCheerCount'를 입력하면 페이징 처리를 통해 무한 스크롤 방식의 매장 조회가 가능합니다. <br>" +
            "'count'는 기본 10개씩입니다. API를 조회할 시 불러와지는 매장의 갯수입니다.")
    @GetMapping("/cheer")
    public ResponseEntity<CommonResponse<TempMarketPageRes<TempMarketRes>>> getTempMarketPage(
            @RequestParam Long memberId,
            @RequestParam(required = false, name = "lastPageIndex") Long tempMarketId,
            @RequestParam(required = false, name = "lastCheerCount") Integer cheerCount,
            @RequestParam(defaultValue = "10", name = "count") Integer size
    ) {
        return ResponseEntity.status(MARKET_FOUND.getStatus())
                .body(CommonResponse.from(MARKET_FOUND.getMessage(), tempMarketService.getUpcomingNearMarketList(memberId, tempMarketId, cheerCount, size)));
    }

    @Operation(summary = "검색 매장 조회",
            description = "처음 요청 시, lastPageIndex는 필요하지 않습니다. <br>" +
                    "최신순으로 보여줍니다.")
    @GetMapping("/search")
    public ResponseEntity<CommonResponse<TempMarketPageRes<TempMarketRes>>> searchMarket(
            @RequestParam Long memberId,
            @Parameter(description = "페이지의 마지막 marketId")
            @RequestParam(required = false, name = "lastPageIndex") Long marketId,
            @RequestParam(defaultValue = "10", name = "pageSize") Integer size,
            @RequestParam String name)
    {
        return ResponseEntity
                .ok(CommonResponse.from(MARKET_FOUND.getMessage()
                        ,tempMarketService.searchMarket(memberId, marketId, size,name)));
    }
}
