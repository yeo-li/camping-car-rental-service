package dbcar.test.java.ui;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class loginViewTest {
	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static PartRepository partRepository;

	public static void main(String[] args) {

		System.out.println("[[LoginVeiwTest 초기 세팅]]");
		System.out.println("\n[[LoginVeiwTest]]");
		AppCoordinator cd = new AppCoordinator();// dbconnection은 코디네이터에서 관리.
		cd.start();
	}
}
