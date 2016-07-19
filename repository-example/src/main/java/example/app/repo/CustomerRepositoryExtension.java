package example.app.repo;

import java.util.List;

import example.app.model.Customer;

/**
 * The CustomerRepositoryExtension class...
 *
 * @author John Blum
 * @since 1.0.0
 */
public interface CustomerRepositoryExtension {

	/*
	@Query("SELECT DISTINCT customer FROM /Customers customer, /Contacts contact"
		+ " WHERE customer.firstName = contact.person.firstName AND customer.lastName = contact.person.lastName")
	*/
	List<Customer> findAllCustomersWithContactInformation();

}
