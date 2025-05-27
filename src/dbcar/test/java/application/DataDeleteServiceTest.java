package dbcar.test.java.application;

import dbcar.main.java.com.dbshindong.dbcar.application.DataDeleteService;
import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;

import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.*;

public class DataDeleteServiceTest {
	static AppConfig ac = AppConfig.getInstance();

	public static void main(String[] args) {
		System.out.println("[[DataDeleteServiceTest 시작]]");

		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");

		DataDeleteService deleteService = ac.dataDeleteService();

		deleteCampingCar테스트(deleteService);
		deleteCampingCarCompany테스트(deleteService);
		deleteEmployee테스트(deleteService);
		deleteCustomer테스트(deleteService);
		deleteExternalRepairShop테스트(deleteService);
		deleteExternalRepairRecord테스트(deleteService);
		deleteInternalRepairRecord테스트(deleteService);
		deletePart테스트(deleteService);
		deleteRental테스트(deleteService);
	}

	private static void deleteCampingCar테스트(DataDeleteService service) {
		service.deleteCampingCars("car_id = 1");
		CampingCarRepository repo = ac.campingCarRepository();
		AssertUtil.assertNull(repo.findById(1), "캠핑카가 삭제되어야 한다.");
	}

	private static void deleteCampingCarCompany테스트(DataDeleteService service) {
		service.deleteCampingCarCompanys("company_id = 2");
		CampingCarCompanyRepository repo = ac.campingCarCompanyRepository();
		AssertUtil.assertNull(repo.findById(2), "회사 정보가 삭제되어야 한다.");
	}

	private static void deleteEmployee테스트(DataDeleteService service) {
		service.deleteEmployees("employee_id = 1");
		EmployeeRepository repo = ac.employeeRepository();
		AssertUtil.assertNull(repo.findById(1), "직원 정보가 삭제되어야 한다.");
	}

	private static void deleteCustomer테스트(DataDeleteService service) {
		service.deleteCustomers("customer_id = 1");
		CustomerRepository repo = ac.customerRepository();
		AssertUtil.assertNull(repo.findById(1), "고객 정보가 삭제되어야 한다.");
	}

	private static void deleteExternalRepairShop테스트(DataDeleteService service) {
		service.deleteExternalRepairShops("shop_id = 1");
		ExternalRepairShopRepository repo = ac.externalRepairShopRepository();
		AssertUtil.assertNull(repo.findById(1), "외부 정비소가 삭제되어야 한다.");
	}

	private static void deleteExternalRepairRecord테스트(DataDeleteService service) {
		service.deleteExternalRepairRecords("external_repair_id = 1");
		ExternalRepairRecordRepository repo = ac.externalRepairRecordRepository();
		AssertUtil.assertNull(repo.findById(1), "외부 정비 기록이 삭제되어야 한다.");
	}

	private static void deleteInternalRepairRecord테스트(DataDeleteService service) {
		service.deleteInternalRepairRecords("internal_repair_id = 1");
		InternalRepairRecordRepository repo = ac.internalRepairRecordRepository();
		AssertUtil.assertNull(repo.findById(1), "내부 정비 기록이 삭제되어야 한다.");
	}

	private static void deletePart테스트(DataDeleteService service) {
		service.deleteParts("part_id = 1");
		PartRepository repo = ac.partRepository();
		AssertUtil.assertNull(repo.findById(1), "부품 정보가 삭제되어야 한다.");
	}

	private static void deleteRental테스트(DataDeleteService service) {
		service.deleteRentals("rental_id = 1");
		RentalRepository repo = ac.rentalRepository();
		AssertUtil.assertNull(repo.findById(1), "렌탈 정보가 삭제되어야 한다.");
	}
}