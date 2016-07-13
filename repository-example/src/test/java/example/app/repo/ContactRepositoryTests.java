package example.app.repo;

import static example.app.model.Address.newAddress;
import static example.app.model.Contact.newContact;
import static example.app.model.Person.newPerson;
import static example.app.model.PhoneNumber.newPhoneNumber;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import com.gemstone.gemfire.cache.Region;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import example.app.config.ApplicationConfiguration;
import example.app.model.Contact;
import example.app.model.Gender;
import example.app.model.State;

/**
 * Test suite of test cases testing the contract and functionality of the {@link ContactRepository} DAO interface.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see example.app.repo.ContactRepository
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@SuppressWarnings("unused")
public class ContactRepositoryTests {

	protected static final AtomicLong ID_GENERATOR = new AtomicLong(0L);

	@Autowired
	private ContactRepository contactRepository;

	@Resource(name = "Contacts")
	private Region<Long, Contact> contacts;

	protected Long generateId() {
		return ID_GENERATOR.incrementAndGet();
	}

	protected Sort newSort(String... properties) {
		return new Sort(properties);
	}

	protected Contact save(Contact contact) {
		if (contact.getId() == null) {
			contact.setId(generateId());
		}

		return contactRepository.save(contact);
	}

	@After
	public void tearDown() {
		contacts.removeAll(contacts.keySet());
	}

	@Test
	public void saveFindAndDeleteIsSuccessful() {
		Contact savedJonDoe = newContact(newPerson("Jon", "Doe"), "jonDoe@home.com")
			.with(newAddress("100 Main St.", "Portland", State.OREGON, "12345"))
			.with(newPhoneNumber("503", "555", "1234"))
			.with(generateId());

		contactRepository.save(savedJonDoe);

		Contact loadedJonDoe = contactRepository.findOne(savedJonDoe.getId());

		assertThat(loadedJonDoe).isEqualTo(savedJonDoe);

		contactRepository.delete(loadedJonDoe);

		assertThat(contactRepository.count()).isEqualTo(0);
	}

	@Test
	public void findByEmailFindsExpectedContact() {
		Contact jonDoe = save(newContact(newPerson("Jon","Doe"), "jonDoe@gmail.com"));
		Contact jackHandy = save(newContact(newPerson("Jack", "Handy"), "jackHandy@yahoo.com"));

		Contact jonDoeByEmail = contactRepository.findByEmail(jonDoe.getEmail());

		assertThat(jonDoeByEmail).isEqualTo(jonDoe);

		Contact jackHandyByEmail = contactRepository.findByEmail(jackHandy.getEmail());

		assertThat(jackHandyByEmail).isEqualTo(jackHandy);
	}

	@Test
	public void findByEmailLikeFindsExpectedContacts() {
		Contact jonDoe = save(newContact(newPerson("Jon", "Doe"), "jonDoe@gmail.com"));
		Contact janeDoe = save(newContact(newPerson("Jane", "Doe"), "janeDoe@yahoo.com"));
		Contact jackHandy = save(newContact(newPerson("Jack", "Handy"), "jackHandy@gmail.com"));

		List<Contact> gmailEmailAccounts = contactRepository.findByEmailLike("%@gmail.com");

		assertThat(gmailEmailAccounts).isNotNull();
		assertThat(gmailEmailAccounts.size()).isEqualTo(2);
		assertThat(gmailEmailAccounts).containsAll(Arrays.asList(jonDoe, jackHandy));

		List<Contact> yahooEmailAccounts = contactRepository.findByEmailLike("%@yahoo.com");

		assertThat(yahooEmailAccounts).isNotNull();
		assertThat(yahooEmailAccounts.size()).isEqualTo(1);
		assertThat(yahooEmailAccounts).containsAll(Collections.singletonList(janeDoe));

		List<Contact> noAolEmailAccounts = contactRepository.findByEmailLike("%@aol.com");

		assertThat(noAolEmailAccounts).isNotNull();
		assertThat(noAolEmailAccounts).isEmpty();
	}

	@Test
	public void findByAddressCityAndState() {
		Contact joeDirt = save(newContact(newPerson("Joe", "Dirt"), newAddress("100 Main St.", "Eugene", State.OREGON, "54321")));
		Contact jonDoe = save(newContact(newPerson("Jon", "Doe"), newAddress("100 Main St.", "Portland", State.OREGON, "97205")));
		Contact benDover = save(newContact(newPerson("Ben", "Dover"), newAddress("100 Main St.", "San Francisco", State.CALIFORNIA, "9876")));
		Contact jackHandy = save(newContact(newPerson("Jack", "Handy"), newAddress("100 Main St.", "Portland", State.MAINE, "12345")));

		List<Contact> contacts = contactRepository.findByAddressCityAndAddressState("Portland", State.OREGON);

		assertThat(contacts).isNotNull();
		assertThat(contacts.size()).isEqualTo(1);
		assertThat(contacts).containsAll(Collections.singletonList(jonDoe));

		contacts = contactRepository.findByAddressCityAndAddressState("Portland", State.MAINE);

		assertThat(contacts).isNotNull();
		assertThat(contacts.size()).isEqualTo(1);
		assertThat(contacts).containsAll(Collections.singletonList(jackHandy));

		contacts = contactRepository.findByAddressCityAndAddressState("Eugene", State.OREGON);

		assertThat(contacts).isNotNull();
		assertThat(contacts.size()).isEqualTo(1);
		assertThat(contacts).containsAll(Collections.singletonList(joeDirt));

		contacts = contactRepository.findByAddressCityAndAddressState("San Francisco", State.CALIFORNIA);

		assertThat(contacts).isNotNull();
		assertThat(contacts.size()).isEqualTo(1);
		assertThat(contacts).containsAll(Collections.singletonList(benDover));

		contacts = contactRepository.findByAddressCityAndAddressState("Hollywood", State.CALIFORNIA);

		assertThat(contacts).isNotNull();
		assertThat(contacts).isEmpty();

		contacts = contactRepository.findByAddressCityAndAddressState("Hollywood", State.NORTH_DAKOTA);

		assertThat(contacts).isNotNull();
		assertThat(contacts).isEmpty();
	}

	@Test
	public void findByPersonGenderFindsExpectedContacts() {
		Contact jonDoe = save(newContact(newPerson("Jon", "Doe").as(Gender.MALE), "jonDoe@office.com"));
		Contact janeDoe = save(newContact(newPerson("Jane", "Doe").as(Gender.FEMALE), "janeDoe@home.com"));
		Contact pieDoe = save(newContact(newPerson("Pie", "Doe").as(Gender.FEMALE), "pieDoe@school.com"));

		List<Contact> femaleContacts = contactRepository.findByPersonGender(Gender.FEMALE);

		assertThat(femaleContacts).isNotNull();
		assertThat(femaleContacts.size()).isEqualTo(2);
		assertThat(femaleContacts).containsAll(Arrays.asList(janeDoe, pieDoe));

		List<Contact> maleContacts = contactRepository.findByPersonGender(Gender.MALE);

		assertThat(maleContacts).isNotNull();
		assertThat(maleContacts.size()).isEqualTo(1);
		assertThat(maleContacts).containsAll(Collections.singletonList(jonDoe));
	}

	@Test
	public void findByPersonLastNameFindsExpectedContacts() {
		Contact jonDoe = save(newContact(newPerson("Jon", "Doe"), "jonDoe@home.com"));
		Contact janeDoe = save(newContact(newPerson("Jane", "Doe"), "janeDoe@home.com"));
		Contact jackHandy = save(newContact(newPerson("Jack", "Handy"), "jackHandy@home.com"));

		List<Contact> doeContacts = contactRepository.findByPersonLastName("Doe");

		assertThat(doeContacts).isNotNull();
		assertThat(doeContacts.size()).isEqualTo(2);
		assertThat(doeContacts).containsAll(Arrays.asList(jonDoe, janeDoe));

		List<Contact> handyContacts = contactRepository.findByPersonLastName("Handy");

		assertThat(handyContacts).isNotNull();
		assertThat(handyContacts.size()).isEqualTo(1);
		assertThat(handyContacts).containsAll(Collections.singletonList(jackHandy));

		List<Contact> noContacts = contactRepository.findByPersonLastName("Smith");

		assertThat(noContacts).isNotNull();
		assertThat(noContacts).isEmpty();
	}

	@Test
	public void findByPersonLastNameLikeFindsExpectedContacts() {
		Contact jonDoe = save(newContact(newPerson("Jon", "Doe"), "jonDoe@home.com"));
		Contact joeDirt = save(newContact(newPerson("Joe", "Dirt"), "joeDirt@office.com"));
		Contact jackHandy = save(newContact(newPerson("Jack", "Handy"), "jackHandy@home.com"));

		List<Contact> jonAndJoeContacts = contactRepository.findByPersonLastNameLike("D%", newSort("person.lastName"));

		assertThat(jonAndJoeContacts).isNotNull();
		assertThat(jonAndJoeContacts.size()).isEqualTo(2);
		assertThat(jonAndJoeContacts).isEqualTo(Arrays.asList(joeDirt, jonDoe));

		List<Contact> jackHandyContact = contactRepository.findByPersonLastNameLike("%and%", newSort("person.lastName"));

		assertThat(jackHandyContact).isNotNull();
		assertThat(jackHandyContact.size()).isEqualTo(1);
		assertThat(jackHandyContact).containsAll(Collections.singletonList(jackHandy));

		List<Contact> noContacts = contactRepository.findByPersonLastNameLike("Smi%", newSort("person.lastName"));

		assertThat(noContacts).isNotNull();
		assertThat(noContacts).isEmpty();
	}

	@Test
	public void findByLastNameLikeLimitedToFiveResultsFindsExpectedContacts() {
		Contact jonDoe = save(newContact(newPerson("Jon", "Doe"), "jonDoe@home.com"));
		Contact janeDoe = save(newContact(newPerson("Jane", "Doe"), "janeDoe@home.com"));
		Contact cookieDoe = save(newContact(newPerson("Cookie", "Doe"), "cookieDoe@home.com"));
		Contact pieDoe = save(newContact(newPerson("Pie", "Doe"), "pieDoe@home.com"));
		Contact sourDoe = save(newContact(newPerson("Sour", "Doe"), "sourDoe@home.com"));
		Contact moeDoe = save(newContact(newPerson("Moe", "Doe"), "moeDoe@home.com"));
		Contact joeDoe = save(newContact(newPerson("Joe", "Doe"), "joeDoe@home.com"));
		Contact hoeDoe = save(newContact(newPerson("Hoe", "Doe"), "hoeDoe@home.com"));
		Contact froDoe = save(newContact(newPerson("Fro", "Doe"), "froDoe@home.com"));

		List<Contact> expectedContacts = Arrays.asList(cookieDoe, froDoe, hoeDoe, janeDoe, joeDoe);

		List<Contact> doeContacts = contactRepository.findByPersonLastNameLike("Doe%", newSort("person.firstName"));

		assertThat(doeContacts).isNotNull();
		assertThat(doeContacts.size()).isEqualTo(5);
		assertThat(doeContacts).describedAs("Expected [%1$s];%n	 but was [%2$s]", expectedContacts, doeContacts)
			.isEqualTo(expectedContacts);
	}

	@Test
	public void findByPhoneNumberFindExpectedContact() {
		Contact jonDoe = save(newContact(newPerson("Jon", "Doe"), newPhoneNumber("503", "555", "1234")));
		Contact janeDoe = save(newContact(newPerson("Jane", "Doe"), newPhoneNumber("503", "555", "1234")));
		Contact jackHandy = save(newContact(newPerson("Jack", "Handy"), newPhoneNumber("971", "555", "1234")));

		List<Contact> doeContacts = contactRepository.findByPhoneNumber(newPhoneNumber("503", "555", "1234"));

		assertThat(doeContacts).isNotNull();
		assertThat(doeContacts.size()).isEqualTo(2);
		assertThat(doeContacts).containsAll(Arrays.asList(jonDoe, janeDoe));

		List<Contact> handyContacts = contactRepository.findByPhoneNumber(newPhoneNumber("971", "555", "1234"));

		assertThat(handyContacts).isNotNull();
		assertThat(handyContacts.size()).isEqualTo(1);
		assertThat(handyContacts).containsAll(Collections.singletonList(jackHandy));

		List<Contact> noContacts = contactRepository.findByPhoneNumber(newPhoneNumber("503", "555", "9876"));

		assertThat(noContacts).isNotNull();
		assertThat(noContacts).isEmpty();
	}
}
