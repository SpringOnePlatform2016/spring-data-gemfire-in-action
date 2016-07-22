/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package example.app.service;

import static example.app.model.PhoneNumber.newPhoneNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import example.app.model.PhoneNumber;

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
		assertThat(customerService.validate("jonDoe@work.com")).isEqualTo("jonDoe@work.com");
		assertThat(customerService.validate("janeDoe@home.net")).isEqualTo("janeDoe@home.net");
		assertThat(customerService.validate("cookieDoe@nonprofit.org")).isEqualTo("cookieDoe@nonprofit.org");
		assertThat(customerService.validate("pieDoe@school.edu")).isEqualTo("pieDoe@school.edu");
	}

	@Test
	public void invalidEmailThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectCause(is(nullValue(Throwable.class)));
		exception.expectMessage("email [joeDirt@bar.biz] is invalid");

		customerService.validate("joeDirt@bar.biz");
	}

	@Test
	public void phoneNumberValidationIsCorrect() {
		PhoneNumber phoneNumber = newPhoneNumber("503", "541", "1234");

		assertThat(customerService.validate(phoneNumber)).isEqualTo(phoneNumber);
	}

	@Test
	public void invalidPhoneNumberThrowsIllegalArgumentException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectCause(is(nullValue(Throwable.class)));
		exception.expectMessage("'555' is not a valid phone number [(503) 555-1234 [Type = HOME]] exchange");

		customerService.validate(newPhoneNumber("503", "555", "1234"));
	}
}
