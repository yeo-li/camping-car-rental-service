package dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external;

import java.sql.*;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataDeleteException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataInsertException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataUpdateException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;

public class ExternalRepairShopRepository {
	private final AppConfig ac = AppConfig.getInstance();

	public List<ExternalRepairShop> findAll() {
		List<ExternalRepairShop> shops = new ArrayList<>();
		String sql = "SELECT * FROM ExternalRepairShop";
		try {
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int shop_id = rs.getInt("shop_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String manager_name = rs.getString("manager_name");
				String manager_email = rs.getString("manager_email");

				ExternalRepairShop shop = new ExternalRepairShop(shop_id, name, address, phone, manager_name,
						manager_email);
				shops.add(shop);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return shops;
	}

	public ExternalRepairShop findById(int id) {
		try {
			String sql = "SELECT * FROM ExternalRepairShop WHERE shop_id = ?";
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int shop_id = rs.getInt("shop_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String manager_name = rs.getString("manager_name");
				String manager_email = rs.getString("manager_email");

				return new ExternalRepairShop(shop_id, name, address, phone, manager_name, manager_email);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return null;
	}

	public List<ExternalRepairShop> findByCondition(String condition) {
		List<ExternalRepairShop> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM ExternalRepairShop WHERE " + condition;
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int shop_id = rs.getInt("shop_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String manager_name = rs.getString("manager_name");
				String manager_email = rs.getString("manager_email");
				list.add(new ExternalRepairShop(shop_id, name, address, phone, manager_name, manager_email));
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return list;
	}

	public void delete(int id) {
		try {
			String sql = "DELETE FROM ExternalRepairShop WHERE shop_id = ?";
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataDeleteException("삭제 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataDeleteException("데이터 삭제 중 오류가 발생했습니다.", e);
		}
	}

	public void save(ExternalRepairShop shop) {
		try {
			String sql = "INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setString(1, shop.getName());
			pstmt.setString(2, shop.getAddress());
			pstmt.setString(3, shop.getPhone());
			pstmt.setString(4, shop.getManager_name());
			pstmt.setString(5, shop.getManager_email());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new DataInsertException("데이터 저장에 실패했습니다.");
			}
		} catch (SQLException e) {
			throw new DataInsertException("데이터 저장 중 오류가 발생했습니다.", e);
		}
	}

	public void update(int id, ExternalRepairShop shop) {
		try {
			String sql = "UPDATE ExternalRepairShop SET name = ?, address = ?, phone = ?, manager_name = ?, manager_email = ? WHERE shop_id = ?";
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setString(1, shop.getName());
			pstmt.setString(2, shop.getAddress());
			pstmt.setString(3, shop.getPhone());
			pstmt.setString(4, shop.getManager_name());
			pstmt.setString(5, shop.getManager_email());
			pstmt.setInt(6, id);
			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataUpdateException("업데이트 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataUpdateException("데이터 업데이트 중 오류가 발생했습니다.", e);
		}
	}
}
