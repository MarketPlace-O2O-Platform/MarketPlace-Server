package com.appcenter.marketplace.domain.market.service.impl;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.category.CategoryRepository;
import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.service.CouponOwnerService;
import com.appcenter.marketplace.domain.image.service.ImageService;
import com.appcenter.marketplace.domain.local.Local;
import com.appcenter.marketplace.domain.local.repository.LocalRepository;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.dto.req.MarketReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReq;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.market.service.MarketOwnerService;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class MarketOwnerServiceImpl implements MarketOwnerService {
    private final MarketRepository marketRepository;
    private final CategoryRepository categoryRepository;
    private final LocalRepository localRepository;
    private final MarketService marketService;
    private final ImageService imageService;
    private final CouponOwnerService couponOwnerService;
    private final MemberCouponService memberCouponService;


    @Override
    @Transactional
    public MarketDetailsRes createMarket(MarketReq marketReq, List<MultipartFile> multipartFileList){
        Category category=findCategoryByMajor(marketReq.getMajor());

        StringTokenizer st= new StringTokenizer(marketReq.getAddress());

        // 두 개 이상의 단어가 있을 경우만 처리
        if (st.countTokens() < 2) {
            throw new CustomException(ADDRESS_INVALID);
        }

        Local local=localRepository.findByAdress(st.nextToken(),st.nextToken());

        Market market=marketRepository.save(marketReq.toEntity(category,local));
        imageService.createImage(market,multipartFileList);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public MarketDetailsRes updateMarket(Long marketId, MarketReq marketReq) {
        Market market=findMarketByMarketId(marketId);
        Category category=findCategoryByMajor(marketReq.getMajor());
        market.updateMarketInfo(marketReq,category);
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
    public void softDeleteMarket(Long marketId) {

        Market market=findMarketByMarketId(marketId);

        // 각 매장의 생성된 쿠폰 일괄 조회
        List<Coupon> couponList = couponOwnerService.getCoupons(market.getId());
        List<Long> couponIds = new ArrayList<>();
        for(Coupon coupon:couponList){
            couponIds.add(coupon.getId());
        }

        // memberCoupon 삭제
        memberCouponService.hardDeleteCoupon(couponIds);

        // coupon 삭제
        couponOwnerService.hardDeleteCoupon(market.getId());

        // 매장 삭제
        imageService.softDeleteImage(marketId);
        market.softDeleteMarket();
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
