package example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The RepositoryExampleApplication class is a {@link SpringBootApplication} that demonstrates examples of
 * Spring Data {@link org.springframework.data.gemfire.repository.GemfireRepository}
 * and {@link org.springframework.data.jpa.repository.JpaRepository} usages.
 *
 * @author John Blum
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @since 1.0.0
 */
@SpringBootApplication
public class RepositoryExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepositoryExampleApplication.class, args);
	}
}
