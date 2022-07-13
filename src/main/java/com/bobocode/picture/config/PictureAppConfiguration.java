package com.bobocode.picture.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableScheduling
@EnableCaching
@Configuration
public class PictureAppConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Scheduled(timeUnit = TimeUnit.DAYS, fixedRate = 1)
    @CacheEvict(cacheNames = "maxSizePicture", allEntries = true)
    public void clearCache() {
        log.info("Clear maxSizePicture cache every day!");
    }
}
