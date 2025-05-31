package dbcar.main.java.com.dbshindong.dbcar.domain.company;

import java.util.Objects;

import dbcar.main.java.com.dbshindong.dbcar.common.Validator;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.exception.InvalidEmployeeException;

public class Employee {
	private final Integer employee_id;
	private final String name;
	private final String phone;
	private final String address;
	private final Integer salary;
	private final Integer dependents;
	private final String department;
	private final String role;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	private final static String EMPLOYEE_ID = "직원 ID";
	private final static String NAME = "직원명";
	private final static String PHONE = "전화번호";
	private final static String ADDRESS = "주소";
	private final static String SALARY = "월급여";
	private final static String DEPENDENTS = "부양 가족 수";
	private final static String DEPARTMENT = "담당부서";
	private final static String ROLE = "담당 업무";

	public Employee(Integer employee_id, String name, String phone, String address, Integer salary, Integer dependents,
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

	public Employee(String name, String phone, String address, Integer salary, Integer dependents, String department,
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

	private void validate(Integer employee_id, String name, String phone, String address, Integer salary,
			Integer dependents, String department, String role) {
		try {
			Objects.requireNonNull(employee_id, String.format(NULL_MESSAGE, EMPLOYEE_ID));
			Validator.requireNonBlank(name, String.format(NULL_MESSAGE, NAME));
			Validator.requireNonBlank(phone, String.format(NULL_MESSAGE, PHONE));
			Validator.requireNonBlank(address, String.format(NULL_MESSAGE, ADDRESS));
			Objects.requireNonNull(salary, String.format(NULL_MESSAGE, SALARY));
			Objects.requireNonNull(dependents, String.format(NULL_MESSAGE, DEPENDENTS));
			Validator.requireNonBlank(department, String.format(NULL_MESSAGE, DEPARTMENT));
			Validator.requireNonBlank(role, String.format(NULL_MESSAGE, ROLE));
		} catch (NullPointerException e) {
			throw new InvalidEmployeeException(e.getMessage(), e);
		}

		if (salary < 0) {
			throw new InvalidEmployeeException(SALARY + "의 입력값이 올바르지 않습니다.");
		}

		if (dependents < 0) {
			throw new InvalidEmployeeException(DEPENDENTS + "의 입력값이 올바르지 않습니다");
		}

		// role은 관리, 사무, 정비 중 하나여야함
		if (!(role.equals("사무") || role.equals("관리") || role.equals("정비"))) {
			throw new InvalidEmployeeException(DEPARTMENT + "는 관리, 사무, 정비 중 하나여야합니다.");
		}

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
