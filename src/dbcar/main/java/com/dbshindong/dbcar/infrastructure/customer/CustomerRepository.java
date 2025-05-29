package dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer;

import java.sql.*;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataDeleteException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataInsertException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataNotFoundException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataUpdateException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;

public class CustomerRepository {
	private final Connection conn;

	public CustomerRepository(Connection conn) {
		this.conn = conn;
	}

	public Customer findById(int id) {
		try {
			String sql = "SELECT * FROM Customer where customer_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int customer_id = rs.getInt("customer_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String license_number = rs.getString("license_number");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");

				return new Customer(customer_id, username, password, license_number, name, address, phone, email);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		throw new DataNotFoundException("조회된 데이터가 없습니다.");

	}

	public List<Customer> findByCondition(String condition) throws SQLSyntaxErrorException {
		List<Customer> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM Customer WHERE " + condition;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int customer_id = rs.getInt("customer_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String license_number = rs.getString("license_number");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				list.add(new Customer(customer_id, username, password, license_number, name, address, phone, email));
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return list;
	}

	public List<Customer> findAll() {
		List<Customer> customers = new ArrayList<>();

		try {
			String sql = "SELECT * FROM Customer";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int customer_id = rs.getInt("customer_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String license_number = rs.getString("license_number");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");

				Customer customer = new Customer(customer_id, username, password, license_number, name, address, phone,
						email);
				customers.add(customer);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return customers;
	}

	public void delete(int id) {
		String sql = "DELETE FROM Customer WHERE customer_id = ?";
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

	public void save(Customer customer) {
		String sql = "INSERT INTO Customer (username, password, license_number, name, address, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, customer.getUsername());
			pstmt.setString(2, customer.getPassword());
			pstmt.setString(3, customer.getLicense_number());
			pstmt.setString(4, customer.getName());
			pstmt.setString(5, customer.getAddress());
			pstmt.setString(6, customer.getPhone());
			pstmt.setString(7, customer.getEmail());

			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new DataInsertException("데이터 저장에 실패했습니다.");
			}
		} catch (SQLException e) {
			throw new DataInsertException("데이터 저장 중 오류가 발생했습니다.", e);
		}
	}

	public void update(int id, Customer customer) {
		String sql = "UPDATE Customer SET username = ?, password = ?, license_number = ?, name = ?, address = ?, phone = ?, email = ? WHERE customer_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, customer.getUsername());
			pstmt.setString(2, customer.getPassword());
			pstmt.setString(3, customer.getLicense_number());
			pstmt.setString(4, customer.getName());
			pstmt.setString(5, customer.getAddress());
			pstmt.setString(6, customer.getPhone());
			pstmt.setString(7, customer.getEmail());
			pstmt.setInt(8, id);

			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataUpdateException("업데이트 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataUpdateException("데이터 업데이트 중 오류가 발생했습니다.", e);
		}
	}

	public List<Customer> findByUsername(String userID) {
		List<Customer> customers = new ArrayList<>();

		String sql = "SELECT *, password FROM customer Where username = ?;";
		ResultSet rs;

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int customer_id = rs.getInt("customer_id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String license_number = rs.getString("license_number");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String email = rs.getString("email");

				Customer customer = new Customer(customer_id, username, password, license_number, name, address, phone,
						email);
				customers.add(customer);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}
		return customers;

	}

}
