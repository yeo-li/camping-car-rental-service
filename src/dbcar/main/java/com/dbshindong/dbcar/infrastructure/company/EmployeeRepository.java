package dbcar.main.java.com.dbshindong.dbcar.infrastructure.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataDeleteException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataInsertException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataNotFoundException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataUpdateException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.Employee;

public class EmployeeRepository {
	private final Connection conn;

	public EmployeeRepository(Connection conn) {
		this.conn = conn;
	}

	public Employee findById(int id) {
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

				return new Employee(employee_id, name, phone, address, salary, dependents, department, role);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		throw new DataNotFoundException("조회된 데이터가 없습니다.");

	}

	public List<Employee> findByCondition(String condition) {
		List<Employee> employees = new ArrayList<>();

		try {
			String sql = "SELECT * FROM Employee WHERE " + condition;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int employeeId = rs.getInt("employee_id");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				int salary = rs.getInt("salary");
				int dependents = rs.getInt("dependents");
				String department = rs.getString("department");
				String role = rs.getString("role");

				Employee emp = new Employee(employeeId, name, phone, address, salary, dependents, department, role);
				employees.add(emp);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return employees;
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
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return employees;
	}

	public void delete(int id) {
		String sql = "DELETE FROM Employee WHERE employee_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataDeleteException("삭제 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataDeleteException("데이터 삭제 중 오류가 발생했습니다.", e);
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
			throw new DataInsertException("데이터 저장 중 오류가 발생했습니다.", e);
		}
	}

	public void update(int id, Employee employee) {
		String sql = "UPDATE Employee SET name = ?, phone = ?, address = ?, salary = ?, dependents = ?, department = ?, role = ? WHERE employee_id = ?";

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

			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataUpdateException("업데이트 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataUpdateException("데이터 업데이트 중 오류가 발생했습니다.", e);
		}
	}
}
