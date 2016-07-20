package example.app.service;

import static example.app.model.Customer.newCustomer;
import static example.app.model.PhoneNumber.newPhoneNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.app.config.LocalTransactionApplicationConfiguration;
import example.app.model.Contact;
import example.app.model.Customer;
import example.app.repo.gemfire.ContactRepository;
import example.app.repo.gemfire.CustomerRepository;

/**
 * Test suite of test cases testing the contract and transactional functionality of the {@link CustomerService} class
 * in a local transaction context.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see example.app.service.CustomerService
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LocalTransactionApplicationConfiguration.class)
@SuppressWarnings("unused")
public class CustomerServiceTransactionalTests {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerService customerService;

	@After
	public void tearDown() {
		contactRepository.deleteAll();
		customerRepository.deleteAll();
	}

	@Test
	public void createsValidContactWithCustomerAccountIsSuccessful() {
		Customer jonDoe = newCustomer("Jon", "Doe");

		assertThat(jonDoe).isNotNull();
		assertThat(jonDoe.getName()).isEqualTo("Jon Doe");
		assertThat(jonDoe.getAccountNumber()).isNull();
		assertThat(jonDoe.getId()).isNull();

		Contact contact = customerService.addContactInformation(jonDoe, "jonDoe@work.com");

		assertThat(contact).isNotNull();
		assertThat(contact.getId()).isNotNull();
		assertThat(contact.getPerson()).isEqualTo(jonDoe);
		assertThat(contact.getEmail()).isEqualTo("jonDoe@work.com");
		assertThat(contact.getPerson().getId()).isNotNull();
		assertThat(((Customer) contact.getPerson()).getAccountNumber()).isNotNull();
		assertThat(contactRepository.exists(contact.getId())).isTrue();
		assertThat(customerRepository.exists(contact.getPerson().getId())).isTrue();

		Contact updatedContact = customerService.addContactInformation(jonDoe, newPhoneNumber("503", "541", "1234"));

		assertThat(updatedContact).isNotNull();
		assertThat(updatedContact).isNotSameAs(contact);
		assertThat(updatedContact.getPerson()).isEqualTo(jonDoe);
		assertThat(updatedContact.getEmail()).isEqualTo("jonDoe@work.com");
		assertThat(updatedContact.getPhoneNumber()).isEqualTo(newPhoneNumber("503", "541", "1234"));
		assertThat(contactRepository.exists(updatedContact.getId())).isTrue();
		assertThat(customerRepository.exists(updatedContact.getPerson().getId())).isTrue();
	}

	@Test
	public void createInvalidContactRollsbackTransaction() {
		Customer joeDirt = newCustomer("Joe", "Dirt");

		assertThat(joeDirt).isNotNull();
		assertThat(joeDirt.getName()).isEqualTo("Joe Dirt");
		assertThat(joeDirt.getAccountNumber()).isNull();
		assertThat(joeDirt.getId()).isNull();

		try {
			exception.expect(IllegalArgumentException.class);
			exception.expectCause(is(nullValue(Throwable.class)));
			exception.expectMessage("email [jd@home.biz] is invalid");

			customerService.addContactInformation(joeDirt, "jd@home.biz");
		}
		finally {
			assertThat(joeDirt.getId()).isNotNull();
			assertThat(contactRepository.findByPersonId(joeDirt.getId())).isNull();
			assertThat(contactRepository.count()).isEqualTo(0);
			assertThat(joeDirt.getAccountNumber()).isNotNull();
			assertThat(customerRepository.findByAccountNumber(joeDirt.getAccountNumber())).isNull();
			assertThat(customerRepository.count()).isEqualTo(0);
		}
	}
}
