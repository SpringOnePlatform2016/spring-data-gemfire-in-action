package example.app.config;

import org.springframework.cache.CacheManager;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.support.GemfireCacheManager;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;
import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig;

@Configuration
@Profile({ "cloud" })
public class CacheConfiguration extends AbstractCloudConfig {

	
	private ServiceConnectorConfig createGemfireConnectorConfig() {
		GemfireServiceConnectorConfig gemfireConfig = new GemfireServiceConnectorConfig();
		return gemfireConfig;
	}


	@Bean
	ClientCache clientCache() {
		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
	    ClientCache cache = cloud.getServiceConnector("hero-cache", ClientCache.class, createGemfireConnectorConfig());
		return cache;
	}
	
	@Bean
	 public CacheManager cacheManager(final Cache gemfireCache) {
		 return new GemfireCacheManager() {{
	            setCache(gemfireCache);
	        }};
	 }


	@Bean
	public ClientRegionFactoryBean<String, String> personRegion(ClientCache cache) {
		ClientRegionFactoryBean<String,String> region = new ClientRegionFactoryBean<>();
		region.setCache(cache);
		region.setName("hero");
		region.setShortcut(ClientRegionShortcut.PROXY);
		return region;
	}

}
