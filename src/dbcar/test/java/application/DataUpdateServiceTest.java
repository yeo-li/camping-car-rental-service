package dbcar.test.java.application;

import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateCampingCarCompanyRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateCampingCarRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateCustomerRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateEmployeeRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateExternalRepairRecordRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateExternalRepairShopRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateInternalRepairRecordRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdatePartRequest;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.UpdateRentalRequest;
import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
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
import dbcar.main.java.com.dbshindong.dbcar.application.DataUpdateService;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairShopRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.InternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;

public class DataUpdateServiceTest {
	static AppConfig ac = AppConfig.getInstance();

	public static void main(String[] args) {
		System.out.println("[[DataUpdateServiceTest 시작]]");

		// 초기화
		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");

		DataUpdateService updateService = ac.dataUpdateService();

		updateCampingCar테스트(updateService);
		updateCampingCarCompany테스트(updateService);
		updateEmployee테스트(updateService);
		updateCustomer테스트(updateService);
		updateExternalRepairShop테스트(updateService);
		updateExternalRepairRecord테스트(updateService);
		updateInternalRepairRecord테스트(updateService);
		updatePart테스트(updateService);
		updateRental테스트(updateService);
	}

	private static void updateCampingCar테스트(DataUpdateService service) {
		UpdateCampingCarRequest updateCar = new UpdateCampingCarRequest("수정된 캠핑카", "서울99가9999", 4,
				new byte[] { (byte) 0xFF, (byte) 0xD8 }, "수정 설명", 123456, 1, "2025-01-01");

		service.updateCampingCars(updateCar, "car_id = 1");

		CampingCarRepository repo = ac.campingCarRepository();
		CampingCar car = repo.findById(1);
		AssertUtil.assertEqual("수정된 캠핑카", car.getName(), "캠핑카 이름이 수정되어야 한다.");
		AssertUtil.assertEqual("서울99가9999", car.getPlate_number(), "차량 번호판이 수정되어야 한다.");
		AssertUtil.assertEqual("수정 설명", car.getDescription(), "설명이 수정되어야 한다.");
	}

	private static void updateCampingCarCompany테스트(DataUpdateService service) {
		UpdateCampingCarCompanyRequest updatedCompany = new UpdateCampingCarCompanyRequest("수정회사", "부산시 캠핑로 99",
				"010-0000-0000", "홍팀장", "boss@update.com");

		service.updateCampingCarCompanies(updatedCompany, "company_id = 1");

		CampingCarCompanyRepository repo = ac.campingCarCompanyRepository();
		CampingCarCompany company = repo.findById(1);
		AssertUtil.assertEqual("수정회사", company.getName(), "회사 이름이 수정되어야 한다.");
		AssertUtil.assertEqual("홍팀장", company.getManager_name(), "매니저 이름이 수정되어야 한다.");
	}

	private static void updateEmployee테스트(DataUpdateService service) {
		UpdateEmployeeRequest updateEmp = new UpdateEmployeeRequest("수정된직원", "010-1111-2222", "서울시 수정구 999", 7000000, 1,
				"연구개발팀", "기획");

		service.updateEmployees(updateEmp, "employee_id = 1");

		EmployeeRepository repo = ac.employeeRepository();
		Employee emp = repo.findById(1);
		AssertUtil.assertEqual("수정된직원", emp.getName(), "직원 이름이 수정되어야 한다.");
		AssertUtil.assertEqual("010-1111-2222", emp.getPhone(), "직원 전화번호가 수정되어야 한다.");
		AssertUtil.assertEqual("기획", emp.getRole(), "직원 역할이 수정되어야 한다.");
	}

	private static void updateCustomer테스트(DataUpdateService service) {
		UpdateCustomerRequest updateCustomer = new UpdateCustomerRequest("updatedUser", "newPw", "L999999", "홍길동",
				"서울시 성동구 수정길 123", "010-1234-5678", "hong@example.com");

		service.updateCustomers(updateCustomer, "customer_id = 1");

		CustomerRepository repo = ac.customerRepository();
		Customer customer = repo.findById(1);
		AssertUtil.assertEqual("updatedUser", customer.getUsername(), "사용자 이름이 수정되어야 한다.");
		AssertUtil.assertEqual("홍길동", customer.getName(), "이름이 수정되어야 한다.");
	}

	private static void updateExternalRepairShop테스트(DataUpdateService service) {
		UpdateExternalRepairShopRequest updated = new UpdateExternalRepairShopRequest("수정정비소", "경기도 수원시 고등동",
				"010-8888-8888", "정수리", "repair@fix.com");

		service.updateExternalRepairShops(updated, "shop_id = 1");

		ExternalRepairShopRepository repo = ac.externalRepairShopRepository();
		ExternalRepairShop shop = repo.findById(1);
		AssertUtil.assertEqual("수정정비소", shop.getName(), "정비소 이름이 수정되어야 한다.");
		AssertUtil.assertEqual("정수리", shop.getManager_name(), "매니저 이름이 수정되어야 한다.");
	}

	private static void updateExternalRepairRecord테스트(DataUpdateService service) {
		UpdateExternalRepairRecordRequest updated = new UpdateExternalRepairRecordRequest(1, 1, 1, 1, "수정내용",
				"2025-01-01", 123456, "2025-02-01", "메모");

		service.updateExternalRepairRecords(updated, "external_repair_id = 1");

		ExternalRepairRecordRepository repo = ac.externalRepairRecordRepository();
		ExternalRepairRecord record = repo.findById(1);
		AssertUtil.assertEqual("수정내용", record.getContent(), "수리 내용이 수정되어야 한다.");
		AssertUtil.assertEqual("메모", record.getNote(), "메모가 수정되어야 한다.");
	}

	private static void updateInternalRepairRecord테스트(DataUpdateService service) {
		UpdateInternalRepairRecordRequest updated = new UpdateInternalRepairRecordRequest(1, 1, "2025-01-01", 120, 1);

		service.updateInternalRepairRecords(updated, "internal_repair_id = 1");

		InternalRepairRecordRepository repo = ac.internalRepairRecordRepository();
		InternalRepairRecord record = repo.findById(1);
		AssertUtil.assertEqual(120, record.getDuration_minutes(), "수리 시간이 수정되어야 한다.");
	}

	private static void updatePart테스트(DataUpdateService service) {
		UpdatePartRequest updated = new UpdatePartRequest("수정부품", 50000, 10, "2025-01-01", "부품상사999");

		service.updateParts(updated, "part_id = 1");

		PartRepository repo = ac.partRepository();
		Part part = repo.findById(1);
		AssertUtil.assertEqual("수정부품", part.getName(), "부품 이름이 수정되어야 한다.");
	}

	private static void updateRental테스트(DataUpdateService service) {
		UpdateRentalRequest updated = new UpdateRentalRequest(1, 1, 1, "2025-01-01", 7, 700000, "2025-01-08", "기타비용",
				10000);

		service.updateRentals(updated, "rental_id = 1");

		RentalRepository repo = ac.rentalRepository();
		Rental rental = repo.findById(1);
		AssertUtil.assertEqual("기타비용", rental.getExtra_charge_detail(), "추가 요금 세부사이 수정되어야 한다.");
		AssertUtil.assertEqual(700000, rental.getTotal_charge(), "총 요금이 수정되어야 한다.");
	}
}
