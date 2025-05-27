package dbcar.main.java.com.dbshindong.dbcar.config;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.application.LoginService;
import dbcar.main.java.com.dbshindong.dbcar.application.factory.LoginServiceFactory;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarCompanyRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.EmployeeRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairShopRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.InternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.LoginController;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class AppConfig {

	private static final AppConfig INSTANCE = new AppConfig();

	private DBConnection dbConnection;

	public AppCoordinator appCoordinator;

	private CustomerRepository customerRepository;
	private CampingCarRepository campingCarRepository;
	private CampingCarCompanyRepository campingCarCompanyRepository;
	private RentalRepository rentalRepository;
	private EmployeeRepository employeeRepository;
	private ExternalRepairShopRepository externalRepairShopRepository;
	private ExternalRepairRecordRepository externalRepairRecordRepository;
	private InternalRepairRecordRepository internalRepairRecordRepository;
	private PartRepository partRepository;

	private DatabaseInitService databaseInitService;
	private LoginService loginService;
	private LoginServiceFactory loginServiceFactory;

	private LoginController loginController;

	private AppConfig() {

	}

	public static AppConfig getInstance() {
		return INSTANCE;
	}

	public AppCoordinator appCoordinator() {
		if (appCoordinator == null) {
			appCoordinator = new AppCoordinator();
		}
		return appCoordinator;
	}

	public DBConnection dbConnection() {
		if (dbConnection == null) {
			dbConnection = new DBConnection();
		}
		return dbConnection;
	}

	public CustomerRepository customerRepository() {
		if (customerRepository == null) {
			customerRepository = new CustomerRepository(dbConnection().getConnection());
		}

		return customerRepository;
	}

	public CampingCarRepository campingCarRepository() {
		if (campingCarRepository == null) {
			campingCarRepository = new CampingCarRepository(dbConnection().getConnection());
		}

		return campingCarRepository;
	}

	public CampingCarCompanyRepository campingCarCompanyRepository() {
		if (campingCarCompanyRepository == null) {
			campingCarCompanyRepository = new CampingCarCompanyRepository(dbConnection().getConnection());
		}

		return campingCarCompanyRepository;
	}

	public RentalRepository rentalRepository() {
		if (rentalRepository == null) {
			rentalRepository = new RentalRepository(dbConnection().getConnection());
		}

		return rentalRepository;
	}

	public EmployeeRepository employeeRepository() {
		if (employeeRepository == null) {
			employeeRepository = new EmployeeRepository(dbConnection().getConnection());
		}

		return employeeRepository;
	}

	public ExternalRepairShopRepository externalRepairShopRepository() {
		if (externalRepairShopRepository == null) {
			externalRepairShopRepository = new ExternalRepairShopRepository(dbConnection().getConnection());
		}

		return externalRepairShopRepository;
	}

	public ExternalRepairRecordRepository externalRepairRecordRepository() {
		if (externalRepairRecordRepository == null) {
			externalRepairRecordRepository = new ExternalRepairRecordRepository(dbConnection().getConnection());
		}

		return externalRepairRecordRepository;
	}

	public InternalRepairRecordRepository internalRepairRecordRepository() {
		if (internalRepairRecordRepository == null) {
			internalRepairRecordRepository = new InternalRepairRecordRepository(dbConnection().getConnection());
		}

		return internalRepairRecordRepository;
	}

	public PartRepository partRepository() {
		if (partRepository == null) {
			partRepository = new PartRepository(dbConnection().getConnection());
		}

		return partRepository;
	}

	public DatabaseInitService databaseInitService() {
		if (databaseInitService == null) {
			databaseInitService = new DatabaseInitService();
		}

		return databaseInitService;
	}

	public LoginService loginService() {
		if (loginService == null) {
			loginService = new LoginService(this.customerRepository());
		}

		return loginService;
	}

	public LoginServiceFactory loginServiceFactory() {
		if (loginServiceFactory == null) {
			loginServiceFactory = new LoginServiceFactory();
		}

		return loginServiceFactory;
	}

	public LoginController loginController() {
		if (loginController == null) {
			loginController = new LoginController(this.loginServiceFactory(), this.appCoordinator());
		}

		return loginController;
	}

}
