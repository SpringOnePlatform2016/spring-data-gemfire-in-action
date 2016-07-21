package example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import example.app.function.CustomerFunctions;
import example.app.service.CustomerService;

/**
 * Spring @{@link Configuration} class used to configure the application services and data access objects.
 *
 * @author John Blum
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.Import
 * @see org.springframework.data.gemfire.function.config.EnableGemfireFunctions
 * @see org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions
 * @see org.springframework.data.gemfire.repository.config.EnableGemfireRepositories
 * @see example.app.config.GemFireConfiguration
 * @see example.app.config.JpaConfiguration
 * @since 1.0.0
 */
@Configuration
@EnableGemfireFunctions
@EnableGemfireFunctionExecutions(basePackages = "example.app.function.executions")
@EnableGemfireRepositories(basePackages = "example.app.repo.gemfire")
@Import({ GemFireConfiguration.class, JpaConfiguration.class })
@SuppressWarnings("unused")
public class ApplicationConfiguration {

	@Bean
	public CustomerFunctions customerFunctions() {
		return new CustomerFunctions();
	}

	@Bean
	public CustomerService customerService() {
		return new CustomerService();
	}
}
