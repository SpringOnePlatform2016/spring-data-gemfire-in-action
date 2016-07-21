package example.app.config;

import com.gemstone.gemfire.cache.Cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.GemfireTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring {@link Configuration} class used to configure local Apache Geode (or Pivotal GemFire) Cache transactions.
 *
 * @author John Blum
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.data.gemfire.GemfireTransactionManager
 * @see org.springframework.transaction.annotation.EnableTransactionManagement
 * @see org.springframework.transaction.PlatformTransactionManager
 * @see example.app.config.ApplicationConfiguration
 * @since 1.0.0
 */
@Configuration
@EnableTransactionManagement
@Import(ApplicationConfiguration.class)
@SuppressWarnings("unused")
public class LocalTransactionApplicationConfiguration {

	@Bean
	public PlatformTransactionManager transactionManager(Cache gemfireCache) {
		return new GemfireTransactionManager(gemfireCache);
	}
}
