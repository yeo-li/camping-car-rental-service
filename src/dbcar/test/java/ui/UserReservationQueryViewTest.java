package dbcar.test.java.ui;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;

public class UserReservationQueryViewTest {
	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static PartRepository partRepository;
	
	public static void main(String[] args) {
		
		System.out.println("[[UserReservationQueryPanel Test]]");

		AppConfig ac = AppConfig.getInstance();
		
		ac.appCoordinator().setUser("user2");
		ac.dbConnection().setConnection("root", "1234");
		//ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),"dbcar/main/java/resources/DatabaseInit.sql");
		ac.appCoordinator().start();
		ac.appCoordinator().showUserReservationQueryView();
	}
}
