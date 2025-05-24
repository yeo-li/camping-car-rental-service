package dbcar.main.java.com.dbshindong.dbcar.application.dto;

public record UpdateCustomerRequest(String username, String password, String license_number, String name,
		String address, String phone, String email) {

}
