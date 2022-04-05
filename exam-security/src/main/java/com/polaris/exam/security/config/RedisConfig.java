package com.polaris.exam.security.config;

import com.polaris.exam.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author CNPolaris
 * @version 1.0
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {

}
