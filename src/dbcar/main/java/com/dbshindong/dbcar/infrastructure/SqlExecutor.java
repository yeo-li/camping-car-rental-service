package dbcar.main.java.com.dbshindong.dbcar.infrastructure;

import java.sql.*;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;

public class SqlExecutor {
	private final Connection conn;

	public SqlExecutor(Connection conn) {
		super();
		this.conn = conn;
	}

	public List<Map<String, Object>> findData(String selectSql) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(selectSql);
			ResultSet rs = pstmt.executeQuery();

			return parseResultSet(rs);
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}
	}

	private List<Map<String, Object>> parseResultSet(ResultSet rs) {
		List<Map<String, Object>> results = new ArrayList<>();
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new LinkedHashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String tableName = metaData.getTableName(i);
					String columnName = metaData.getColumnLabel(i);

					// "table_column" 형식으로 충돌 방지
					String uniqueKey = (tableName == null || tableName.isEmpty()) ? columnName
							: tableName + "_" + columnName;

					row.put(uniqueKey, rs.getObject(i));
				}
				results.add(row);
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return results;
	}
}
