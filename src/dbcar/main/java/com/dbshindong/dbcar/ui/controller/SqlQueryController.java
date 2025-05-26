package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import dbcar.main.java.com.dbshindong.dbcar.application.DataFetchService;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Map;

public class SqlQueryController {

	private final DataFetchService dataFetchService = new DataFetchService();

	public List<Map<String, Object>> handleQuery(String sql) throws SQLSyntaxErrorException {
		validateSql(sql);
		return dataFetchService.fetchData(sql);
	}

	private void validateSql(String sql) {
		if (sql == null || sql.trim().isEmpty()) {
			throw new IllegalArgumentException("❗ SQL을 입력해주세요.");
		}

		String lower = sql.trim().toLowerCase();
		if (!lower.startsWith("select")) {
			throw new IllegalArgumentException("❗ SELECT 문만 실행할 수 있습니다.");
		}
	}
}