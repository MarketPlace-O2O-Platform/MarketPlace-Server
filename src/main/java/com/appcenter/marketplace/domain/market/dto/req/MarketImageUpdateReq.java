package com.appcenter.marketplace.domain.market.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class MarketImageUpdateReq {
    private List<Long> deletedImageIds; // 삭제할 이미지 ID 리스트
    private Map<Long, Integer> changedSequences; // 변경된 순서 (id와 sequence를 Map으로 처리)
    private List<Integer> addedImageSequences; // 추가된 이미지의 순서 리스트 (순서 번호)

}
