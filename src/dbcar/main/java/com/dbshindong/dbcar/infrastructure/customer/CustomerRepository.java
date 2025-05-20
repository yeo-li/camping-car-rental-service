package dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer;

import java.sql.*;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;

public class CustomerRepository {
	private final Connection conn;

	public CustomerRepository(Connection conn) {
		this.conn = conn;
	}

	public Customer findById(int id) {

		Customer customer = null;
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

				customer = new Customer(customer_id, username, password, license_number, name, address, phone, email);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return customer;

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
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return customers;
	}

	public void deleteById(int id) {
		String sql = "DELETE FROM Customer WHERE customer_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			int deleted = pstmt.executeUpdate();
			if (deleted > 0)
				System.out.printf("%d개 삭제 되었습니다.\n", deleted);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
