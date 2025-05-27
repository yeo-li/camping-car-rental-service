package dbcar.main.java.com.dbshindong.dbcar.application;

import java.util.stream.Collectors;
import java.io.*;
import java.sql.*;

public class DatabaseInitService {
	public void initDatabase(Connection conn, String resourcePath) {
		try {
			InputStream is = DatabaseInitService.class.getClassLoader().getResourceAsStream(resourcePath);
			if (is == null) {
				throw new RuntimeException("[initDatabase] sql 파일을 찾을 수 없습니다. (" + resourcePath + ")");
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
			System.err.println("[initDatabase] SQL 스크립트 실행 중 오류 발생");
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
}
