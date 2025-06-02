package dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import java.time.LocalDate;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataDeleteException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataInsertException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataNotFoundException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataUpdateException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;

import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;

public class RentalRepository {
	private final Connection conn;

	public RentalRepository(Connection conn) {
		this.conn = conn;
	}

	public Rental findById(int id) {
		try {
			String sql = "SELECT * FROM Rental WHERE rental_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Integer rental_id = rs.getInt("rental_id");
				Integer car_id = rs.getInt("car_id");
				Integer customer_id = rs.getInt("customer_id");
				Integer company_id = rs.getInt("company_id");
				Date start_date = rs.getDate("start_date");
				Integer rental_period = rs.getInt("rental_period");
				Integer total_charge = rs.getInt("total_charge");
				Date due_date = rs.getDate("due_date");
				String extra_charge_detail = rs.getString("extra_charges");
				Integer extra_charge = rs.getInt("extra_charge_amount");

				return new Rental(rental_id, car_id, customer_id, company_id, start_date.toString(), rental_period,
						total_charge, due_date.toString(), extra_charge_detail, extra_charge);
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return null;
	}

	public List<Rental> findByCondition(String condition) {
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
				list.add(new Rental(rental_id, car_id, customer_id, company_id, start_date.toString(), rental_period,
						total_charge, due_date.toString(), extra_charges, extra_charge_amount));
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
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
				Integer rental_id = rs.getInt("rental_id");
				Integer car_id = rs.getInt("car_id");
				Integer customer_id = rs.getInt("customer_id");
				Integer company_id = rs.getInt("company_id");
				Date start_date = rs.getDate("start_date");
				Integer rental_period = rs.getInt("rental_period");
				Integer total_charge = rs.getInt("total_charge");
				Date due_date = rs.getDate("due_date");
				String extra_charge_detail = rs.getObject("extra_charges", String.class);
				Integer extra_charge = rs.getObject("extra_charge_amount", Integer.class);
				System.out.println(extra_charge);
				Rental rental = new Rental(rental_id, car_id, customer_id, company_id, start_date.toString(),
						rental_period, total_charge, due_date.toString(), extra_charge_detail, extra_charge);
				rentals.add(rental);
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}
		return rentals;
	}

	public void delete(int id) {
		try {
			String sql = "DELETE FROM Rental WHERE rental_id = ?";
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
			
			if (rental.getExtra_charge_detail() != null)
				pstmt.setString(8, rental.getExtra_charge_detail());
			else
				pstmt.setNull(8, java.sql.Types.VARCHAR);

			if (rental.getExtra_charge() == null) {
				pstmt.setInt(9, 0);

			} else {
				pstmt.setInt(9, rental.getExtra_charge());
			}
			System.out.println(pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			//System.out.println(e+"!");
			e.printStackTrace();
			throw new DataInsertException("데이터 저장 중 오류가 발생했습니다.", e);
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
			if (rental.getExtra_charge_detail() != null)
				pstmt.setString(8, rental.getExtra_charge_detail());
			else
				pstmt.setNull(8, java.sql.Types.VARCHAR);

			if (rental.getExtra_charge() == null) {
				pstmt.setInt(9, 0);
			} else {
				pstmt.setInt(9, rental.getExtra_charge());
			}
			pstmt.setInt(10, id);
			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataUpdateException("업데이트 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataUpdateException("데이터 업데이트 중 오류가 발생했습니다.", e);
		}
	}

	public List<Rental> findByCarId(int car) {
		List<Rental> rentals = new ArrayList<>();
		try {
			String sql = "SELECT * FROM Rental WHERE car_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, car);
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

				Rental rental = new Rental(rental_id, car_id, customer_id, company_id, start_date.toString(), rental_period,
						total_charge, due_date.toString(), extra_charge_detail, extra_charge);
				rentals.add(rental);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentals;
	}
	public List<Rental> findByUserId(int id) {
		List<Rental> rentals = new ArrayList<>();
		try {
			String sql = "SELECT * FROM Rental WHERE Customer_id = ?";
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

				Rental rental = new Rental(rental_id, car_id, customer_id, company_id, start_date.toString(), rental_period,
						total_charge, due_date.toString(), extra_charge_detail, extra_charge);
				rentals.add(rental);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentals;
	}
	public List<Integer> findCarNotInPeriod(Rental rent){
		try {
			List<Integer> availCar = new ArrayList<>();
			String sql = "SELECT DISTINCT car_id "
					+ "FROM Rental "
					+ "WHERE start_date <= ? "
					+ "AND DATE_ADD(start_date, INTERVAL rental_period -1 DAY) >= ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, Date.valueOf(rent.getStart_date().toLocalDate().plusDays(rent.getRental_period() - 1)));
			pstmt.setDate(2, rent.getStart_date());
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int car_id = rs.getInt("car_id");
				availCar.add(car_id);
			}
			return availCar;
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}
	}
	

}
