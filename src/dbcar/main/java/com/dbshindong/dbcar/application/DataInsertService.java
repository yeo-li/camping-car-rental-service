package dbcar.main.java.com.dbshindong.dbcar.application;

import java.sql.Date;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCarCompany;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.Employee;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarCompanyRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.EmployeeRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairShopRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.InternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;

public class DataInsertService {
	private final CampingCarCompanyRepository campingCarCampanyRepository;
	private final ExternalRepairShopRepository externalRepairShopRepository;
	private final RentalRepository rentalRepository;
	private final CampingCarRepository campingCarRepository;
	private final ExternalRepairRecordRepository externalRepairRecordRepository;
	private final CustomerRepository customerRepository;
	private final PartRepository partRepository;
	private final EmployeeRepository employeeRepository;
	private final InternalRepairRecordRepository internalRepairRecordRepository;

	public DataInsertService(CampingCarCompanyRepository campingCarCampanyRepository,
			ExternalRepairShopRepository externalRepairShopRepository, RentalRepository rentalRepository,
			CampingCarRepository campingCarRepository, ExternalRepairRecordRepository externalRepairRecordRepository,
			CustomerRepository customerRepository, PartRepository partRepository, EmployeeRepository employeeRepository,
			InternalRepairRecordRepository internalRepairRecordRepository) {
		super();
		this.campingCarCampanyRepository = campingCarCampanyRepository;
		this.externalRepairShopRepository = externalRepairShopRepository;
		this.rentalRepository = rentalRepository;
		this.campingCarRepository = campingCarRepository;
		this.externalRepairRecordRepository = externalRepairRecordRepository;
		this.customerRepository = customerRepository;
		this.partRepository = partRepository;
		this.employeeRepository = employeeRepository;
		this.internalRepairRecordRepository = internalRepairRecordRepository;
	}

	public CampingCarCompany createCampingCarCompany(String name, String address, String phone, String manager_name,
			String manager_email) {
		try {
			CampingCarCompany company = new CampingCarCompany(name, address, phone, manager_name, manager_email);
			return company;
		} catch (NullPointerException e) {
			System.out.println("[error]CampingCarCompany의 입력값이 올바르지 않습니다.");
			return null;
		}
	}

	public void insertCampingCarCompany(CampingCarCompany company) {
		campingCarCampanyRepository.save(company);
	}

	public CampingCar creatCampingCar(String name, String plate_number, int capacity, byte[] image, String description,
			int rental_price, int company_id, Date registered_date) {
		try {
			CampingCar car = new CampingCar(name, plate_number, capacity, image, description, rental_price, company_id,
					registered_date);
			return car;
		} catch (NullPointerException e) {
			System.out.println("[error]CampingCar의 입력값이 올바르지 않습니다.");
			return null;
		}
	}

	public void insertCampingCar(CampingCar car) {
		campingCarRepository.save(car);
	}

	public Employee creatEmployee(String name, String phone, String address, int salary, int dependents,
			String department, String role) {
		try {
			Employee employee = new Employee(name, phone, address, salary, dependents, department, role);
			return employee;
		} catch (NullPointerException e) {
			System.out.println("[error]Employee의 입력값이 올바르지 않습니다.");
			return null;
		}
	}

	public void insertEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	public Customer creatCustomer(String username, String password, String license_number, String name, String address,
			String phone, String email) {
		try {
			Customer customer = new Customer(username, password, license_number, name, address, phone, email);
			return customer;
		} catch (NullPointerException e) {
			System.out.println("[error]Customer의 입력값이 올바르지 않습니다.");
			return null;
		}
	}

	public void insertCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public Rental creatRental(int car_id, int customer_id, int company_id, Date start_date, int rental_period,
			int total_charge, Date due_date, String extra_charge_detail, Integer extra_charge) {
		try {
			Rental rental = new Rental(car_id, customer_id, company_id, start_date, rental_period, total_charge,
					due_date, extra_charge_detail, extra_charge);
			return rental;
		} catch (NullPointerException e) {
			System.out.println("[error]Rental의 입력값이 올바르지 않습니다.");
			return null;
		}
	}

	public void insertRental(Rental rental) {
		rentalRepository.save(rental);
	}

	public ExternalRepairRecord creatExternalRepairRecord(int car_id, int shop_id, int company_id, int customer_id,
			String content, Date repair_date, int cost, Date due_date, String note) {
		try {
			ExternalRepairRecord record = new ExternalRepairRecord(car_id, shop_id, company_id, customer_id, content,
					repair_date, cost, due_date, note);
			return record;
		} catch (NullPointerException e) {
			System.out.println("[error]ExternalRepairRecord의 입력값이 올바르지 않습니다.");
			return null;
		}
	}

	public void insertExternalRepairRecord(ExternalRepairRecord record) {
		externalRepairRecordRepository.save(record);
	}

	public ExternalRepairShop creatExternalRepairShop(String name, String address, String phone, String manager_name,
			String manager_email) {
		try {
			ExternalRepairShop shop = new ExternalRepairShop(name, address, phone, manager_name, manager_email);
			return shop;
		} catch (NullPointerException e) {
			System.out.println("[error]ExternalRepairShop의 입력값이 올바르지 않습니다.");
			return null;
		}
	}

	public void insertExternalRepairShop(ExternalRepairShop shop) {
		externalRepairShopRepository.save(shop);
	}

}
