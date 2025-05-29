package dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataDeleteException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataInsertException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataNotFoundException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataUpdateException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;
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
				String stock_date = rs.getDate("stock_date").toString();
				String supplier_name = rs.getString("supplier_name");

				Part part = new Part(part_id, name, unit_price, stock_quantity, stock_date, supplier_name);
				parts.add(part);
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return parts;
	}

	public Part findById(int id) {
		String sql = "SELECT * FROM Part WHERE part_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int part_id = rs.getInt("part_id");
				String name = rs.getString("name");
				int unit_price = rs.getInt("unit_price");
				int stock_quantity = rs.getInt("stock_quantity");
				String stock_date = rs.getDate("stock_date").toString();
				String supplier_name = rs.getString("supplier_name");

				return new Part(part_id, name, unit_price, stock_quantity, stock_date, supplier_name);
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return null;
	}

	public List<Part> findByCondition(String condition) {
		List<Part> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM Part WHERE " + condition;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int part_id = rs.getInt("part_id");
				String name = rs.getString("name");
				int unit_price = rs.getInt("unit_price");
				int stock_quantity = rs.getInt("stock_quantity");
				String stock_date = rs.getDate("stock_date").toString();
				String supplier_name = rs.getString("supplier_name");
				list.add(new Part(part_id, name, unit_price, stock_quantity, stock_date, supplier_name));
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return list;
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

			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new DataInsertException("데이터 저장에 실패했습니다.");
			}
		} catch (SQLException e) {
			throw new DataInsertException("데이터 저장 중 오류가 발생했습니다.", e);
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

			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataUpdateException("업데이트 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataUpdateException("데이터 업데이트 중 오류가 발생했습니다.", e);
		}
	}

	public void delete(int id) {
		String sql = "DELETE FROM Part WHERE part_id = ?";

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