package example.app.repo;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.query.annotation.Trace;

import example.app.model.Customer;

/**
 * Spring Data {@link GemfireRepository} implementation to perform data access, CRUD and query operations on
 * {@link Customer} objects.
 *
 * @author John Blum
 * @see org.springframework.data.gemfire.repository.GemfireRepository
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends GemfireRepository<Customer, Long>, CustomerRepositoryExtension {

	@Trace
	Customer findByAccountNumber(String accountNumber);

}
