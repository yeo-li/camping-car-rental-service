package dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.Part;

public class PartRepository {
	private final Connection conn;

	public PartRepository(Connection conn) {
		this.conn = conn;
	}

	public List<Part> findAll() {
		List<Part> parts = new ArrayList<>();
		String sql = "SELECT * FROM Part";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int part_id = rs.getInt("part_id");
				String name = rs.getString("name");
				int unit_price = rs.getInt("unit_price");
				int stock_quantity = rs.getInt("stock_quantity");
				Date stock_date = rs.getDate("stock_date");
				String supplier_name = rs.getString("supplier_name");

				Part part = new Part(part_id, name, unit_price, stock_quantity, stock_date, supplier_name);
				parts.add(part);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return parts;
	}

	public Part findById(int id) {
		String sql = "SELECT * FROM Part WHERE part_id = ?";
		Part part = null;

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int part_id = rs.getInt("part_id");
				String name = rs.getString("name");
				int unit_price = rs.getInt("unit_price");
				int stock_quantity = rs.getInt("stock_quantity");
				Date stock_date = rs.getDate("stock_date");
				String supplier_name = rs.getString("supplier_name");

				part = new Part(part_id, name, unit_price, stock_quantity, stock_date, supplier_name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return part;
	}

	public void save(Part part) {
		String sql = "INSERT INTO Part (name, unit_price, stock_quantity, stock_date, supplier_name) VALUES (?, ?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, part.getName());
			pstmt.setInt(2, part.getUnit_price());
			pstmt.setInt(3, part.getStock_quantity());
			pstmt.setDate(4, part.getStock_date());
			pstmt.setString(5, part.getSupplier_name());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(int id, Part part) {
		String sql = "UPDATE Part SET name = ?, unit_price = ?, stock_quantity = ?, stock_date = ?, supplier_name = ? WHERE part_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, part.getName());
			pstmt.setInt(2, part.getUnit_price());
			pstmt.setInt(3, part.getStock_quantity());
			pstmt.setDate(4, part.getStock_date());
			pstmt.setString(5, part.getSupplier_name());
			pstmt.setInt(6, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(int id) {
		String sql = "DELETE FROM Part WHERE part_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}