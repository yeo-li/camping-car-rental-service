package dbcar.test.java.ui;

import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarQueryService;
import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarAvailableDateQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;
import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarAvailableDateQueryService;
public class CampingCarQueryViewTest {
	
	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static PartRepository partRepository;
	
	public static void main(String[] args) {
		System.out.println("[[CampingCarQueryVeiwTest 초기 세팅]]");
		System.out.println("\n[[CampingCarQueryTest]]");
		AppConfig ac = AppConfig.getInstance();
		ac.appCoordinator().setUser("user2");
		// ac.dbConnection().setConnection("user1", "user1");
		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),"dbcar/main/java/resources/DatabaseInit.sql");
		ac.appCoordinator().start();
		ac.appCoordinator().showCampingCarQueryView();
	}
}
