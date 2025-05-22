package dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external;

import java.sql.*;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;

public class ExternalRepairShopRepository {
	private final Connection conn;

	public ExternalRepairShopRepository(Connection conn) {
		this.conn = conn;
	}

	public List<ExternalRepairShop> findAll() {
		List<ExternalRepairShop> shops = new ArrayList<>();
		String sql = "SELECT * FROM ExternalRepairShop";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int shop_id = rs.getInt("shop_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String manager_name = rs.getString("manager_name");
				String manager_email = rs.getString("manager_email");

				ExternalRepairShop shop = new ExternalRepairShop(shop_id, name, address, phone, manager_name, manager_email);
				shops.add(shop);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shops;
	}

	public ExternalRepairShop findById(int id) {
		ExternalRepairShop shop = null;
		try {
			String sql = "SELECT * FROM ExternalRepairShop WHERE shop_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int shop_id = rs.getInt("shop_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String manager_name = rs.getString("manager_name");
				String manager_email = rs.getString("manager_email");

				shop = new ExternalRepairShop(shop_id, name, address, phone, manager_name, manager_email);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shop;
	}

	public void delete(int id) {
		try {
			String sql = "DELETE FROM ExternalRepairShop WHERE shop_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void save(ExternalRepairShop shop) {
		try {
			String sql = "INSERT INTO ExternalRepairShop (name, address, phone, manager_name, manager_email) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, shop.getName());
			pstmt.setString(2, shop.getAddress());
			pstmt.setString(3, shop.getPhone());
			pstmt.setString(4, shop.getManager_name());
			pstmt.setString(5, shop.getManager_email());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(int id, ExternalRepairShop shop) {
		try {
			String sql = "UPDATE ExternalRepairShop SET name = ?, address = ?, phone = ?, manager_name = ?, manager_email = ? WHERE shop_id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, shop.getName());
			pstmt.setString(2, shop.getAddress());
			pstmt.setString(3, shop.getPhone());
			pstmt.setString(4, shop.getManager_name());
			pstmt.setString(5, shop.getManager_email());
			pstmt.setInt(6, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
