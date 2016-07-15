package example.app.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * The Contact class...
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see org.springframework.data.annotation.Id
 * @see org.springframework.data.gemfire.mapping.Region
 * @since 1.0.0
 */
@Region("Contacts")
@SuppressWarnings("unused")
public class Contact implements Serializable {

	private static final long serialVersionUID = 6434575088917742861L;

	@Id
	private Long id;

	private Address address;

	private Person person;

	private PhoneNumber phoneNumber;

	private String email;

	public static Contact newContact(Person person, Address address) {
		Assert.notNull(person, "Person is required");
		Assert.notNull(address, "Address is required");

		Contact contact = new Contact();

		contact.setPerson(person);
		contact.setAddress(address);

		return contact;
	}

	public static Contact newContact(Person person, String email) {
		Assert.notNull(person, "Person is required");
		Assert.hasText(email, "Email is required");

		Contact contact = new Contact();

		contact.setPerson(person);
		contact.setEmail(email);

		return contact;
	}

	public static Contact newContact(Person person, PhoneNumber phoneNumber) {
		Assert.notNull(person, "Person is required");
		Assert.notNull(phoneNumber, "PhoneNumber is required");

		Contact contact = new Contact();

		contact.setPerson(person);
		contact.setPhoneNumber(phoneNumber);

		return contact;
	}

	public static Contact newContact(Person person, Address address, PhoneNumber phoneNumber) {
		Assert.notNull(person, "Person is required");
		Assert.notNull(address, "Address is required");
		Assert.notNull(phoneNumber, "PhoneNumber is required");

		Contact contact = new Contact();

		contact.setPerson(person);
		contact.setAddress(address);
		contact.setPhoneNumber(phoneNumber);

		return contact;
	}

	public static Contact newContact(Person person, String email, PhoneNumber phoneNumber) {
		Assert.notNull(person, "Person is required");
		Assert.hasText(email, "Email is required");
		Assert.notNull(phoneNumber, "PhoneNumber is required");

		Contact contact = new Contact();

		contact.setPerson(person);
		contact.setEmail(email);
		contact.setPhoneNumber(phoneNumber);

		return contact;
	}

	public static Contact newContact(Person person, Address address, String email, PhoneNumber phoneNumber) {
		Assert.notNull(person, "Person is required");
		Assert.notNull(address, "Address is required");
		Assert.hasText(email, "Email is required");
		Assert.notNull(phoneNumber, "PhoneNumber is required");

		Contact contact = new Contact();

		contact.setPerson(person);
		contact.setAddress(address);
		contact.setEmail(email);
		contact.setPhoneNumber(phoneNumber);

		return contact;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	protected final void setPerson(Person person) {
		Assert.notNull(person, "Person cannot be null");
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Contact)) {
			return false;
		}

		Contact that = (Contact) obj;

		return ObjectUtils.nullSafeEquals(this.getPerson(), that.getPerson())
			&& ObjectUtils.nullSafeEquals(this.getAddress(), that.getAddress())
			&& ObjectUtils.nullSafeEquals(this.getEmail(), that.getEmail())
			&& ObjectUtils.nullSafeEquals(this.getPhoneNumber(), that.getPhoneNumber());
	}

	@Override
	public int hashCode() {
		int hashValue = 17;
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getPerson());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getAddress());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getEmail());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getPhoneNumber());
		return hashValue;
	}

	@Override
	public String toString() {
		return String.format("{ @type = %1$s, person = %2$s, address = %3$s, phoneNumber = %4$s, email = %5$s }",
			getClass().getName(), getPerson(), getAddress(), getPhoneNumber(), getEmail());
	}

	public Contact with(Long id) {
		setId(id);
		return this;
	}

	public Contact with(Address address) {
		setAddress(address);
		return this;
	}

	public Contact with(String email) {
		setEmail(email);
		return this;
	}

	public Contact with(PhoneNumber phoneNumber) {
		setPhoneNumber(phoneNumber);
		return this;
	}
}
