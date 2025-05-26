package dbcar.test.java.ui;

import dbcar.main.java.com.dbshindong.dbcar.ui.view.CampingCarQueryView;
import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarQueryService;
import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarAvailableDateQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarQueryController;
import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarAvailableDateQueryService;
public class CampingCarQueryViewTest {
	
	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static PartRepository partRepository;
	
	public static void main(String[] args) {
		
		System.out.println("[[CampingCarQueryVeiwTest 초기 세팅]]");
		
		dc = new DBConnection("root", "1234");
		databaseInitService = new DatabaseInitService();
		partRepository = new PartRepository(DBConnection.getConnection());
		databaseInitService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");

		System.out.println("\n[[CampingCarQueryVeiwTest]]");
		CampingCarRepository campingCarRepository = new CampingCarRepository(DBConnection.getConnection()); 
		CampingCarQueryService campingCarService = new CampingCarQueryService(campingCarRepository);
		CampingCarQueryController campingCarQueryController = new CampingCarQueryController(campingCarService);
		
		
		RentalRepository rentalRepository = new RentalRepository(DBConnection.getConnection()); 
		CampingCarAvailableDateQueryService campingCarAvailableDateQueryService = new CampingCarAvailableDateQueryService(rentalRepository);
		CampingCarAvailableDateQueryController campingCarAvailableDateQueryController = new CampingCarAvailableDateQueryController(campingCarAvailableDateQueryService);
		
		new CampingCarQueryView(campingCarQueryController, campingCarAvailableDateQueryController);
	}
}
