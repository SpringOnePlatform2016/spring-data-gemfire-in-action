package example.app.repo;

import static example.app.model.Address.newAddress;
import static example.app.model.Contact.newContact;
import static example.app.model.Customer.newCustomer;
import static example.app.model.PhoneNumber.newPhoneNumber;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import com.gemstone.gemfire.cache.Region;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.app.config.ApplicationConfiguration;
import example.app.function.CustomerFunctions;
import example.app.function.executions.CustomerFunctionExecutions;
import example.app.model.Customer;
import example.app.model.State;

/**
 * Test suite of test cases testing the contract and functionality of the {@link CustomerRepository} class.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see example.app.repo.CustomerRepository
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfiguration.class, CustomerFunctions.class })
@SuppressWarnings("unused")
public class CustomerRepositoryTests {

	protected static final AtomicLong ID_GENERATOR = new AtomicLong(0L);

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CustomerFunctionExecutions customerFunctionExecutions;

	@Autowired
	private CustomerRepository customerRepository;

	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	protected String generateAccountNumber() {
		return UUID.randomUUID().toString();
	}

	protected long generateId() {
		return ID_GENERATOR.incrementAndGet();
	}

	@Test
	public void findCustomerForAccountIsSuccessful() {
		Customer jonDoe = newCustomer("Jon", "Doe").with(generateAccountNumber()).with(generateId());
		Customer janeDoe = newCustomer("Jane", "Doe").with(generateAccountNumber()).with(generateId());

		customerRepository.save(jonDoe);
		customerRepository.save(janeDoe);

		Customer jonDoeByAccountNumber = customerRepository.findByAccountNumber(jonDoe.getAccountNumber());

		assertThat(jonDoeByAccountNumber).isEqualTo(jonDoe);
	}

	/**
	 * FAIL!!!
	 * @see <a href="http://gemfire.docs.pivotal.io/docs-gemfire/latest/developing/query_additional/partitioned_region_query_restrictions.html#concept_5353476380D44CC1A7F586E5AE1CE7E8">Partition Region Query Restrictions</a>
	 */
	@Test
	@Ignore
	public void findAllCustomersWithContactsIsSuccessful() {
		Customer jonDoe = newCustomer("Jon","Doe").with(generateAccountNumber()).with(generateId());
		Customer janeDoe = newCustomer("Jane", "Doe").with(generateAccountNumber()).with(generateId());
		Customer jackHandy = newCustomer("Jack", "Handy").with(generateAccountNumber()).with(generateId());

		contactRepository.save(newContact(customerRepository.save(jonDoe), "jonDoe@home.com")
			.with(newPhoneNumber("503", "555", "1234")).with(generateId()));

		customerRepository.save(janeDoe);

		contactRepository.save(newContact(customerRepository.save(jackHandy), "jackHandy@office.com")
			.with(newAddress("100 Main St.", "Portland", State.OREGON, "97205")).with(generateId()));

		List<Customer> customers = customerRepository.findAllCustomersWithContactInformation();

		assertThat(customers).isNotNull();
		assertThat(customers.size()).isEqualTo(2);
		assertThat(customers).containsAll(Arrays.asList(jonDoe, jackHandy));
	}

	@Test
	public void findAllCustomersWithContactsUsingFunctionIsSuccessful() {
		Customer jonDoe = newCustomer("Jon","Doe").with(generateAccountNumber()).with(generateId());
		Customer janeDoe = newCustomer("Jane", "Doe").with(generateAccountNumber()).with(generateId());
		Customer jackHandy = newCustomer("Jack", "Handy").with(generateAccountNumber()).with(generateId());

		contactRepository.save(newContact(customerRepository.save(jonDoe), "jonDoe@home.com")
			.with(newPhoneNumber("503", "555", "1234")).with(generateId()));

		customerRepository.save(janeDoe);

		contactRepository.save(newContact(customerRepository.save(jackHandy), "jackHandy@office.com")
			.with(newAddress("100 Main St.", "Portland", State.OREGON, "97205")).with(generateId()));

		List<?> results = customerFunctionExecutions.findAllCustomersWithContactInformation();

		List<Customer> customers = toCustomerList(results);

		assertThat(customers).isNotNull();
		assertThat(customers.size()).isEqualTo(2);
		assertThat(customers).containsAll(Arrays.asList(jonDoe, jackHandy));
	}

	@SuppressWarnings("unchecked")
	protected List<Customer> toCustomerList(List<?> list) {
		assertThat(list.size()).isEqualTo(1);
		assertThat(list.get(0)).isInstanceOf(List.class);
		return (List<Customer>) list.get(0);
	}
}
