package dbcar.main.java.com.dbshindong.dbcar.domain.customer;

import java.util.*;

public class Customer {
	private final String username;
	private final String password;
	private final String license_number;
	private final String name;
	private final String address;
	private final String phone;
	private final String email;
	
	private static final String NULL_MESSAGE = "%s은() null이 들어갈 수 없습니다.";
	
	public Customer(String username, String password, String license_number, String name, String address, String phone, String email) {
		validate(username, password, license_number, name, address, phone, email);
		
		this.username = username;
		this.password = password;
		this.license_number = license_number;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;	
	}
	
	private void validate(String username, String password, String license_number, String name, String address, String phone, String emial) {
	    Objects.requireNonNull(username, String.format(NULL_MESSAGE, "username"));
	    Objects.requireNonNull(password, String.format(NULL_MESSAGE, "password"));
	    Objects.requireNonNull(license_number, String.format(NULL_MESSAGE, "license_number"));
	    Objects.requireNonNull(name, String.format(NULL_MESSAGE, "name"));
	    Objects.requireNonNull(address, String.format(NULL_MESSAGE, "address"));
	    Objects.requireNonNull(phone, String.format(NULL_MESSAGE, "phone"));
	    Objects.requireNonNull(email, String.format(NULL_MESSAGE, "email"));
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
	
	
}
