package com.appcenter.marketplace.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AopForTransaction {

    // 독립적인 트랜잭션을 만들어 비지니스 로직이 롤백과 상관 없이 락 해제를 하기 위함이다.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object proceed(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
