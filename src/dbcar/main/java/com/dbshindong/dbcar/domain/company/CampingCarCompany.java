package dbcar.main.java.com.dbshindong.dbcar.domain.company;

import java.util.Objects;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.exception.InvalidCampingCarCompanyException;

public class CampingCarCompany {
	private final int company_id;
	private final String name;
	private final String address;
	private final String phone;
	private final String manager_name;
	private final String manager_email;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	public CampingCarCompany(int company_id, String name, String address, String phone, String manager_name,
			String manager_email) {
		this.validate(company_id, name, address, phone, manager_name, manager_email);
		this.company_id = company_id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.manager_name = manager_name;
		this.manager_email = manager_email;
	}

	public CampingCarCompany(String name, String address, String phone, String manager_name, String manager_email) {
		this.validate(-1, name, address, phone, manager_name, manager_email);
		this.company_id = -1;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.manager_name = manager_name;
		this.manager_email = manager_email;
	}

	private void validate(int company_id, String name, String address, String phone, String manager_name,
			String manager_email) {
		try {
			Objects.requireNonNull(company_id, String.format(NULL_MESSAGE, "company_id"));
			Objects.requireNonNull(name, String.format(NULL_MESSAGE, "name"));
			Objects.requireNonNull(address, String.format(NULL_MESSAGE, "address"));
			Objects.requireNonNull(phone, String.format(NULL_MESSAGE, "phone"));
			Objects.requireNonNull(manager_name, String.format(NULL_MESSAGE, "manager_name"));
			Objects.requireNonNull(manager_email, String.format(NULL_MESSAGE, "manager_email"));
		} catch (NullPointerException e) {
			throw new InvalidCampingCarCompanyException(e.getMessage(), e);
		}

		if (!isValidEmail(manager_email)) {
			throw new InvalidCampingCarCompanyException("이메일 형식이 올바르지 않습니다.");
		}

	}

	@Override
	public String toString() {
		return String.format(
				"{ \"company_id\": %d, \"name\": \"%s\", \"address\": \"%s\", \"phone\": \"%s\", "
						+ "\"manager_name\": \"%s\", \"manager_email\": \"%s\" }",
				company_id, name, address, phone, manager_name, manager_email);
	}

	public int getCompany_id() {
		return company_id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getManager_name() {
		return manager_name;
	}

	public String getManager_email() {
		return manager_email;
	}

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@" + // local-part
			"[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // domain

	public static boolean isValidEmail(String email) {
		if (email == null || email.isBlank()) {
			return false;
		}
		return email.matches(EMAIL_REGEX);
	}

}