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
		} catch (NullPointerException e) {
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

	public void delete(int id) {
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
		    if (result > 0) System.out.printf("%d개 삭제 되었습니다.\n", result);
		} catch (SQLException e) {
		    e.printStackTrace();
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
			
			int result = pstmt.executeUpdate();
			if(result > 0)
				System.out.printf("고객 정보가 %d건 수정되었습니다.", result);
			else
				System.out.println("해당 고객 ID를 찾을 수 없습니다.");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
