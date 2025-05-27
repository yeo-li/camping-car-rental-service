package dbcar.main.java.com.dbshindong.dbcar.application.dto;

public record UpdateEmployeeRequest(String name, String phone, String address, Integer salary, Integer dependents,
		String department, String role) {

}
