package example.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * The Address class is an abstract data type (ADT) that models a US address location.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see javax.persistence.Entity
 * @see org.springframework.data.geo.Point
 * @see example.app.model.AddressType
 * @since 1.0.0
 */
@Entity
@SuppressWarnings("unused")
public class Address implements Serializable {

	private static final long serialVersionUID = -1775411208922748140L;

	private AddressType type;

	private Long id;

	private Point location;

	private State state;
	private String city;
	private String street;
	private String zipCode;

	public static Address newAddress(Point location) {
		Assert.notNull(location, "location is required");

		Address address = new Address();

		address.setLocation(location);

		return address;
	}

	public static Address newAddress(String street, String city, State state, String zipCode) {
		Assert.hasText(street, "street is required");
		Assert.hasText(city, "city is required");
		Assert.notNull(state, "state is required");
		Assert.hasText(zipCode, "zipCode is required");

		Address address = new Address();

		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setZipCode(zipCode);

		return address;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@javax.persistence.Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Point getLocation() {
		return location;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(nullable = false)
	public String getStreet() {
		return street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(nullable = false)
	public String getCity() {
		return city;
	}

	public void setState(State state) {
		this.state = state;
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public State getState() {
		return state;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(nullable = false)
	public String getZipCode() {
		return zipCode;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

	@Enumerated(EnumType.STRING)
	public AddressType getType() {
		return (type != null ? type : AddressType.HOME);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Address)) {
			return false;
		}

		Address that = (Address) obj;

		return ObjectUtils.nullSafeEquals(this.getLocation(), that.getLocation())
			&& ObjectUtils.nullSafeEquals(this.getStreet(), that.getStreet())
			&& ObjectUtils.nullSafeEquals(this.getCity(), that.getCity())
			&& ObjectUtils.nullSafeEquals(this.getState(), that.getState())
			&& ObjectUtils.nullSafeEquals(this.getZipCode(), that.getZipCode());
	}

	@Override
	public int hashCode() {
		int hashValue = 17;
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getLocation());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getStreet());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getCity());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getState());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getZipCode());
		return hashValue;
	}

	@Override
	public String toString() {
		return String.format("%1$s %2$s, %3$s %4$s [Location = %5$s, Type = %6$s]",
			getStreet(), getCity(), getState(), getZipCode(), getLocation(), getType());
	}

	public Address with(Long id) {
		setId(id);
		return this;
	}
}
