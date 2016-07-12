package example.app.config;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.support.GemfireCacheManager;

import com.gemstone.gemfire.cache.Cache;

import example.app.model.CustomerKeyGenerator;

@Configuration
public class CacheConfiguration extends AbstractCloudConfig {

	@Bean
    GemfireCacheManager cacheManager(final Cache gemfireCache) {
        GemfireCacheManager cacheManager = new GemfireCacheManager();
        cacheManager.setCache(gemfireCache);
        return cacheManager;
    }
	
	@Bean(name="keyGenerator")
	CustomerKeyGenerator getKeyGenerator() {
		return new CustomerKeyGenerator();
	}
}
