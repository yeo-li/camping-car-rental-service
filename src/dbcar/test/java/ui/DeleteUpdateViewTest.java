package dbcar.test.java.ui;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;

public class DeleteUpdateViewTest {

	public static void main(String[] args) {
		AppConfig ac = AppConfig.getInstance();
		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");

		ac.appCoordinator().start();
		ac.appCoordinator().showDeleteUpdateView();
	}

}
