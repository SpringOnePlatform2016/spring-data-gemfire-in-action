package example.app.repo.gemfire;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.query.annotation.Hint;
import org.springframework.data.gemfire.repository.query.annotation.Limit;
import org.springframework.data.gemfire.repository.query.annotation.Trace;

import example.app.model.Contact;
import example.app.model.Gender;
import example.app.model.PhoneNumber;
import example.app.model.State;

/**
 * Spring Data {@link GemfireRepository} for performing basic data access, CRUD and querying operations on
 * {@link Contact} objects stored and managed in Apache Geode (or Pivotal GemFire).
 *
 * @author John Blum
 * @see example.app.model.Contact
 * @see org.springframework.data.gemfire.repository.GemfireRepository
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface ContactRepository extends GemfireRepository<Contact, Long> {

	@Trace
	List<Contact> findByAddressCityAndAddressState(String city, State state);

	@Trace
	@Hint("EmailIdx")
	Contact findByEmail(String email);

	@Trace
	List<Contact> findByEmailLike(String emailWildcard);

	@Trace
	List<Contact> findByPersonAgeGreaterThanEqualOrderByPersonLastNameAscPersonAgeDesc(int age);

	@Trace
	List<Contact> findByPersonGender(Gender gender);

	@Trace
	//@Query("SELECT * FROM /Contacts c WHERE c.person.firstName.equalsIgnoreCase($1) AND c.person.lastName.equalsIgnoreCase($2)")
	//@Query("SELECT * FROM /Contacts c WHERE c.person.firstName.toLowerCase LIKE $1 AND c.person.lastName.toLowerCase LIKE $2")
	List<Contact> findByPersonFirstNameIgnoreCaseAndPersonLastNameIgnoreCase(String firstName, String lastName);

	@Trace
	Contact findByPersonId(Long id);

	@Trace
	@Hint("PersonLastNameIdx")
	List<Contact> findByPersonLastName(String lastName);

	@Trace
	@Limit(5)
	List<Contact> findByPersonLastNameLike(String lastNameWildcard, Sort sort);

	@Trace
	List<Contact> findByPhoneNumber(PhoneNumber phoneNumber);

}
