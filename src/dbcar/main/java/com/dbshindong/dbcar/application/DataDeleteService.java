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

public class DataDeleteService {
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

	public void deleteCampingCars(String sql) {
		try {
			List<CampingCar> cars = campingCarRepository.findByCondition(sql);
			for (CampingCar car : cars) {
				campingCarRepository.delete(car.getCar_id());
			}

		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void deleteCampingCarCompanys(String sql) {
		try {
			List<CampingCarCompany> companys = campingCarCompanyRepository.findByCondition(sql);
			for (CampingCarCompany company : companys) {
				campingCarCompanyRepository.delete(company.getCompany_id());
			}

		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void deleteEmployees(String sql) {
		try {
			List<Employee> employees = employeeRepository.findByCondition(sql);
			for (Employee employee : employees) {
				employeeRepository.delete(employee.getEmployee_id());
			}

		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void deleteCustomers(String conditionSql) {
		try {
			List<Customer> list = customerRepository.findByCondition(conditionSql);
			for (Customer item : list) {
				customerRepository.delete(item.getCustomer_id());
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void deleteExternalRepairShops(String conditionSql) {
		try {
			List<ExternalRepairShop> list = externalRepairShopRepository.findByCondition(conditionSql);
			for (ExternalRepairShop item : list) {
				externalRepairShopRepository.delete(item.getShop_id());
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void deleteExternalRepairRecords(String conditionSql) {
		try {
			List<ExternalRepairRecord> list = externalRepairRecordRepository.findByCondition(conditionSql);
			for (ExternalRepairRecord item : list) {
				externalRepairRecordRepository.delete(item.getExternal_repair_id());
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void deleteInternalRepairRecords(String conditionSql) {
		try {
			List<InternalRepairRecord> list = internalRepairRecordRepository.findByCondition(conditionSql);
			for (InternalRepairRecord item : list) {
				internalRepairRecordRepository.delete(item.getInternal_repair_id());
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void deleteParts(String conditionSql) {
		try {
			List<Part> list = partRepository.findByCondition(conditionSql);
			for (Part item : list) {
				partRepository.delete(item.getPart_id());
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	public void deleteRentals(String conditionSql) {
		try {
			List<Rental> list = rentalRepository.findByCondition(conditionSql);
			for (Rental item : list) {
				rentalRepository.delete(item.getRental_id());
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

}
