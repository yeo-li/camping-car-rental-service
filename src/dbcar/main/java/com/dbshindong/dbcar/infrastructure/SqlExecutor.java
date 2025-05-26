package dbcar.main.java.com.dbshindong.dbcar.infrastructure;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;

public class SqlExecutor {
	private final Connection conn;

	public SqlExecutor(Connection conn) {
		super();
		this.conn = conn;
	}

	public List<Map<String, Object>> findData(String selectSql) throws SQLSyntaxErrorException {
		try {
			PreparedStatement pstmt = conn.prepareStatement(selectSql);
			ResultSet rs = pstmt.executeQuery();

			return parseResultSet(rs);
		} catch (SQLSyntaxErrorException e) {
			throw new SQLSyntaxErrorException("SQL 문법이 올바르지 않습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Map<String, Object>> parseResultSet(ResultSet rs) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<>();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		while (rs.next()) {
			Map<String, Object> row = new LinkedHashMap<>(); // 순서 보장
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				Object value = rs.getObject(i);
				row.put(columnName, value);
			}
			results.add(row);
		}

		return results;
	}
}
