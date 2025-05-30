package dbcar.test.java.ui;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class SqlQueryViewTest {

	public static void main(String[] args) {
		AppCoordinator cd = new AppCoordinator();// dbconnection은 코디네이터에서 관리.
		AppConfig ac = AppConfig.getInstance();
		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");

		cd.start();
		cd.showSqlQueryView();
	}
}
