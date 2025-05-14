package com.appcenter.marketplace.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD) // 메서드에 사용가능
@Retention(RetentionPolicy.RUNTIME) // AOP 사용가능
public @interface DistributeLock {
    String key(); // 락의 키(이름)
    TimeUnit timeUnit() default TimeUnit.SECONDS; // 시간 단위
    long waitTime() default 5L; // 대기 시간
    long leaseTime() default 3L; // 점유 시간: -1이면 명시적으로 unlock 할 때까지 유지됨
}
