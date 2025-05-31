package dbcar.main.java.com.dbshindong.dbcar.domain.customer;

import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.common.Validator;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.exception.InvalidCustomerException;

public class Customer {
	private final Integer customer_id;
	private final String username;
	private final String password;
	private final String license_number;
	private final String name;
	private final String address;
	private final String phone;
	private final String email;

	private static final String NULL_MESSAGE = "%s은() null이 들어갈 수 없습니다.";

	private static final String CUSTOMER_ID = "고객 ID";
	private static final String USERNAME = "아이디";
	private static final String PASSWORD = "비밀번호";
	private static final String LICENSE_NUMBER = "운전면허증 번호";
	private static final String NAME = "고객명";
	private static final String ADDRESS = "주소";
	private static final String PHONE = "전화번호";
	private static final String EMAIL = "이메일";

	public Customer(Integer customer_id, String username, String password, String license_number, String name,
			String address, String phone, String email) {
		validate(customer_id, username, password, license_number, name, address, phone, email);

		this.customer_id = customer_id;
		this.username = username;
		this.password = password;
		this.license_number = license_number;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	public Customer(String username, String password, String license_number, String name, String address, String phone,
			String email) {
		validate(-1, username, password, license_number, name, address, phone, email);

		this.customer_id = -1;
		this.username = username;
		this.password = password;
		this.license_number = license_number;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	private void validate(Integer customer_id, String username, String password, String license_number, String name,
			String address, String phone, String email) {
		try {
			Objects.requireNonNull(customer_id, String.format(NULL_MESSAGE, CUSTOMER_ID));
			Validator.requireNonBlank(username, String.format(NULL_MESSAGE, USERNAME));
			Validator.requireNonBlank(password, String.format(NULL_MESSAGE, PASSWORD));
			Validator.requireNonBlank(license_number, String.format(NULL_MESSAGE, LICENSE_NUMBER));
			Validator.requireNonBlank(name, String.format(NULL_MESSAGE, NAME));
			Validator.requireNonBlank(address, String.format(NULL_MESSAGE, ADDRESS));
			Validator.requireNonBlank(phone, String.format(NULL_MESSAGE, PHONE));
			Validator.requireNonBlank(email, String.format(NULL_MESSAGE, EMAIL));
		} catch (NullPointerException e) {
			throw new InvalidCustomerException(e.getMessage(), e);
		}

		if (!Validator.isValidEmail(email)) {
			throw new InvalidCustomerException(EMAIL + "의 입력이 잘못되었습니다.");
		}
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getLicense_number() {
		return license_number;
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

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return String.format(
				"{ \"username\": \"%s\", \"password\": \"%s\", \"license_number\": \"%s\", "
						+ "\"name\": \"%s\", \"address\": \"%s\", \"phone\": \"%s\", \"email\": \"%s\" }",
				username, password, license_number, name, address, phone, email);
	}

}
