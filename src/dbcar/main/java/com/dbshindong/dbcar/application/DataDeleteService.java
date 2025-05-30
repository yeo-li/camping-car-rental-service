package dbcar.main.java.com.dbshindong.dbcar.application;

import java.sql.SQLSyntaxErrorException;
import java.util.*;

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

public class DataDeleteService {
	private final CampingCarCompanyRepository campingCarCompanyRepository;
	private final ExternalRepairShopRepository externalRepairShopRepository;
	private final RentalRepository rentalRepository;
	private final CampingCarRepository campingCarRepository;
	private final ExternalRepairRecordRepository externalRepairRecordRepository;
	private final CustomerRepository customerRepository;
	private final PartRepository partRepository;
	private final EmployeeRepository employeeRepository;
	private final InternalRepairRecordRepository internalRepairRecordRepository;

	public DataDeleteService(CampingCarCompanyRepository campingCarCompanyRepository,
			ExternalRepairShopRepository externalRepairShopRepository, RentalRepository rentalRepository,
			CampingCarRepository campingCarRepository, ExternalRepairRecordRepository externalRepairRecordRepository,
			CustomerRepository customerRepository, PartRepository partRepository, EmployeeRepository employeeRepository,
			InternalRepairRecordRepository internalRepairRecordRepository) {
		super();
		this.campingCarCompanyRepository = campingCarCompanyRepository;
		this.externalRepairShopRepository = externalRepairShopRepository;
		this.rentalRepository = rentalRepository;
		this.campingCarRepository = campingCarRepository;
		this.externalRepairRecordRepository = externalRepairRecordRepository;
		this.customerRepository = customerRepository;
		this.partRepository = partRepository;
		this.employeeRepository = employeeRepository;
		this.internalRepairRecordRepository = internalRepairRecordRepository;
	}

	public void deleteCampingCars(String sql) {

		List<CampingCar> cars = campingCarRepository.findByCondition(sql);
		for (CampingCar car : cars) {
			campingCarRepository.delete(car.getCar_id());
		}

	}

	public void deleteCampingCarCompanys(String sql) {

		List<CampingCarCompany> companys = campingCarCompanyRepository.findByCondition(sql);
		for (CampingCarCompany company : companys) {
			campingCarCompanyRepository.delete(company.getCompany_id());
		}

	}

	public void deleteEmployees(String sql) {

		List<Employee> employees = employeeRepository.findByCondition(sql);
		for (Employee employee : employees) {
			employeeRepository.delete(employee.getEmployee_id());
		}

	}

	public void deleteCustomers(String conditionSql) {

		List<Customer> list = customerRepository.findByCondition(conditionSql);
		for (Customer item : list) {
			customerRepository.delete(item.getCustomer_id());
		}

	}

	public void deleteExternalRepairShops(String conditionSql) {
		List<ExternalRepairShop> list = externalRepairShopRepository.findByCondition(conditionSql);
		for (ExternalRepairShop item : list) {
			externalRepairShopRepository.delete(item.getShop_id());
		}

	}

	public void deleteExternalRepairRecords(String conditionSql) {

		List<ExternalRepairRecord> list = externalRepairRecordRepository.findByCondition(conditionSql);
		for (ExternalRepairRecord item : list) {
			externalRepairRecordRepository.delete(item.getExternal_repair_id());
		}

	}

	public void deleteInternalRepairRecords(String conditionSql) {

		List<InternalRepairRecord> list = internalRepairRecordRepository.findByCondition(conditionSql);
		for (InternalRepairRecord item : list) {
			internalRepairRecordRepository.delete(item.getInternal_repair_id());
		}

	}

	public void deleteParts(String conditionSql) {

		List<Part> list = partRepository.findByCondition(conditionSql);
		for (Part item : list) {
			partRepository.delete(item.getPart_id());
		}

	}

	public void deleteRentals(String conditionSql) {
		List<Rental> list = rentalRepository.findByCondition(conditionSql);
		for (Rental item : list) {
			rentalRepository.delete(item.getRental_id());
		}
	}

}
