package example.app.repo;

import java.util.List;

import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.gemfire.repository.query.annotation.Trace;

import example.app.model.Customer;

/**
 * Spring Data {@link GemfireRepository} implementation to perform data access CRUD and query operations on
 * {@link Customer} objects.
 *
 * @author John Blum
 * @see org.springframework.data.gemfire.repository.GemfireRepository
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends GemfireRepository<Customer, Long> {

	@Trace
	Customer findByAccountNumber(String accountNumber);

	@Trace
	@Query("SELECT DISTINCT customer FROM /Customers customer, /Contacts contact"
		+ " WHERE customer.firstName = contact.person.firstName AND customer.lastName = contact.person.lastName")
	List<Customer> findAllCustomersWithContactInformation();

}
