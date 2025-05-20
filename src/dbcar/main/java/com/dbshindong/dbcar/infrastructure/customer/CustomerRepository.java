package dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer;

import java.sql.*;

import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;

public class CustomerRepository {
	private final Connection conn;
	
	public CustomerRepository(Connection conn) {
		this.conn = conn;
	}
	
	public Customer findById(int id) {
		
		Customer customer = null;
		try(Connection conn = this.conn) {
			String sql = "SELECT * FROM Customer where customer_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
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
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return customer;
		
	}
	
}
