package dbcar.main.java.com.dbshindong.dbcar.application.factory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.application.LoginService;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DatabaseInitException;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;

public class LoginServiceFactory {
	private AppConfig ac = AppConfig.getInstance();

	public LoginServiceFactory() {
	}

	public LoginService create(String DBId, String password) {
		try {
			
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "1234")){
				InputStream is = DatabaseInitService.class.getClassLoader().getResourceAsStream("dbcar/main/java/resources/DatabaseCreate.sql");
				if (is == null) {
					throw new FileNotFoundException("데이터베이스 초기화 파일을 찾을 수 없습니다. (" + "dbcar/main/java/resources/DatabaseCreate.sql" + ")");
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				Statement stmt = conn.createStatement();

				String sql = reader.lines().collect(Collectors.joining("\n"));
				for (String command : sql.split(";")) {
					if (!command.strip().isEmpty()) {
						stmt.execute(command.trim() + ";");
					}
				}
				System.out.println("[createDatabase] 데이터베이스 생성");

			} catch (SQLException e) {
				throw new DatabaseInitException("데이터베이스 생성 중 오류가 발생했습니다.", e);
			} catch (FileNotFoundException e) {
				throw new DatabaseInitException(e.getMessage(), e);
			}
			System.out.println(DBId + password);
			ac.dbConnection().setConnection(DBId, password);
			// 데이터베이스 생성 및 재할당
			return ac.loginService();
		} catch(Exception e) {
			throw e;
		}

	}
}