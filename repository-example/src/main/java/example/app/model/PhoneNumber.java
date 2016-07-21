package example.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * The PhoneNumber class is an abstract data type (ADT) modeling a US phone number.
 *
 * @author John Blum
 * @see java.io.Serializable
 * @see javax.persistence.Entity
 * @see example.app.model.PhoneNumberType
 * @see <a href="https://en.wikipedia.org/wiki/Telephone_numbering_plan">Telephone Numbering Plan</a>
 * @since 1.0.0
 */
@Entity
@SuppressWarnings("unused")
public class PhoneNumber implements Serializable {

	private static final long serialVersionUID = -7966052569771224197L;

	@Id
	@javax.persistence.Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.ORDINAL)
	private PhoneNumberType type;

	@Column(name = "area_code", nullable = false)
	private String areaCode;

	private String extension;

	@Column(nullable = false)
	private String prefix;

	@Column(nullable = false)
	private String suffix;

	public static PhoneNumber newPhoneNumber(String areaCode, String prefix, String suffix) {
		Assert.hasText(areaCode, "areaCode is required");
		Assert.hasText(prefix, "prefix is required");
		Assert.hasText(suffix, "suffix is required");

		PhoneNumber phoneNumber = new PhoneNumber();

		phoneNumber.setAreaCode(areaCode);
		phoneNumber.setPrefix(prefix);
		phoneNumber.setSuffix(suffix);

		return phoneNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}

	public void setType(PhoneNumberType type) {
		this.type = type;
	}

	public PhoneNumberType getType() {
		return (type != null ? type : PhoneNumberType.HOME);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof PhoneNumber)) {
			return false;
		}

		PhoneNumber that = (PhoneNumber) obj;

		return ObjectUtils.nullSafeEquals(this.getAreaCode(), that.getAreaCode())
			&& ObjectUtils.nullSafeEquals(this.getPrefix(), that.getPrefix())
			&& ObjectUtils.nullSafeEquals(this.getSuffix(), that.getSuffix())
			&& ObjectUtils.nullSafeEquals(this.getExtension(), that.getExtension());
	}

	@Override
	public int hashCode() {
		int hashValue = 17;
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getAreaCode());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getPrefix());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getSuffix());
		hashValue = 37 * hashValue + ObjectUtils.nullSafeHashCode(this.getExtension());
		return hashValue;
	}

	@Override
	public String toString() {
		String extension = getExtension();

		return String.format("(%1$s) %2$s-%3$s%4$s [Type = %5$s]", getAreaCode(), getPrefix(), getSuffix(),
			StringUtils.hasText(extension) ? String.format(" x%s", extension) : "", getType());
	}

	public PhoneNumber with(Long id) {
		setId(id);
		return this;
	}

	public PhoneNumber with(String extension) {
		setExtension(extension);
		return this;
	}
}
