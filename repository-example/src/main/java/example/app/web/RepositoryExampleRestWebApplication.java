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

package example.app.web;

import static example.app.model.Address.newAddress;
import static example.app.model.Contact.newContact;
import static example.app.model.Person.newPerson;
import static example.app.model.PhoneNumber.newPhoneNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import example.app.config.ApplicationConfiguration;
import example.app.model.Contact;
import example.app.model.Gender;
import example.app.model.State;
import example.app.repo.gemfire.ContactRepository;

/**
 * The RepositoryExampleRestWebApplication class is a {@link SpringBootApplication} demonstrating how to make
 * the Repository example application {@link ContactRepository} interface REST-ful using Spring Data REST.
 *
 * @author John Blum
 * @see org.springframework.boot.CommandLineRunner
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see example.app.config.ApplicationConfiguration
 * @see example.app.model.Contact
 * @see example.app.repo.gemfire.ContactRepository
 * @link http://projects.spring.io/spring-data-gemfire
 * @link http://projects.spring.io/spring-data-rest
 * @link https://spring.io/guides/gs/accessing-gemfire-data-rest/
 * @since 1.0.0
 */
@SpringBootApplication
@Import(ApplicationConfiguration.class)
@SuppressWarnings("unused")
public class RepositoryExampleRestWebApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RepositoryExampleRestWebApplication.class, args);
	}

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public void run(String... args) throws Exception {
		Contact jonDoe = newContact(newPerson("Jon", "Doe").as(Gender.MALE).age(21), "jonDoe@work.com")
			.with(newAddress("100 Main St.", "Portland", State.OREGON, "97205"))
			.with(newPhoneNumber("503", "541", "1234"))
			.with(1L);

		contactRepository.save(jonDoe);
	}
}
