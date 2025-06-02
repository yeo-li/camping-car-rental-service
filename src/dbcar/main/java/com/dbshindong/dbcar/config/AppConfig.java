package dbcar.main.java.com.dbshindong.dbcar.config;

import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarAvailableDateQueryService;
import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarQueryService;
import dbcar.main.java.com.dbshindong.dbcar.application.DataDeleteService;
import dbcar.main.java.com.dbshindong.dbcar.application.DataFetchService;
import dbcar.main.java.com.dbshindong.dbcar.application.DataInsertService;
import dbcar.main.java.com.dbshindong.dbcar.application.DataUpdateService;
import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.application.LoginService;
import dbcar.main.java.com.dbshindong.dbcar.application.RepairRecordFetchService;
import dbcar.main.java.com.dbshindong.dbcar.application.UserRequestRepairService;
import dbcar.main.java.com.dbshindong.dbcar.application.UserReservationModifyService;
import dbcar.main.java.com.dbshindong.dbcar.application.UserReservationQueryService;
import dbcar.main.java.com.dbshindong.dbcar.application.factory.LoginServiceFactory;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.SqlExecutor;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarCompanyRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.EmployeeRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairShopRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.InternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarAvailableDateQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.LoginController;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.SqlQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.UserRequestRepairController;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.UserReservationModifyController;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.UserReservationQueryController;
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
	private SqlExecutor sqlExecutor;

	private DatabaseInitService databaseInitService;
	private LoginService loginService;
	private LoginServiceFactory loginServiceFactory;
	private DataDeleteService dataDeleteService;
	private DataFetchService dataFetchService;
	private DataInsertService dataInsertService;
	private DataUpdateService dataUpdateService;
	private RepairRecordFetchService repairRecordFetchService;
	private CampingCarQueryService campingCarQueryService;
	private CampingCarAvailableDateQueryService campingCarAvailableDateQueryService;
	private UserReservationQueryService userReservationQueryService;
	private UserReservationModifyService userReservationModifyService;
	private UserRequestRepairService userRequestRepairService;
	private LoginController loginController;

	private CampingCarQueryController campingCarQueryController;
	private CampingCarAvailableDateQueryController campingCarAvailableDateQueryController;
	private SqlQueryController sqlQueryController;
	private UserReservationQueryController userReservationQueryController;
	private UserReservationModifyController userReservationModifyController;
	private UserRequestRepairController userRequestRepairController;

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
			customerRepository = new CustomerRepository();
		}

		return customerRepository;
	}

	public CampingCarRepository campingCarRepository() {
		if (campingCarRepository == null) {
			campingCarRepository = new CampingCarRepository();
		}

		return campingCarRepository;
	}

	public CampingCarCompanyRepository campingCarCompanyRepository() {
		if (campingCarCompanyRepository == null) {
			campingCarCompanyRepository = new CampingCarCompanyRepository();
		}

		return campingCarCompanyRepository;
	}

	public RentalRepository rentalRepository() {
		if (rentalRepository == null) {
			rentalRepository = new RentalRepository();
		}

		return rentalRepository;
	}

	public EmployeeRepository employeeRepository() {
		if (employeeRepository == null) {
			employeeRepository = new EmployeeRepository();
		}

		return employeeRepository;
	}

	public ExternalRepairShopRepository externalRepairShopRepository() {
		if (externalRepairShopRepository == null) {
			externalRepairShopRepository = new ExternalRepairShopRepository();
		}

		return externalRepairShopRepository;
	}

	public ExternalRepairRecordRepository externalRepairRecordRepository() {
		if (externalRepairRecordRepository == null) {
			externalRepairRecordRepository = new ExternalRepairRecordRepository();
		}

		return externalRepairRecordRepository;
	}

	public InternalRepairRecordRepository internalRepairRecordRepository() {
		if (internalRepairRecordRepository == null) {
			internalRepairRecordRepository = new InternalRepairRecordRepository();
		}

		return internalRepairRecordRepository;
	}

	public PartRepository partRepository() {
		if (partRepository == null) {
			partRepository = new PartRepository();
		}

		return partRepository;
	}

	public SqlExecutor sqlExecutor() {
		if (sqlExecutor == null) {
			sqlExecutor = new SqlExecutor(dbConnection().getConnection());
		}

		return sqlExecutor;
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

	public DataDeleteService dataDeleteService() {
		if (dataDeleteService == null) {
			dataDeleteService = new DataDeleteService(this.campingCarCompanyRepository(),
					this.externalRepairShopRepository(), this.rentalRepository(), this.campingCarRepository(),
					this.externalRepairRecordRepository(), this.customerRepository(), this.partRepository(),
					this.employeeRepository(), this.internalRepairRecordRepository());
		}

		return dataDeleteService;
	}

	public DataFetchService dataFetchService() {
		if (dataFetchService == null) {
			dataFetchService = new DataFetchService(this.campingCarCompanyRepository(),
					this.externalRepairShopRepository(), this.rentalRepository(), this.campingCarRepository(),
					this.externalRepairRecordRepository(), this.customerRepository(), this.partRepository(),
					this.employeeRepository(), this.internalRepairRecordRepository(), this.sqlExecutor());
		}

		return dataFetchService;
	}

	public DataInsertService dataInsertService() {
		if (dataInsertService == null) {
			dataInsertService = new DataInsertService(this.campingCarCompanyRepository(),
					this.externalRepairShopRepository(), this.rentalRepository(), this.campingCarRepository(),
					this.externalRepairRecordRepository(), this.customerRepository(), this.partRepository(),
					this.employeeRepository(), this.internalRepairRecordRepository());
		}

		return dataInsertService;
	}

	public DataUpdateService dataUpdateService() {
		if (dataUpdateService == null) {
			dataUpdateService = new DataUpdateService(this.campingCarCompanyRepository(),
					this.externalRepairShopRepository(), this.rentalRepository(), this.campingCarRepository(),
					this.externalRepairRecordRepository(), this.customerRepository(), this.partRepository(),
					this.employeeRepository(), this.internalRepairRecordRepository());
		}

		return dataUpdateService;
	}

	public RepairRecordFetchService repairRecordFetchService() {
		if (repairRecordFetchService == null) {
			repairRecordFetchService = new RepairRecordFetchService(this.campingCarRepository(),
					this.externalRepairRecordRepository(), this.externalRepairShopRepository(),
					this.internalRepairRecordRepository(), this.partRepository());
		}

		return repairRecordFetchService;
	}

	public CampingCarQueryService campingCarQueryService() {
		if (campingCarQueryService == null) {
			this.campingCarQueryService = new CampingCarQueryService(this.campingCarRepository());
		}
		return campingCarQueryService;
	}

	public CampingCarAvailableDateQueryService campingCarAvailableDateQueryService() {
		if (campingCarAvailableDateQueryService == null) {
			this.campingCarAvailableDateQueryService = new CampingCarAvailableDateQueryService(this.rentalRepository());
		}
		return campingCarAvailableDateQueryService;
	}

	public UserReservationQueryService userReservationQueryService() {
		if(userReservationQueryService == null) {
			this.userReservationQueryService = new UserReservationQueryService(this.rentalRepository(), this.campingCarRepository(), this.customerRepository(), this.campingCarCompanyRepository());
		}
		return userReservationQueryService;
	}
	public UserReservationModifyService userReservationModifyService() {
		if(userReservationModifyService == null) {
			this.userReservationModifyService = new UserReservationModifyService(this.rentalRepository(), this.campingCarRepository(), this.customerRepository());
		}
		return userReservationModifyService;
	}
	public UserRequestRepairService userRequestRepairService() {
		if(userRequestRepairService == null) {
			this.userRequestRepairService = new UserRequestRepairService(this.externalRepairRecordRepository(),this.externalRepairShopRepository(), this.customerRepository());
		}
		return userRequestRepairService;
	}

	// controller

	public LoginController loginController() {
		if (loginController == null) {
			loginController = new LoginController(this.loginServiceFactory(), this.appCoordinator());
		}

		return loginController;
	}

	public CampingCarQueryController campingCarQueryController() {
		if (campingCarQueryController == null) {
			campingCarQueryController = new CampingCarQueryController(this.campingCarQueryService(),
					this.appCoordinator());
		}
		return campingCarQueryController;
	}

	public CampingCarAvailableDateQueryController campingCarAvailableDateQueryController() {
		if (campingCarAvailableDateQueryController == null) {
			campingCarAvailableDateQueryController = new CampingCarAvailableDateQueryController(
					this.campingCarAvailableDateQueryService(), this.appCoordinator());
		}
		return campingCarAvailableDateQueryController;
	}

	public SqlQueryController sqlQueryController() {
		if (sqlQueryController == null) {
			sqlQueryController = new SqlQueryController(this.dataFetchService());
		}

		return sqlQueryController;
	}
	public UserReservationQueryController userReservationQueryController() {
		if(userReservationQueryController == null) {
			userReservationQueryController = new UserReservationQueryController(this.userReservationQueryService(), this.appCoordinator());
		}
		return userReservationQueryController;
	}
	public UserReservationModifyController userReservationModifyController() {
		if(userReservationModifyController == null) {
			userReservationModifyController = new UserReservationModifyController(this.userReservationModifyService(), this.appCoordinator());
		}
		return userReservationModifyController;
	}
	public UserRequestRepairController userRequestRepairController() {
		if(userRequestRepairController == null) {
			userRequestRepairController = new UserRequestRepairController(this.userRequestRepairService(), this.appCoordinator());
		}
		return userRequestRepairController;
	}
}
