package dbcar.main.java.com.dbshindong.dbcar.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection conn;

	public DBConnection() {// ID와 pw를 불러와서 DB에 연결합니다.
	}

	public void setConnection(String user, String pw) {
		try {
			System.out.println("DB 연결 시도");
			Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 드라이버 로드
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBTEST", user, pw); // JDBC 연결
			System.out.println("DB 연결 완료");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 오류");
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println("DB 연결 오류");
		}
	}

	public Connection getConnection() {// Connection getter
		if (conn == null) {
			throw new IllegalStateException("❗ 커넥션이 설정되지 않았습니다. 로그인 먼저 하세요.");
		}

		return this.conn;
	}

}
