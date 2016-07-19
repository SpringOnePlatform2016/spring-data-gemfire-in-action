package example.app.repo.support;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import example.app.function.executions.CustomerFunctionExecutions;
import example.app.model.Customer;
import example.app.repo.CustomerRepositoryExtension;

/**
 * The CustomerRepositoryImpl class is a {@link example.app.repo.CustomerRepository} extension implementation
 * to support GemFire OQL JOINS on 2 or more collocated PARTITION Regions.
 *
 * @author John Blum
 * @see example.app.repo.CustomerRepositoryExtension
 * @since 1.0.0
 */
@Component
@SuppressWarnings("unused")
public class CustomerRepositoryImpl implements CustomerRepositoryExtension {

	@Autowired
	private CustomerFunctionExecutions customerFunctionExecutions;

	@Override
	public List<Customer> findAllCustomersWithContactInformation() {
		return toCustomerList(customerFunctionExecutions.findAllCustomersWithContactInformation());
	}

	@SuppressWarnings("unchecked")
	protected List<Customer> toCustomerList(List<?> list) {
		Assert.notNull(list, "List cannot be null");

		if (list.size() != 1) {
			throw new IncorrectResultSizeDataAccessException(1, list.size());
		}

		Assert.isTrue(list.get(0) instanceof List, "Expected a List of Lists");

		return (List<Customer>) list.get(0);
	}
}
