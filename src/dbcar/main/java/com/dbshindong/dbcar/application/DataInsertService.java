package dbcar.main.java.com.dbshindong.dbcar.application;

import java.sql.Date;

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

public class DataInsertService {
	private final CampingCarCompanyRepository campingCarCompanyRepository;
	private final ExternalRepairShopRepository externalRepairShopRepository;
	private final RentalRepository rentalRepository;
	private final CampingCarRepository campingCarRepository;
	private final ExternalRepairRecordRepository externalRepairRecordRepository;

	private final CustomerRepository customerRepository;
	private final PartRepository partRepository;
	private final EmployeeRepository employeeRepository;
	private final InternalRepairRecordRepository internalRepairRecordRepository;

	public DataInsertService(CampingCarCompanyRepository campingCarCompanyRepository,
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

	public CampingCarCompany createCampingCarCompany(String name, String address, String phone, String manager_name,
			String manager_email) {
		CampingCarCompany company = new CampingCarCompany(name, address, phone, manager_name, manager_email);
		return company;
	}

	public void insertCampingCarCompany(CampingCarCompany company) {
		campingCarCompanyRepository.save(company);
	}

	public CampingCar createCampingCar(String name, String plate_number, int capacity, byte[] image, String description,
			int rental_price, int company_id, String registered_date) {
		CampingCar car = new CampingCar(name, plate_number, capacity, image, description, rental_price, company_id,
				registered_date);
		return car;
	}

	public void insertCampingCar(CampingCar car) {
		campingCarRepository.save(car);
	}

	public Employee createEmployee(String name, String phone, String address, int salary, int dependents,
			String department, String role) {
		Employee employee = new Employee(name, phone, address, salary, dependents, department, role);
		return employee;
	}

	public void insertEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	public Customer createCustomer(String username, String password, String license_number, String name, String address,
			String phone, String email) {
		Customer customer = new Customer(username, password, license_number, name, address, phone, email);
		return customer;

	}

	public void insertCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	public Rental createRental(int car_id, int customer_id, int company_id, String start_date, int rental_period,
			int total_charge, String due_date, String extra_charge_detail, Integer extra_charge) {
		Rental rental = new Rental(car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date,
				extra_charge_detail, extra_charge);
		return rental;

	}

	public void insertRental(Rental rental) throws Exception {
		rentalRepository.save(rental);
	}

	public ExternalRepairRecord createExternalRepairRecord(int car_id, int shop_id, int company_id, int customer_id,
			String content, String repair_date, int cost, String due_date, String note) {
		ExternalRepairRecord record = new ExternalRepairRecord(car_id, shop_id, company_id, customer_id, content,
				repair_date, cost, due_date, note);
		return record;

	}

	public void insertExternalRepairRecord(ExternalRepairRecord record) {
		externalRepairRecordRepository.save(record);
	}

	public ExternalRepairShop createExternalRepairShop(String name, String address, String phone, String manager_name,
			String manager_email) {

		ExternalRepairShop shop = new ExternalRepairShop(name, address, phone, manager_name, manager_email);
		return shop;

	}

	public void insertExternalRepairShop(ExternalRepairShop shop) {
		externalRepairShopRepository.save(shop);
	}

	public InternalRepairRecord creatInternalRepairRecord(int car_id, Integer part_id, String repair_date,
			int duration_minutes, int employee_id) {
		InternalRepairRecord record = new InternalRepairRecord(car_id, part_id, repair_date, duration_minutes,
				employee_id);
		return record;

	}

	public void insertInternalRepairRecord(InternalRepairRecord record) {
		internalRepairRecordRepository.save(record);
	}

	public Part createPart(String name, int unit_price, int stock_quantity, String stock_date, String supplier_name) {
		Part part = new Part(name, unit_price, stock_quantity, stock_date, supplier_name);
		return part;
	}

	public void insertPart(Part part) {
		partRepository.save(part);
	}

}
