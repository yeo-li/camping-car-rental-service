package dbcar.test.java.ui;

import java.sql.Connection;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.application.LoginService;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.LoginController;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.LoginPanel;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
public class loginViewTest {
	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static PartRepository partRepository;

	public static void main(String[] args) {
		
		System.out.println("[[LoginVeiwTest 초기 세팅]]");
		
		/*dc = new DBConnection("root", "1234");
		databaseInitService = new DatabaseInitService();
		partRepository = new PartRepository(DBConnection.getConnection());
		databaseInitService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");
*/
		System.out.println("\n[[LoginVeiwTest]]");

			
 
        AppCoordinator cd = new AppCoordinator();
        cd.start();
		
	}
}
