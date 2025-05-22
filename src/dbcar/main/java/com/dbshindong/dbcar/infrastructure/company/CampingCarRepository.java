package dbcar.main.java.com.dbshindong.dbcar.infrastructure.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;

public class CampingCarRepository {
    private final Connection conn;

    public CampingCarRepository(Connection conn) {
        this.conn = conn;
    }

    public CampingCar findById(int id) {
        CampingCar car = null;
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

    			car = new CampingCar(car_id, name, plate_number, capacity, image,
    					description, rental_price, company_id, registered_date);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return car;
    }
    
    public List<CampingCar> findByCondition(String condition) throws SQLSyntaxErrorException {
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

    			CampingCar car = new CampingCar(car_id, name, plate_number, capacity, image,
    					description, rental_price, company_id, registered_date);
    			cars.add(car);
    		}

    	} catch (SQLSyntaxErrorException e) {
    		throw new SQLSyntaxErrorException("조건식의 문법이 올바르지 않습니다.");
    	} catch (SQLException e) {
    		e.printStackTrace();
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

    			CampingCar car = new CampingCar(car_id, name, plate_number, capacity, image,
    					description, rental_price, company_id, registered_date);
    			cars.add(car);
    		}

    	} catch (SQLException e) {
    		e.printStackTrace();
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

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM CampingCar WHERE car_id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
}
