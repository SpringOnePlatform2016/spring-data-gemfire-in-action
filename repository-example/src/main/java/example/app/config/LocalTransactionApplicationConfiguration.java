package example.app.config;

import com.gemstone.gemfire.cache.Cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.GemfireTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The LocalTransactionApplicationConfiguration class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@Configuration
@EnableTransactionManagement
@Import(ApplicationConfiguration.class)
@SuppressWarnings("unused")
public class LocalTransactionApplicationConfiguration {

	@Bean
	public GemfireTransactionManager gemfireTransactionManager(Cache gemfireCache) {
		return new GemfireTransactionManager(gemfireCache);
	}
}
