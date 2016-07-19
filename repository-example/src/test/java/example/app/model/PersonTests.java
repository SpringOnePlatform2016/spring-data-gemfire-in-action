package example.app.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test suite of test cases testing the contract and functionality of the {@link Person} class.
 *
 * @author John Blum
 * @see org.junit.Test
 * @see example.app.model.Person
 * @since 1.0.0
 */
public class PersonTests {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	protected LocalDate getBirthDateFor(int age) {
		return LocalDate.now().minusYears(age);
	}

	@Test
	public void newPersonWithFirstAndLastName() {
		Person jonDoe = Person.newPerson("Jon", "Doe");

		assertThat(jonDoe).isNotNull();
		assertThat(jonDoe.getId()).isNull();
		assertThat(jonDoe.getBirthDate()).isNull();
		assertThat(jonDoe.getFirstName()).isEqualTo("Jon");
		assertThat(jonDoe.getGender()).isNull();
		assertThat(jonDoe.getLastName()).isEqualTo("Doe");
		assertThat(jonDoe.getName()).isEqualTo("Jon Doe");
	}

	@Test
	public void newPersonWithNameBirthDateGenderAndId() {
		LocalDate birthDate = LocalDate.of(1969, Month.APRIL, 1);

		Person janeDoe = Person.newPerson("Jane", "Doe").as(Gender.FEMALE).born(birthDate).with(1L);

		assertThat(janeDoe).isNotNull();
		assertThat(janeDoe.getId()).isEqualTo(1L);
		assertThat(janeDoe.getBirthDate()).isEqualTo(birthDate);
		assertThat(janeDoe.getGender()).isEqualTo(Gender.FEMALE);
		assertThat(janeDoe.getName()).isEqualTo("Jane Doe");
	}

	@Test
	public void getAge() {
		Person jonDoe = Person.newPerson("Jon", "Doe").born(getBirthDateFor(21));

		assertThat(jonDoe).isNotNull();
		assertThat(jonDoe.getName()).isEqualTo("Jon Doe");
		assertThat(jonDoe.getBirthDate()).isNotNull();
		assertThat(jonDoe.getAge()).isEqualTo(21);
	}

	@Test
	public void getAgeWhenBirthDateIsNullThrowsIllegalStateException() {
		Person jonDoe = Person.newPerson("Jon", "Doe");

		assertThat(jonDoe).isNotNull();
		assertThat(jonDoe.getName()).isEqualTo("Jon Doe");
		assertThat(jonDoe.getBirthDate()).isNull();

		exception.expect(IllegalStateException.class);
		exception.expectCause(is(nullValue(Throwable.class)));
		exception.expectMessage("the birth date of person [Jon Doe] is unknown");

		jonDoe.getAge();
	}

	@Test
	public void setBirthDateToFutureThrowsIllegalArgumentException() {
		Person jonDoe = Person.newPerson("Jon", "Doe");

		assertThat(jonDoe).isNotNull();
		assertThat(jonDoe.getName()).isEqualTo("Jon Doe");
		assertThat(jonDoe.getBirthDate()).isNull();

		try {
			exception.expect(IllegalArgumentException.class);
			exception.expectCause(is(nullValue(Throwable.class)));
			exception.expectMessage(String.format("[Jon Doe] cannot be born after today [%s]",
				jonDoe.toString(LocalDate.now())));

			jonDoe.setBirthDate(getBirthDateFor(-10));
		}
		finally {
			assertThat(jonDoe.getBirthDate()).isNull();
		}
	}

	@Test
	public void setBirthDateToNullIsAllowed() {
		LocalDate birthDate = getBirthDateFor(42);

		Person jonDoe = Person.newPerson("Jon", "Doe").born(birthDate);

		assertThat(jonDoe).isNotNull();
		assertThat(jonDoe.getName()).isEqualTo("Jon Doe");
		assertThat(jonDoe.getBirthDate()).isEqualTo(birthDate);

		jonDoe.setBirthDate(null);

		assertThat(jonDoe.getBirthDate()).isNull();
	}
}
