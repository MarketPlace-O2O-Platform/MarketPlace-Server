package com.appcenter.marketplace.domain.favorite.controller;


import com.appcenter.marketplace.domain.favorite.service.FavoriteService;
import com.appcenter.marketplace.global.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.appcenter.marketplace.global.common.StatusCode.FAVORITE_CREATE;

@Tag(name = "[찜]", description = "[찜] 매장 찜하기")
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

}
