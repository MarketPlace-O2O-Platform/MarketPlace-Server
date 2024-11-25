package com.appcenter.marketplace.domain.market.service.impl;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.category.CategoryRepository;
import com.appcenter.marketplace.domain.image.service.ImageService;
import com.appcenter.marketplace.domain.local.Local;
import com.appcenter.marketplace.domain.local.repository.LocalRepository;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.market.service.MarketOwnerService;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import static com.appcenter.marketplace.global.common.StatusCode.CATEGORY_NOT_EXIST;
import static com.appcenter.marketplace.global.common.StatusCode.MARKET_NOT_EXIST;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MarketOwnerServiceImpl implements MarketOwnerService {
    private final MarketRepository marketRepository;
    private final CategoryRepository categoryRepository;
    private final LocalRepository localRepository;
    private final MarketService marketService;
    private final ImageService imageService;


    @Override
    @Transactional
    public MarketDetailsResDto createMarket(MarketCreateReqDto marketCreateReqDto, List<MultipartFile> multipartFileList) throws IOException {
        Category category=findCategoryByMajor(marketCreateReqDto.getMajor());

        StringTokenizer st= new StringTokenizer(marketCreateReqDto.getAddress());
        Local local=localRepository.findByAdress(st.nextToken(),st.nextToken());

        Market market=marketRepository.save(marketCreateReqDto.toEntity(category,local));
        imageService.createImage(market,multipartFileList);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public MarketDetailsResDto updateMarket(Long marketId, MarketUpdateReqDto marketUpdateReqDto) {
        Market market=findMarketByMarketId(marketId);
        Category category=findCategoryByMajor(marketUpdateReqDto.getMajor());
        market.updateMarketInfo(marketUpdateReqDto,category);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public MarketDetailsResDto updateMarketImage(Long marketId, MarketImageUpdateReqDto marketImageUpdateReqDto, List<MultipartFile> multiPartFileList) throws IOException {
        Market market=findMarketByMarketId(marketId);
        imageService.UpdateImage(market,marketImageUpdateReqDto,multiPartFileList);
        return marketService.getMarketDetails(market.getId());
    }


    @Override
    @Transactional
    public void deleteMarket(Long marketId) {
        imageService.deleteAllImages(marketId);
        marketRepository.deleteById(marketId);
    }

    // 카테고리 조회
    private Category findCategoryByMajor(String major){
        // 카테고리 대분류명 존재 확인
        if(Major.exists(major)) {
            return categoryRepository.findByMajor(Major.valueOf(major))
                    .orElseThrow(() -> new CustomException(CATEGORY_NOT_EXIST));
        }
        else throw new CustomException(CATEGORY_NOT_EXIST);
    }

    // 마켓 조회
    private Market findMarketByMarketId(Long marketId){
        return marketRepository.findById(marketId)
                .orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));
    }
}
