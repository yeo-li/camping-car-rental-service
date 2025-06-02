package dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataDeleteException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataInsertException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataUpdateException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;

public class ExternalRepairRecordRepository {
	private final AppConfig ac = AppConfig.getInstance();

	public ExternalRepairRecord findById(int id) {
		String sql = "SELECT * FROM ExternalRepairRecord WHERE external_repair_id = ?";
		try {
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return extractRecord(rs);
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return null;
	}

	public List<ExternalRepairRecord> findByCondition(String condition) {
		List<ExternalRepairRecord> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM ExternalRepairRecord WHERE " + condition;
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Integer external_repair_id = rs.getInt("external_repair_id");
				Integer car_id = rs.getInt("car_id");
				Integer shop_id = rs.getInt("shop_id");
				Integer company_id = rs.getInt("company_id");
				Integer customer_id = rs.getInt("customer_id");
				String content = rs.getString("content");
				String repair_date = rs.getDate("repair_date").toString();
				int cost = rs.getInt("cost");
				String due_date = rs.getDate("due_date").toString();
				String note = rs.getString("note");
				list.add(new ExternalRepairRecord(external_repair_id, car_id, shop_id, company_id, customer_id, content,
						repair_date, cost, due_date, note));
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}
		return list;
	}

	public List<ExternalRepairRecord> findAll() {
		List<ExternalRepairRecord> records = new ArrayList<>();
		String sql = "SELECT * FROM ExternalRepairRecord";
		try {
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				records.add(extractRecord(rs));
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return records;
	}

	public void save(ExternalRepairRecord record) {
		String sql = "INSERT INTO ExternalRepairRecord (car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setInt(1, record.getCar_id());
			pstmt.setInt(2, record.getShop_id());
			pstmt.setInt(3, record.getCompany_id());
			pstmt.setInt(4, record.getCustomer_id());
			pstmt.setString(5, record.getContent());
			pstmt.setDate(6, record.getRepair_date());
			pstmt.setInt(7, record.getCost());
			pstmt.setDate(8, record.getDue_date());
			pstmt.setString(9, record.getNote());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new DataInsertException("데이터 저장에 실패했습니다.");
			}
		} catch (SQLException e) {
			throw new DataInsertException("데이터 저장 중 오류가 발생했습니다.", e);
		}
	}

	public void update(int id, ExternalRepairRecord record) {
		String sql = "UPDATE ExternalRepairRecord SET car_id = ?, shop_id = ?, company_id = ?, customer_id = ?, content = ?, repair_date = ?, cost = ?, due_date = ?, note = ? WHERE external_repair_id = ?";
		try {
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setInt(1, record.getCar_id());
			pstmt.setInt(2, record.getShop_id());
			pstmt.setInt(3, record.getCompany_id());
			pstmt.setInt(4, record.getCustomer_id());
			pstmt.setString(5, record.getContent());
			pstmt.setDate(6, record.getRepair_date());
			pstmt.setInt(7, record.getCost());
			pstmt.setDate(8, record.getDue_date());
			pstmt.setString(9, record.getNote());
			pstmt.setInt(10, id);
			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataUpdateException("업데이트 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataUpdateException("데이터 업데이트 중 오류가 발생했습니다.", e);
		}
	}

	public void delete(int id) {
		String sql = "DELETE FROM ExternalRepairRecord WHERE external_repair_id = ?";
		try {
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

	private ExternalRepairRecord extractRecord(ResultSet rs) throws SQLException {
		int external_repair_id = rs.getInt("external_repair_id");
		int car_id = rs.getInt("car_id");
		int shop_id = rs.getInt("shop_id");
		int company_id = rs.getInt("company_id");
		int customer_id = rs.getInt("customer_id");
		String content = rs.getString("content");
		String repair_date = rs.getDate("repair_date").toString();
		int cost = rs.getInt("cost");
		String due_date = rs.getDate("due_date").toString();
		String note = rs.getString("note");

		return new ExternalRepairRecord(external_repair_id, car_id, shop_id, company_id, customer_id, content,
				repair_date, cost, due_date, note);
	}

	public List<ExternalRepairRecord> findByCarAndCustomer(int carid, int custid) {
		List<ExternalRepairRecord> records = new ArrayList<>();
		String sql = "SELECT * FROM ExternalRepairRecord WHERE car_id = ? AND customer_id = ?";
		try {
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setInt(1, carid);
			pstmt.setInt(2, custid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				records.add(extractRecord(rs));
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return records;
	}
}
