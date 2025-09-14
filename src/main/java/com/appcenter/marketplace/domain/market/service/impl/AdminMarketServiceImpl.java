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
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketRes;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.market.service.AdminMarketService;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminMarketServiceImpl implements AdminMarketService {
    private final MarketRepository marketRepository;
    private final CategoryRepository categoryRepository;
    private final LocalRepository localRepository;
    private final MarketService marketService;
    private final ImageService imageService;
    private final CouponOwnerService couponOwnerService;
    private final MemberCouponService memberCouponService;

    @Override
    public MarketPageRes<MarketRes> getAllMarkets(Long marketId, Integer size, String major) {
        List<MarketRes> marketResList;
        if (major == null) {
            marketResList = marketRepository.findMarketListForAdmin(marketId, size);
        } else if (Major.exists(major)) {
            marketResList = marketRepository.findMarketListByCategoryForAdmin(marketId, size, major);
        } else throw new CustomException(CATEGORY_NOT_EXIST);

        return checkNextPageAndReturn(marketResList, size);
    }

    @Override
    public MarketDetailsRes getMarketDetails(Long marketId) {
        return marketService.getMarketDetails(marketId);
    }

    @Override
    @Transactional
    public MarketDetailsRes createMarket(MarketReq marketReq, List<MultipartFile> multipartFileList) {
        Category category = findCategoryByMajor(marketReq.getMajor());

        StringTokenizer st = new StringTokenizer(marketReq.getAddress());

        if (st.countTokens() < 2) {
            throw new CustomException(ADDRESS_INVALID);
        }

        Local local = localRepository.findByAdress(st.nextToken(), st.nextToken());

        Market market = marketRepository.save(marketReq.toEntity(category, local));
        imageService.createImage(market, multipartFileList);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public MarketDetailsRes updateMarket(Long marketId, MarketReq marketReq) {
        Market market = findMarketByMarketId(marketId);
        Category category = findCategoryByMajor(marketReq.getMajor());
        market.updateMarketInfo(marketReq, category);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public void deleteMarket(Long marketId) {
        Market market = findMarketByMarketId(marketId);

        List<Coupon> couponList = couponOwnerService.getCoupons(market.getId());
        List<Long> couponIds = new ArrayList<>();
        for (Coupon coupon : couponList) {
            couponIds.add(coupon.getId());
        }

        memberCouponService.hardDeleteCoupon(couponIds);
        couponOwnerService.hardDeleteCoupon(market.getId());
        imageService.softDeleteImage(marketId);
        market.softDeleteMarket();
    }

    private Category findCategoryByMajor(String major) {
        if (Major.exists(major)) {
            return categoryRepository.findByMajor(Major.valueOf(major))
                    .orElseThrow(() -> new CustomException(CATEGORY_NOT_EXIST));
        } else throw new CustomException(CATEGORY_NOT_EXIST);
    }

    private Market findMarketByMarketId(Long marketId) {
        return marketRepository.findById(marketId)
                .orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));
    }

    private <T> MarketPageRes<T> checkNextPageAndReturn(List<T> marketResList, Integer size) {
        boolean hasNext = false;

        if (marketResList.size() > size) {
            hasNext = true;
            marketResList.remove(size.intValue());
        }

        return new MarketPageRes<>(marketResList, hasNext);
    }
}