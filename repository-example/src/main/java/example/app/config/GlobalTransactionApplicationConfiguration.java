package example.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The GlobalTransactionApplicationConfiguration class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@Configuration
@EnableTransactionManagement
@Import(ApplicationConfiguration.class)
@SuppressWarnings("unused")
public class GlobalTransactionApplicationConfiguration {

}
