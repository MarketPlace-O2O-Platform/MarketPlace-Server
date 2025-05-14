package com.appcenter.marketplace.global.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    private static final String REDISSON_HOST_PREFIX = "redis://";

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(REDISSON_HOST_PREFIX + host + ":" + port);
        return Redisson.create(config);
    }

    // Redis 캐시 매니저 설정
    //GenericJackson2JsonRedisSerializer()을 통해 json으로 직렬화 하면 객체의 패키지 정보까지 저장한다.
    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
//        Map<String, CacheConfig> config = new HashMap<>();
//        config.put("exampleCache", new CacheConfig(
//                TimeUnit.HOURS.toMillis(1), // TTL 1 hour
//                TimeUnit.MINUTES.toMillis(30) // Max idle time 30 minutes
//        ));
        return new RedissonSpringCacheManager(redissonClient);
    }
}
