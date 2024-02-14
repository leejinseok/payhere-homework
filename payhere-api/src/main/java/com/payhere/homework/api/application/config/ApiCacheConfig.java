package com.payhere.homework.api.application.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class ApiCacheConfig {

    public static final String SAMPLE_CACHE = "sample";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = new ArrayList<>();
        caches.add(scrapCache());
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    private CaffeineCache scrapCache() {
        return new CaffeineCache(SAMPLE_CACHE, Caffeine.newBuilder().recordStats()
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .maximumSize(100)
                .build());
    }

}
