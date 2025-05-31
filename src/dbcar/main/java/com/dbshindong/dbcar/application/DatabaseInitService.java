package dbcar.main.java.com.dbshindong.dbcar.application;

import java.util.stream.Collectors;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DatabaseInitException;

import java.io.*;
import java.sql.*;

public class DatabaseInitService {
	public void initDatabase(Connection conn, String resourcePath) {
		try {
			InputStream is = DatabaseInitService.class.getClassLoader().getResourceAsStream(resourcePath);
			if (is == null) {
				throw new FileNotFoundException("데이터베이스 초기화 파일을 찾을 수 없습니다. (" + resourcePath + ")");
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			Statement stmt = conn.createStatement();

			String sql = reader.lines().collect(Collectors.joining("\n"));
			for (String command : sql.split(";")) {
				if (!command.strip().isEmpty()) {
					stmt.execute(command.trim() + ";");
				}
			}
			System.out.println("[initDatabase] 데이터베이스 초기화 완료");

		} catch (SQLException e) {
			throw new DatabaseInitException("데이터베이스 초기화 중 오류가 발생했습니다.", e);
		} catch (FileNotFoundException e) {
			throw new DatabaseInitException(e.getMessage(), e);
		}
	}
}
