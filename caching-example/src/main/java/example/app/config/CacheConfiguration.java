package example.app.config;

import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientRegionShortcut;

import example.app.model.Customer;
import example.app.model.CustomerKeyGenerator;

@Configuration
@Profile({ "cloud" })
public class CacheConfiguration extends AbstractCloudConfig {
	
	@Value("${cache_service_name}")
	private String cache_service_name;
	
	@Value("${region_name}")
	private String region_name;
	
	/**
	 * This is where the Cache behavior is configured
	 * @return ServiceConnectorConfig
	 */
	public ServiceConnectorConfig createGemfireConnectorConfig() {
	    GemfireServiceConnectorConfig gemfireConfig = new GemfireServiceConnectorConfig();
	    gemfireConfig.setPoolIdleTimeout(7777L);
	    //gemfireConfig.setPdxSerializer(new ReflectionBasedAutoSerializer(pdx_class));
	    gemfireConfig.setPdxPersistent(true);
	    return gemfireConfig;
	  }


	@Bean
	ClientCache clientCache() {
		CloudFactory cloudFactory = new CloudFactory();
		Cloud cloud = cloudFactory.getCloud();
	    ClientCache cache = cloud.getServiceConnector(cache_service_name, ClientCache.class, createGemfireConnectorConfig());
		return cache;
	}

	@Bean
	public ClientRegionFactoryBean<String, Customer> personRegion(ClientCache cache) {
		ClientRegionFactoryBean<String,Customer> region = new
		ClientRegionFactoryBean<>();
		region.setCache(cache);
		region.setName(region_name);
		region.setShortcut(ClientRegionShortcut.PROXY);
		return region;

	}

	@Bean(name = "keyGenerator")
	CustomerKeyGenerator getKeyGenerator() {
		return new CustomerKeyGenerator();
	}

}
