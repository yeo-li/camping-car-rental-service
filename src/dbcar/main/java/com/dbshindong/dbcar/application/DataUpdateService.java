package dbcar.main.java.com.dbshindong.dbcar.application;

import java.sql.SQLSyntaxErrorException;
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
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarCompanyRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.EmployeeRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairShopRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.InternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;

public class DataUpdateService {

	private final CampingCarCompanyRepository campingCarCompanyRepository;
	private final CampingCarRepository campingCarRepository;
	private final CustomerRepository customerRepository;
	private final EmployeeRepository employeeRepository;
	private final ExternalRepairShopRepository externalRepairShopRepository;
	private final ExternalRepairRecordRepository externalRepairRecordRepository;
	private final InternalRepairRecordRepository internalRepairRecordRepository;
	private final PartRepository partRepository;
	private final RentalRepository rentalRepository;

	public DataUpdateService(CampingCarRepository campingCarRepository,
			CampingCarCompanyRepository campingCarCompanyRepository, 
			EmployeeRepository employeeRepository,
			CustomerRepository customerRepository,
			RentalRepository rentalRepository,
			ExternalRepairRecordRepository externalRepairRecordRepository,
			ExternalRepairShopRepository externalRepairShopRepository,
			InternalRepairRecordRepository internalRepairRecordRepository, 
			PartRepository partRepository) {
		this.campingCarCompanyRepository = campingCarCompanyRepository;
		this.campingCarRepository = campingCarRepository;
		this.customerRepository = customerRepository;
		this.employeeRepository = employeeRepository;
		this.externalRepairShopRepository = externalRepairShopRepository;
		this.externalRepairRecordRepository = externalRepairRecordRepository;
		this.internalRepairRecordRepository = internalRepairRecordRepository;
		this.partRepository = partRepository;
		this.rentalRepository = rentalRepository;
	}

	public void updateCampingCars(CampingCar updateCar, String conditionSql) {
		try {
			List<CampingCar> cars = campingCarRepository.findByCondition(conditionSql);
			for (CampingCar car : cars) {
				campingCarRepository.update(car.getCar_id(), updateCar);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void updateCampingCarCompanies(CampingCarCompany updateCompany, String conditionSql) {
		try {
			List<CampingCarCompany> list = campingCarCompanyRepository.findByCondition(conditionSql);
			for (CampingCarCompany item : list) {
				campingCarCompanyRepository.update(item.getCompany_id(), updateCompany);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void updateCustomers(Customer updateCustomer, String conditionSql) {
		try {
			List<Customer> list = customerRepository.findByCondition(conditionSql);
			for (Customer item : list) {
				customerRepository.update(item.getCustomer_id(), updateCustomer);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void updateEmployees(Employee updateEmployee, String conditionSql) {
		try {
			List<Employee> list = employeeRepository.findByCondition(conditionSql);
			for (Employee item : list) {
				employeeRepository.update(item.getEmployee_id(), updateEmployee);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void updateExternalRepairShops(ExternalRepairShop updateShop, String conditionSql) {
		try {
			List<ExternalRepairShop> list = externalRepairShopRepository.findByCondition(conditionSql);
			for (ExternalRepairShop item : list) {
				externalRepairShopRepository.update(item.getShop_id(), updateShop);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void updateExternalRepairRecords(ExternalRepairRecord updateRecord, String conditionSql) {
		try {
			List<ExternalRepairRecord> list = externalRepairRecordRepository.findByCondition(conditionSql);
			for (ExternalRepairRecord item : list) {
				externalRepairRecordRepository.update(item.getExternal_repair_id(), updateRecord);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void updateInternalRepairRecords(InternalRepairRecord updateRecord, String conditionSql) {
		try {
			List<InternalRepairRecord> list = internalRepairRecordRepository.findByCondition(conditionSql);
			for (InternalRepairRecord item : list) {
				internalRepairRecordRepository.update(item.getInternal_repair_id(), updateRecord);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void updateParts(Part updatePart, String conditionSql) {
		try {
			List<Part> list = partRepository.findByCondition(conditionSql);
			for (Part item : list) {
				partRepository.update(item.getPart_id(), updatePart);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void updateRentals(Rental updateRental, String conditionSql) {
		try {
			List<Rental> list = rentalRepository.findByCondition(conditionSql);
			for (Rental item : list) {
				rentalRepository.update(item.getRental_id(), updateRental);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}
}
