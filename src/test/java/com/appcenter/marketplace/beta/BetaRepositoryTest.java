package com.appcenter.marketplace.beta;


import com.appcenter.marketplace.domain.beta.repository.BetaCouponRepository;
import com.appcenter.marketplace.domain.beta.repository.BetaMarketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class BetaRepositoryTest {
    @Autowired
    BetaMarketRepository betaMarketRepository;
    
    @Autowired
    BetaCouponRepository betaCouponRepository;
    
    @Test
    @DisplayName("베타 쿠폰 조회 테스트")
    public void testFindAllCoupons() throws Exception{
        // given

        // when
    
        // then
    }
}
