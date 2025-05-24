package dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;

public class RentalRepository {
	private final Connection conn;

	public RentalRepository(Connection conn) {
		this.conn = conn;
	}

	public Rental findById(int id) {
		Rental rental = null;
		try {
			String sql = "SELECT * FROM Rental WHERE rental_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int rental_id = rs.getInt("rental_id");
				int car_id = rs.getInt("car_id");
				int customer_id = rs.getInt("customer_id");
				int company_id = rs.getInt("company_id");
				Date start_date = rs.getDate("start_date");
				int rental_period = rs.getInt("rental_period");
				int total_charge = rs.getInt("total_charge");
				Date due_date = rs.getDate("due_date");
				String extra_charge_detail = rs.getString("extra_charges");
				int extra_charge = rs.getInt("extra_charge_amount");

				rental = new Rental(rental_id, car_id, customer_id, company_id, start_date, rental_period, total_charge,
						due_date, extra_charge_detail, extra_charge);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return rental;
	}
	
	public List<Rental> findByCondition(String condition) throws SQLSyntaxErrorException {
	    List<Rental> list = new ArrayList<>();
	    try {
	        String sql = "SELECT * FROM Rental WHERE " + condition;
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            int rental_id = rs.getInt("rental_id");
	            int car_id = rs.getInt("car_id");
	            int customer_id = rs.getInt("customer_id");
	            int company_id = rs.getInt("company_id");
	            Date start_date = rs.getDate("start_date");
	            int rental_period = rs.getInt("rental_period");
	            int total_charge = rs.getInt("total_charge");
	            Date due_date = rs.getDate("due_date");
	            String extra_charges = rs.getString("extra_charges");
	            int extra_charge_amount = rs.getInt("extra_charge_amount");
	            list.add(new Rental(rental_id, car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount));
	        }
	    } catch (SQLSyntaxErrorException e) {
	        throw new SQLSyntaxErrorException("조건식 문법 오류: " + e.getMessage());
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}


	public List<Rental> findAll() {
		List<Rental> rentals = new ArrayList<>();
		try {
			String sql = "SELECT * FROM Rental";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int rental_id = rs.getInt("rental_id");
				int car_id = rs.getInt("car_id");
				int customer_id = rs.getInt("customer_id");
				int company_id = rs.getInt("company_id");
				Date start_date = rs.getDate("start_date");
				int rental_period = rs.getInt("rental_period");
				int total_charge = rs.getInt("total_charge");
				Date due_date = rs.getDate("due_date");
				String extra_charge_detail = rs.getString("extra_charges");
				int extra_charge = rs.getInt("extra_charge_amount");

				Rental rental = new Rental(rental_id, car_id, customer_id, company_id, start_date, rental_period,
						total_charge, due_date, extra_charge_detail, extra_charge);
				rentals.add(rental);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentals;
	}

	public void delete(int id) {
		try {
			String sql = "DELETE FROM Rental WHERE rental_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void save(Rental rental) {
		try {
			String sql = "INSERT INTO Rental (car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date, extra_charges, extra_charge_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rental.getCar_id());
			pstmt.setInt(2, rental.getCustomer_id());
			pstmt.setInt(3, rental.getCompany_id());
			pstmt.setDate(4, rental.getStart_date());
			pstmt.setInt(5, rental.getRental_period());
			pstmt.setInt(6, rental.getTotal_charge());
			pstmt.setDate(7, rental.getDue_date());
			pstmt.setString(8, rental.getExtra_charge_detail());
			if (rental.getExtra_charge() == null)
				pstmt.setInt(9, 0);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(int id, Rental rental) {
		try {
			String sql = "UPDATE Rental SET car_id = ?, customer_id = ?, company_id = ?, start_date = ?, rental_period = ?, total_charge = ?, due_date = ?, extra_charges = ?, extra_charge_amount = ? WHERE rental_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rental.getCar_id());
			pstmt.setInt(2, rental.getCustomer_id());
			pstmt.setInt(3, rental.getCompany_id());
			pstmt.setDate(4, rental.getStart_date());
			pstmt.setInt(5, rental.getRental_period());
			pstmt.setInt(6, rental.getTotal_charge());
			pstmt.setDate(7, rental.getDue_date());
			pstmt.setString(8, rental.getExtra_charge_detail());
			pstmt.setInt(9, rental.getExtra_charge());
			pstmt.setInt(10, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
