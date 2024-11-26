package com.appcenter.marketplace.domain.market.service.impl;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.category.CategoryRepository;
import com.appcenter.marketplace.domain.image.service.ImageService;
import com.appcenter.marketplace.domain.local.Local;
import com.appcenter.marketplace.domain.local.repository.LocalRepository;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReq;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.market.service.MarketOwnerService;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.StringTokenizer;

import static com.appcenter.marketplace.global.common.StatusCode.*;

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
    public MarketDetailsRes createMarket(MarketCreateReq marketCreateReq, List<MultipartFile> multipartFileList){
        Category category=findCategoryByMajor(marketCreateReq.getMajor());

        StringTokenizer st= new StringTokenizer(marketCreateReq.getAddress());

        // 두 개 이상의 단어가 있을 경우만 처리
        if (st.countTokens() < 2) {
            throw new CustomException(ADDRESS_INVALID);
        }

        Local local=localRepository.findByAdress(st.nextToken(),st.nextToken());

        Market market=marketRepository.save(marketCreateReq.toEntity(category,local));
        imageService.createImage(market,multipartFileList);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public MarketDetailsRes updateMarket(Long marketId, MarketUpdateReq marketUpdateReq) {
        Market market=findMarketByMarketId(marketId);
        Category category=findCategoryByMajor(marketUpdateReq.getMajor());
        market.updateMarketInfo(marketUpdateReq,category);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public MarketDetailsRes updateMarketImage(Long marketId, MarketImageUpdateReq marketImageUpdateReq, List<MultipartFile> multiPartFileList) {
        Market market=findMarketByMarketId(marketId);
        imageService.UpdateImage(market, marketImageUpdateReq,multiPartFileList);
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
