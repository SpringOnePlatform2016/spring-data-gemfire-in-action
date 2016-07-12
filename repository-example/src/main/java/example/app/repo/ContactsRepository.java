package example.app.repo;

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
 * ContactsRepository is a data access object for performing CRUD and querying operations on {@link Contact}s.
 *
 * @author John Blum
 * @see example.app.model.Contact
 * @see org.springframework.data.gemfire.repository.GemfireRepository
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface ContactsRepository extends GemfireRepository<Contact, Long> {

	@Trace
	List<Contact> findByAddressCityAndAddressState(String city, State state);

	@Trace
	@Hint("EmailIdx")
	Contact findByEmail(String email);

	@Trace
	List<Contact> findByEmailLike(String emailWildcard);

	@Trace
	List<Contact> findByPersonGender(Gender gender);

	@Trace
	@Hint("PersonLastNameIdx")
	List<Contact> findByPersonLastName(String lastName);

	@Trace
	@Limit(5)
	List<Contact> findByPersonLastNameLike(String lastNameWildcard, Sort sort);

	@Trace
	List<Contact> findByPhoneNumber(PhoneNumber phoneNumber);

}
