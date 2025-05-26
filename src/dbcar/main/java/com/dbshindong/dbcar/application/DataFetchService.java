package dbcar.main.java.com.dbshindong.dbcar.application;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCarCompany;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.Employee;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.InternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.Part;
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

public class DataFetchService {
	private final CampingCarCompanyRepository campingCarCompanyRepository = new CampingCarCompanyRepository(
			DBConnection.getConnection());
	private final ExternalRepairShopRepository externalRepairShopRepository = new ExternalRepairShopRepository(
			DBConnection.getConnection());
	private final RentalRepository rentalRepository = new RentalRepository(DBConnection.getConnection());
	private final CampingCarRepository campingCarRepository = new CampingCarRepository(DBConnection.getConnection());
	private final ExternalRepairRecordRepository externalRepairRecordRepository = new ExternalRepairRecordRepository(
			DBConnection.getConnection());
	private final CustomerRepository customerRepository = new CustomerRepository(DBConnection.getConnection());
	private final PartRepository partRepository = new PartRepository(DBConnection.getConnection());
	private final EmployeeRepository employeeRepository = new EmployeeRepository(DBConnection.getConnection());
	private final InternalRepairRecordRepository internalRepairRecordRepository = new InternalRepairRecordRepository(
			DBConnection.getConnection());

	public List<CampingCarCompany> fetchAllCampingCarCompanies() {
		return campingCarCompanyRepository.findAll();
	}

	public List<CampingCar> fetchAllCampingCars() {
		return campingCarRepository.findAll();
	}

	public List<Employee> fetchAllEmployees() {
		return employeeRepository.findAll();
	}

	public List<Customer> fetchAllCustomers() {
		return customerRepository.findAll();
	}

	public List<Rental> fetchAllRentals() {
		return rentalRepository.findAll();
	}

	public List<ExternalRepairRecord> fetchAllExternalRepairRecords() {
		return externalRepairRecordRepository.findAll();
	}

	public List<ExternalRepairShop> fetchAllExternalRepairShops() {
		return externalRepairShopRepository.findAll();
	}

	public List<InternalRepairRecord> fetchAllInternalRepairRecords() {
		return internalRepairRecordRepository.findAll();
	}

	public List<Part> fetchAllParts() {
		return partRepository.findAll();
	}
}
