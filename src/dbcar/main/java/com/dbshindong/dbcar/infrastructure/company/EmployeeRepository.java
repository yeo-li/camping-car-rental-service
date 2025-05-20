package dbcar.main.java.com.dbshindong.dbcar.infrastructure.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.Employee;

public class EmployeeRepository {
	private final Connection conn;

	public EmployeeRepository(Connection conn) {
		this.conn = conn;
	}

	public Employee findById(int id) {

		Employee employee = null;
		try {
			String sql = "SELECT * FROM Employee where employee_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int employee_id = rs.getInt("employee_id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				int salary = rs.getInt("salary");
				int dependents = rs.getInt("dependents");
				String department = rs.getString("department");
				String role = rs.getString("role");

				employee = new Employee(employee_id, name, phone, address, salary, dependents, department, role);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}

		return employee;

	}

	public List<Employee> findAll() {
		List<Employee> employees = new ArrayList<>();

		try {
			String sql = "SELECT * FROM Employee";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int employee_id = rs.getInt("employee_id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				int salary = rs.getInt("salary");
				int dependents = rs.getInt("dependents");
				String department = rs.getString("department");
				String role = rs.getString("role");

				Employee employee = new Employee(employee_id, name, phone, address, salary, dependents, department,
						role);
				employees.add(employee);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return employees;
	}

	public void delete(int id) {
		String sql = "DELETE FROM Employee WHERE employee_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			int deleted = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void save(Employee employee) {
		String sql = "INSERT INTO Employee (name, phone, address, salary, dependents, department, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, employee.getName());
			pstmt.setString(2, employee.getPhone());
			pstmt.setString(3, employee.getAddress());
			pstmt.setInt(4, employee.getSalary());
			pstmt.setInt(5, employee.getDependents());
			pstmt.setString(6, employee.getDepartment());
			pstmt.setString(7, employee.getRole());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(int id, Employee employee) {
		String sql = "UPDATE Employee SET username = ?, password = ?, license_number = ?, name = ?, address = ?, phone = ?, email = ? WHERE employee_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, employee.getName());
			pstmt.setString(2, employee.getPhone());
			pstmt.setString(3, employee.getAddress());
			pstmt.setInt(4, employee.getSalary());
			pstmt.setInt(5, employee.getDependents());
			pstmt.setString(6, employee.getDepartment());
			pstmt.setString(7, employee.getRole());
			pstmt.setInt(8, id);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
