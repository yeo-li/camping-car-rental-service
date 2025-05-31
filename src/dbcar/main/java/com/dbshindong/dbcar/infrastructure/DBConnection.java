package dbcar.main.java.com.dbshindong.dbcar.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DatabaseException;

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
			throw new DatabaseException("JDBC 드라이버 로드 오류", e);
		} catch (SQLException e) {
			throw new DatabaseException("DB 연결 오류", e);
		}
	}

	public Connection getConnection() {// Connection getter
		if (conn == null) {
			throw new DatabaseException("커넥션이 설정되지 않았습니다. 로그인 먼저 하세요.");
		}

		return this.conn;
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
				System.out.println("DB 연결 종료");
			} catch (SQLException e) {
				throw new DatabaseException("DB 연결 종료 중 오류 발생", e);
			}
		}
	}

}
