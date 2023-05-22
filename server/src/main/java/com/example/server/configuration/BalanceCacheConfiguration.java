package com.example.server.configuration;

import lombok.RequiredArgsConstructor;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

/**
 * Класс конфигурации для кэша.
 * @author KhanSOLO
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class BalanceCacheConfiguration {

    /**
     * Метод возвращающий сконфигурированный и инициализированный ehCache для BalanceService
     * @return {@link javax.cache.CacheManager}
     */
    @Bean
    public CacheManager ehCacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        CacheConfigurationBuilder<Long, Long> configuration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Long.class,
                                Long.class,
                                ResourcePoolsBuilder
                                        .newResourcePoolsBuilder().offheap(10, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(10)));

        javax.cache.configuration.Configuration<Long, Long> stringDoubleConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

        cacheManager.createCache("balanceCache", stringDoubleConfiguration);
        return cacheManager;
    }

}
