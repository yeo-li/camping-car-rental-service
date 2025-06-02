package dbcar.main.java.com.dbshindong.dbcar.infrastructure.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataDeleteException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataInsertException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataNotFoundException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataUpdateException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;

public class CampingCarRepository {
	private final Connection conn;

	public CampingCarRepository(Connection conn) {
		this.conn = conn;
	}

	public CampingCar findById(int id) {
		String sql = "SELECT * FROM CampingCar WHERE car_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int car_id = rs.getInt("car_id");
				String name = rs.getString("name");
				String plate_number = rs.getString("plate_number");
				int capacity = rs.getInt("capacity");
				byte[] image = rs.getBytes("image");
				String description = rs.getString("description");
				int rental_price = rs.getInt("rental_price");
				int company_id = rs.getInt("company_id");
				Date registered_date = rs.getDate("registered_date");

				return new CampingCar(car_id, name, plate_number, capacity, image, description, rental_price,
						company_id, registered_date.toString());
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return null;
	}

	public List<CampingCar> findByCondition(String condition) {
		List<CampingCar> cars = new ArrayList<>();

		try {
			String sql = "SELECT * FROM CampingCar WHERE " + condition;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int car_id = rs.getInt("car_id");
				String name = rs.getString("name");
				String plate_number = rs.getString("plate_number");
				int capacity = rs.getInt("capacity");
				byte[] image = rs.getBytes("image");
				String description = rs.getString("description");
				int rental_price = rs.getInt("rental_price");
				int company_id = rs.getInt("company_id");
				Date registered_date = rs.getDate("registered_date");

				CampingCar car = new CampingCar(car_id, name, plate_number, capacity, image, description, rental_price,
						company_id, registered_date.toString());
				cars.add(car);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return cars;
	}

	public List<CampingCar> findAll() {
		List<CampingCar> cars = new ArrayList<>();

		try {
			String sql = "SELECT * FROM CampingCar";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int car_id = rs.getInt("car_id");
				String name = rs.getString("name");
				String plate_number = rs.getString("plate_number");
				int capacity = rs.getInt("capacity");
				byte[] image = rs.getBytes("image");
				String description = rs.getString("description");
				int rental_price = rs.getInt("rental_price");
				int company_id = rs.getInt("company_id");
				Date registered_date = rs.getDate("registered_date");

				CampingCar car = new CampingCar(car_id, name, plate_number, capacity, image, description, rental_price,
						company_id, registered_date.toString());
				cars.add(car);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return cars;
	}

	public void save(CampingCar car) {
		String sql = "INSERT INTO CampingCar (name, plate_number, capacity, image, description, rental_price, company_id, registered_date) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, car.getName());
			pstmt.setString(2, car.getPlate_number());
			pstmt.setInt(3, car.getCapacity());
			pstmt.setBytes(4, car.getImage());
			pstmt.setString(5, car.getDescription());
			pstmt.setInt(6, car.getRental_price());
			pstmt.setInt(7, car.getCompany_id());
			pstmt.setDate(8, new java.sql.Date(car.getRegistered_date().getTime()));

			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new DataInsertException("데이터 저장에 실패했습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataInsertException("데이터 저장 중 오류가 발생했습니다.", e);
		}
	}

	public void update(int id, CampingCar car) {
		String sql = "UPDATE CampingCar SET name = ?, plate_number = ?, capacity = ?, image = ?, description = ?, rental_price = ?, company_id = ?, registered_date = ? "
				+ "WHERE car_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, car.getName());
			pstmt.setString(2, car.getPlate_number());
			pstmt.setInt(3, car.getCapacity());
			pstmt.setBytes(4, car.getImage());
			pstmt.setString(5, car.getDescription());
			pstmt.setInt(6, car.getRental_price());
			pstmt.setInt(7, car.getCompany_id());
			pstmt.setDate(8, new java.sql.Date(car.getRegistered_date().getTime()));
			pstmt.setInt(9, id);

			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataUpdateException("업데이트 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataUpdateException("데이터 업데이트 중 오류가 발생했습니다.", e);
		}
	}

	public void delete(int id) {
		String sql = "DELETE FROM CampingCar WHERE car_id = ?";

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

}
