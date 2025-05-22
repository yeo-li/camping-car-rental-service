package dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.InternalRepairRecord;

public class InternalRepairRecordRepository {
	private final Connection conn;

	public InternalRepairRecordRepository(Connection conn) {
		this.conn = conn;
	}

	public InternalRepairRecord findById(int id) {
		InternalRepairRecord record = null;
		String sql = "SELECT * FROM InternalRepairRecord WHERE internal_repair_id = ?";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int internal_repair_id = rs.getInt("internal_repair_id");
				int car_id = rs.getInt("car_id");
				int part_id = rs.getInt("part_id");
				Date repair_date = rs.getDate("repair_date");
				int duration_minutes = rs.getInt("duration_minutes");
				int employee_id = rs.getInt("employee_id");

				record = new InternalRepairRecord(
						internal_repair_id, car_id, part_id, repair_date, duration_minutes, employee_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return record;
	}
	
	public List<InternalRepairRecord> findByCondition(String condition) throws SQLSyntaxErrorException {
	    List<InternalRepairRecord> list = new ArrayList<>();
	    try {
	        String sql = "SELECT * FROM InternalRepairRecord WHERE " + condition;
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            int internal_repair_id = rs.getInt("internal_repair_id");
	            int car_id = rs.getInt("car_id");
	            int part_id = rs.getInt("part_id");
	            Date repair_date = rs.getDate("repair_date");
	            int duration_minutes = rs.getInt("duration_minutes");
	            int employee_id = rs.getInt("employee_id");
	            list.add(new InternalRepairRecord(internal_repair_id, car_id, part_id, repair_date, duration_minutes, employee_id));
	        }
	    } catch (SQLSyntaxErrorException e) {
	        throw new SQLSyntaxErrorException("조건식 문법 오류: " + e.getMessage());
	    } catch (SQLException e) {
	        e.printStackTrace();
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
				Date repair_date = rs.getDate("repair_date");
				int duration_minutes = rs.getInt("duration_minutes");
				int employee_id = rs.getInt("employee_id");

				InternalRepairRecord record = new InternalRepairRecord(
						internal_repair_id, car_id, part_id, repair_date, duration_minutes, employee_id);
				records.add(record);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return records;
	}

	public void delete(int id) {
		String sql = "DELETE FROM InternalRepairRecord WHERE internal_repair_id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}