package example.app.repo.gemfire;

import java.util.List;

import example.app.model.Customer;

/**
 * The CustomerRepositoryExtension interface is an extension of the {@link CustomerRepository} to define custom
 * {@link org.springframework.data.repository.Repository} data access operations on Apache Geode (or Pivotal GemFire).
 *
 * @author John Blum
 * @see example.app.model.Customer
 * @see example.app.repo.gemfire.CustomerRepository
 * @since 1.0.0
 */
public interface CustomerRepositoryExtension {

	/*
	@Query("SELECT DISTINCT customer FROM /Customers customer, /Contacts contact"
		+ " WHERE customer.firstName = contact.person.firstName AND customer.lastName = contact.person.lastName")
	*/
	List<Customer> findAllCustomersWithContactInformation();

}
