package dbcar.main.java.com.dbshindong.dbcar.domain.company;

import java.util.Objects;

public class Employee {
	private final int employee_id;
	private final String name;
	private final String phone;
	private final String address;
	private final int salary;
	private final int dependents;
	private final String department;
	private final String role;

	private static final String NULL_MESSAGE = "%s은() null이 들어갈 수 없습니다.";

	public Employee(int employee_id, String name, String phone, String address, int salary, int dependents,
			String department, String role) {
		this.validate(employee_id, name, phone, address, salary, dependents, department, role);
		this.employee_id = employee_id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.salary = salary;
		this.dependents = dependents;
		this.department = department;
		this.role = role;
	}

	public Employee(String name, String phone, String address, int salary, int dependents, String department,
			String role) {
		this.validate(-1, name, phone, address, salary, dependents, department, role);
		this.employee_id = -1;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.salary = salary;
		this.dependents = dependents;
		this.department = department;
		this.role = role;
	}

	private void validate(int employee_id, String name, String phone, String address, int salary, int dependents,
			String department, String role) {
		Objects.requireNonNull(employee_id, String.format(NULL_MESSAGE, "employee_id"));
		Objects.requireNonNull(name, String.format(NULL_MESSAGE, "name"));
		Objects.requireNonNull(phone, String.format(NULL_MESSAGE, "phone"));
		Objects.requireNonNull(address, String.format(NULL_MESSAGE, "address"));
		Objects.requireNonNull(salary, String.format(NULL_MESSAGE, "salary"));
		Objects.requireNonNull(dependents, String.format(NULL_MESSAGE, "dependents"));
		Objects.requireNonNull(department, String.format(NULL_MESSAGE, "department"));
		Objects.requireNonNull(department, String.format(NULL_MESSAGE, "department"));
	}

	@Override
	public String toString() {
		return String.format(
				"{ \"employee_id\": %d, \"name\": \"%s\", \"phone\": \"%s\", \"address\": \"%s\", "
						+ "\"salary\": %d, \"dependents\": %d, \"department\": \"%s\", \"role\": \"%s\" }",
				employee_id, name, phone, address, salary, dependents, department, role);
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public int getSalary() {
		return salary;
	}

	public int getDependents() {
		return dependents;
	}

	public String getDepartment() {
		return department;
	}

	public String getRole() {
		return role;
	}
}
