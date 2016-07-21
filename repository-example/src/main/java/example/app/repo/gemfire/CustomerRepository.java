package example.app.repo.gemfire;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.query.annotation.Trace;

import example.app.model.Customer;

/**
 * Spring Data {@link GemfireRepository} interface for performing basic data access, CRUD and query operations on
 * {@link Customer} objects stored and managed in Apache Geode (or Pivotal GemFire).
 *
 * @author John Blum
 * @see org.springframework.data.gemfire.repository.GemfireRepository
 * @see example.app.model.Customer
 * @see example.app.repo.gemfire.CustomerRepositoryExtension
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends GemfireRepository<Customer, Long>, CustomerRepositoryExtension {

	@Trace
	Customer findByAccountNumber(String accountNumber);

}
