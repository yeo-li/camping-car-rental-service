package dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataDeleteException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataInsertException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataNotFoundException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataUpdateException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.InternalRepairRecord;

public class InternalRepairRecordRepository {
	private final Connection conn;

	public InternalRepairRecordRepository(Connection conn) {
		this.conn = conn;
	}

	public InternalRepairRecord findById(int id) {
		String sql = "SELECT * FROM InternalRepairRecord WHERE internal_repair_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int internal_repair_id = rs.getInt("internal_repair_id");
				int car_id = rs.getInt("car_id");
				int part_id = rs.getInt("part_id");
				String repair_date = rs.getDate("repair_date").toString();
				int duration_minutes = rs.getInt("duration_minutes");
				int employee_id = rs.getInt("employee_id");

				return new InternalRepairRecord(internal_repair_id, car_id, part_id, repair_date, duration_minutes,
						employee_id);
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return null;
	}

	public List<InternalRepairRecord> findByCondition(String condition) {
		List<InternalRepairRecord> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM InternalRepairRecord WHERE " + condition;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int internal_repair_id = rs.getInt("internal_repair_id");
				int car_id = rs.getInt("car_id");
				int part_id = rs.getInt("part_id");
				String repair_date = rs.getDate("repair_date").toString();
				int duration_minutes = rs.getInt("duration_minutes");
				int employee_id = rs.getInt("employee_id");
				list.add(new InternalRepairRecord(internal_repair_id, car_id, part_id, repair_date, duration_minutes,
						employee_id));
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return list;
	}

	public List<InternalRepairRecord> findAll() {
		List<InternalRepairRecord> records = new ArrayList<>();
		String sql = "SELECT * FROM InternalRepairRecord";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int internal_repair_id = rs.getInt("internal_repair_id");
				int car_id = rs.getInt("car_id");
				int part_id = rs.getInt("part_id");
				String repair_date = rs.getDate("repair_date").toString();
				int duration_minutes = rs.getInt("duration_minutes");
				int employee_id = rs.getInt("employee_id");

				InternalRepairRecord record = new InternalRepairRecord(internal_repair_id, car_id, part_id, repair_date,
						duration_minutes, employee_id);
				records.add(record);
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return records;
	}

	public void delete(int id) {
		String sql = "DELETE FROM InternalRepairRecord WHERE internal_repair_id = ?";
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

	public void save(InternalRepairRecord record) {
		String sql = "INSERT INTO InternalRepairRecord (car_id, part_id, repair_date, duration_minutes, employee_id) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, record.getCar_id());
			pstmt.setInt(2, record.getPart_id());
			pstmt.setDate(3, record.getRepair_date());
			pstmt.setInt(4, record.getDuration_minutes());
			pstmt.setInt(5, record.getEmployee_id());

			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new DataInsertException("데이터 저장에 실패했습니다.");
			}
		} catch (SQLException e) {
			throw new DataInsertException("데이터 저장 중 오류가 발생했습니다.", e);
		}
	}

	public void update(int id, InternalRepairRecord record) {
		String sql = "UPDATE InternalRepairRecord SET car_id = ?, part_id = ?, repair_date = ?, duration_minutes = ?, employee_id = ? WHERE internal_repair_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, record.getCar_id());
			pstmt.setInt(2, record.getPart_id());
			pstmt.setDate(3, record.getRepair_date());
			pstmt.setInt(4, record.getDuration_minutes());
			pstmt.setInt(5, record.getEmployee_id());
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