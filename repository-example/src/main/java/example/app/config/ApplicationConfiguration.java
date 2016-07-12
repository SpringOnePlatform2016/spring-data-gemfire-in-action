package example.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * Spring @{@link Configuration} class used to configure the application services and data access objects.
 *
 * @author John Blum
 * @since 1.0.0
 *
 * @author John Blum
 * @since 1.0.0
 */
@Configuration
@EnableGemfireRepositories(basePackages = "example.app.repo")
@Import(GemFireConfiguration.class)
public class ApplicationConfiguration {

}
