package example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import example.app.service.CustomerService;

/**
 * Spring @{@link Configuration} class used to configure the application services and data access objects.
 *
 * @author John Blum
 * @since 1.0.0
 */
@Configuration
@EnableGemfireFunctions
@EnableGemfireFunctionExecutions(basePackages = "example.app.function.executions")
@EnableGemfireRepositories(basePackages = "example.app.repo")
@Import(GemFireConfiguration.class)
public class ApplicationConfiguration {

	@Bean
	public CustomerService customerService() {
		return new CustomerService();
	}
}
