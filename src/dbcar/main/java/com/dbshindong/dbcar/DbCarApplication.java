package dbcar.main.java.com.dbshindong.dbcar;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;

public class DbCarApplication {

	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static PartRepository partRepository;

	public static void main(String[] args) {

		System.out.println("[프로그램 시작]");
		
		AppConfig ac = AppConfig.getInstance();
		
		ac.appCoordinator().start();
	}
}

