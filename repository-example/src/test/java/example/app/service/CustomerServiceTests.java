package example.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test suite of test cases testing the contract and functionality of the {@link CustomerService} class.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see example.app.service.CustomerService
 * @since 1.0.0
 */
public class CustomerServiceTests {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private CustomerService customerService = new CustomerService();

	@Test
	public void emailValidationIsCorrect() {
		assertThat(customerService.validateEmail("jonDoe@work.com")).isEqualTo("jonDoe@work.com");
		assertThat(customerService.validateEmail("janeDoe@home.net")).isEqualTo("janeDoe@home.net");
		assertThat(customerService.validateEmail("cookieDoe@nonprofit.org")).isEqualTo("cookieDoe@nonprofit.org");
		assertThat(customerService.validateEmail("pieDoe@school.edu")).isEqualTo("pieDoe@school.edu");
	}

	@Test
	public void invalidEmailThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectCause(is(nullValue(Throwable.class)));
		exception.expectMessage("email [joeDirt@bar.biz] is invalid");

		customerService.validateEmail("joeDirt@bar.biz");
	}
}
