package dbcar.main.java.com.dbshindong.dbcar.application;

import java.sql.Date;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateCampingCarCompanyRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateCampingCarRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateCustomerRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateEmployeeRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateExternalRepairRecordRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateExternalRepairShopRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateInternalRepairRecordRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdatePartRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateRentalRequest;
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
	private final ExternalRepairShopRepository externalRepairShopRepository;
	private final RentalRepository rentalRepository;
	private final CampingCarRepository campingCarRepository;
	private final ExternalRepairRecordRepository externalRepairRecordRepository;
	private final CustomerRepository customerRepository;
	private final PartRepository partRepository;
	private final EmployeeRepository employeeRepository;
	private final InternalRepairRecordRepository internalRepairRecordRepository;

	public DataUpdateService(CampingCarCompanyRepository campingCarCompanyRepository,
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

	public void updateCampingCars(UpdateCampingCarRequest request, String conditionSql) {
		try {
			List<CampingCar> cars = campingCarRepository.findByCondition(conditionSql);
			for (CampingCar car : cars) {
				CampingCar updateCar = createUpdateCampingCar(car, request);
				campingCarRepository.update(car.getCar_id(), updateCar);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	private CampingCar createUpdateCampingCar(CampingCar car, UpdateCampingCarRequest request) {
		String name = request.name() == null ? car.getName() : request.name();
		String plate_number = request.plate_number() == null ? car.getPlate_number() : request.plate_number();
		Integer capacity = request.capacity() == null ? car.getCapacity() : request.capacity();
		byte[] image = request.image() == null ? car.getImage() : request.image();
		String description = request.description() == null ? car.getDescription() : request.description();
		Integer rental_price = request.rental_price() == null ? car.getRental_price() : request.rental_price();
		Integer company_id = request.company_id() == null ? car.getCompany_id() : request.company_id();
		Date registered_date = request.registered_date() == null ? car.getRegistered_date() : request.registered_date();

		return new CampingCar(name, plate_number, capacity, image, description, rental_price, company_id,
				registered_date);
	}

	public void updateCampingCarCompanies(UpdateCampingCarCompanyRequest request, String conditionSql) {
		try {
			List<CampingCarCompany> list = campingCarCompanyRepository.findByCondition(conditionSql);
			for (CampingCarCompany item : list) {
				CampingCarCompany updateCompany = createUpdateCampingCarCompany(item, request);
				campingCarCompanyRepository.update(item.getCompany_id(), updateCompany);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	private CampingCarCompany createUpdateCampingCarCompany(CampingCarCompany entity,
			UpdateCampingCarCompanyRequest request) {
		String name = request.name() == null ? entity.getName() : request.name();
		String address = request.address() == null ? entity.getAddress() : request.address();
		String phone = request.phone() == null ? entity.getPhone() : request.phone();
		String manager_name = request.manager_name() == null ? entity.getManager_name() : request.manager_name();
		String manager_email = request.manager_email() == null ? entity.getManager_email() : request.manager_email();
		return new CampingCarCompany(name, address, phone, manager_name, manager_email);
	}

	public void updateCustomers(UpdateCustomerRequest request, String conditionSql) {
		try {
			List<Customer> list = customerRepository.findByCondition(conditionSql);
			for (Customer item : list) {
				Customer updateCustomer = createUpdateCustomer(item, request);
				customerRepository.update(item.getCustomer_id(), updateCustomer);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	private Customer createUpdateCustomer(Customer entity, UpdateCustomerRequest request) {
		String username = request.username() == null ? entity.getUsername() : request.username();
		String password = request.password() == null ? entity.getPassword() : request.password();
		String license_number = request.license_number() == null ? entity.getLicense_number()
				: request.license_number();
		String name = request.name() == null ? entity.getName() : request.name();
		String address = request.address() == null ? entity.getAddress() : request.address();
		String phone = request.phone() == null ? entity.getPhone() : request.phone();
		String email = request.email() == null ? entity.getEmail() : request.email();
		return new Customer(username, password, license_number, name, address, phone, email);
	}

	public void updateEmployees(UpdateEmployeeRequest request, String conditionSql) {
		try {
			List<Employee> list = employeeRepository.findByCondition(conditionSql);
			for (Employee item : list) {
				Employee updateEmployee = createUpdateEmployee(item, request);
				employeeRepository.update(item.getEmployee_id(), updateEmployee);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	private Employee createUpdateEmployee(Employee entity, UpdateEmployeeRequest request) {
		String name = request.name() == null ? entity.getName() : request.name();
		String phone = request.phone() == null ? entity.getPhone() : request.phone();
		String address = request.address() == null ? entity.getAddress() : request.address();
		Integer salary = request.salary() == null ? entity.getSalary() : request.salary();
		Integer dependents = request.dependents() == null ? entity.getDependents() : request.dependents();
		String department = request.department() == null ? entity.getDepartment() : request.department();
		String role = request.role() == null ? entity.getRole() : request.role();
		return new Employee(name, phone, address, salary, dependents, department, role);
	}

	public void updateExternalRepairShops(UpdateExternalRepairShopRequest request, String conditionSql) {
		try {
			List<ExternalRepairShop> list = externalRepairShopRepository.findByCondition(conditionSql);
			for (ExternalRepairShop item : list) {
				ExternalRepairShop updateShop = createUpdateExternalRepairShop(item, request);
				externalRepairShopRepository.update(item.getShop_id(), updateShop);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	private ExternalRepairShop createUpdateExternalRepairShop(ExternalRepairShop entity,
			UpdateExternalRepairShopRequest request) {
		String name = request.name() == null ? entity.getName() : request.name();
		String address = request.address() == null ? entity.getAddress() : request.address();
		String phone = request.phone() == null ? entity.getPhone() : request.phone();
		String manager_name = request.manager_name() == null ? entity.getManager_name() : request.manager_name();
		String manager_email = request.manager_email() == null ? entity.getManager_email() : request.manager_email();
		return new ExternalRepairShop(name, address, phone, manager_name, manager_email);
	}

	public void updateExternalRepairRecords(UpdateExternalRepairRecordRequest request, String conditionSql) {
		try {
			List<ExternalRepairRecord> list = externalRepairRecordRepository.findByCondition(conditionSql);
			for (ExternalRepairRecord item : list) {
				ExternalRepairRecord updateRecord = createUpdateExternalRepairRecord(item, request);
				externalRepairRecordRepository.update(item.getExternal_repair_id(), updateRecord);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	private ExternalRepairRecord createUpdateExternalRepairRecord(ExternalRepairRecord entity,
			UpdateExternalRepairRecordRequest request) {
		Integer car_id = request.car_id() == null ? entity.getCar_id() : request.car_id();
		Integer shop_id = request.shop_id() == null ? entity.getShop_id() : request.shop_id();
		Integer company_id = request.company_id() == null ? entity.getCompany_id() : request.company_id();
		Integer customer_id = request.customer_id() == null ? entity.getCustomer_id() : request.customer_id();
		String content = request.content() == null ? entity.getContent() : request.content();
		Date repair_date = request.repair_date() == null ? entity.getRepair_date() : request.repair_date();
		Integer cost = request.cost() == null ? entity.getCost() : request.cost();
		Date due_date = request.due_date() == null ? entity.getDue_date() : request.due_date();
		String note = request.note() == null ? entity.getNote() : request.note();
		return new ExternalRepairRecord(car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date,
				note);
	}

	public void updateInternalRepairRecords(UpdateInternalRepairRecordRequest request, String conditionSql) {
		try {
			List<InternalRepairRecord> list = internalRepairRecordRepository.findByCondition(conditionSql);
			for (InternalRepairRecord item : list) {
				InternalRepairRecord updateRecord = createUpdateInternalRepairRecord(item, request);
				internalRepairRecordRepository.update(item.getInternal_repair_id(), updateRecord);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	private InternalRepairRecord createUpdateInternalRepairRecord(InternalRepairRecord entity,
			UpdateInternalRepairRecordRequest request) {
		Integer car_id = request.car_id() == null ? entity.getCar_id() : request.car_id();
		Integer part_id = request.part_id() == null ? entity.getPart_id() : request.part_id();
		Date repair_date = request.repair_date() == null ? entity.getRepair_date() : request.repair_date();
		Integer duration_minutes = request.duration_minutes() == null ? entity.getDuration_minutes()
				: request.duration_minutes();
		Integer employee_id = request.employee_id() == null ? entity.getEmployee_id() : request.employee_id();
		return new InternalRepairRecord(car_id, part_id, repair_date, duration_minutes, employee_id);
	}

	public void updateParts(UpdatePartRequest request, String conditionSql) {
		try {
			List<Part> list = partRepository.findByCondition(conditionSql);
			for (Part item : list) {
				Part updatePart = createUpdatePart(item, request);
				partRepository.update(item.getPart_id(), updatePart);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	private Part createUpdatePart(Part entity, UpdatePartRequest request) {
		String name = request.name() == null ? entity.getName() : request.name();
		Integer unit_price = request.unit_price() == null ? entity.getUnit_price() : request.unit_price();
		Integer stock_quantity = request.stock_quantity() == null ? entity.getStock_quantity()
				: request.stock_quantity();
		Date stock_date = request.stock_date() == null ? entity.getStock_date() : request.stock_date();
		String supplier_name = request.supplier_name() == null ? entity.getSupplier_name() : request.supplier_name();
		return new Part(name, unit_price, stock_quantity, stock_date, supplier_name);
	}

	public void updateRentals(UpdateRentalRequest request, String conditionSql) {
		try {
			List<Rental> list = rentalRepository.findByCondition(conditionSql);
			for (Rental item : list) {
				Rental updateRental = createUpdateRental(item, request);
				rentalRepository.update(item.getRental_id(), updateRental);
			}
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}
	}

	private Rental createUpdateRental(Rental entity, UpdateRentalRequest request) {
		Integer car_id = request.car_id() == null ? entity.getCar_id() : request.car_id();
		Integer customer_id = request.customer_id() == null ? entity.getCustomer_id() : request.customer_id();
		Integer company_id = request.company_id() == null ? entity.getCompany_id() : request.company_id();
		Date start_date = request.start_date() == null ? entity.getStart_date() : request.start_date();
		Integer rental_period = request.rental_period() == null ? entity.getRental_period() : request.rental_period();
		Integer total_charge = request.total_charge() == null ? entity.getTotal_charge() : request.total_charge();
		Date due_date = request.due_date() == null ? entity.getDue_date() : request.due_date();
		String extra_charges = request.extra_charge_detail() == null ? entity.getExtra_charge_detail()
				: request.extra_charge_detail();
		Integer extra_charge = request.extra_charge() == null ? entity.getExtra_charge() : request.extra_charge();
		return new Rental(car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date,
				extra_charges, extra_charge);
	}

}
