package dbcar.test.java.application;

import dbcar.main.java.com.dbshindong.dbcar.application.DataDeleteService;
import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.*;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.*;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.*;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.*;

import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.*;

public class DataDeleteServiceTest {

    public static void main(String[] args) {
        System.out.println("[[DataDeleteServiceTest 시작]]");

        DBConnection dc = new DBConnection("root", "1234");
        DatabaseInitService initService = new DatabaseInitService();
        initService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");

        DataDeleteService deleteService = new DataDeleteService(
            new CampingCarCompanyRepository(DBConnection.getConnection()),
            new ExternalRepairShopRepository(DBConnection.getConnection()),
            new RentalRepository(DBConnection.getConnection()),
            new CampingCarRepository(DBConnection.getConnection()),
            new ExternalRepairRecordRepository(DBConnection.getConnection()),
            new CustomerRepository(DBConnection.getConnection()),
            new PartRepository(DBConnection.getConnection()),
            new EmployeeRepository(DBConnection.getConnection()),
            new InternalRepairRecordRepository(DBConnection.getConnection())
        );

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
        CampingCarRepository repo = new CampingCarRepository(DBConnection.getConnection());
        AssertUtil.assertNull(repo.findById(1), "캠핑카가 삭제되어야 한다.");
    }

    private static void deleteCampingCarCompany테스트(DataDeleteService service) {
        service.deleteCampingCarCompanys("company_id = 2");
        CampingCarCompanyRepository repo = new CampingCarCompanyRepository(DBConnection.getConnection());
        AssertUtil.assertNull(repo.findById(2), "회사 정보가 삭제되어야 한다.");
    }

    private static void deleteEmployee테스트(DataDeleteService service) {
        service.deleteEmployees("employee_id = 1");
        EmployeeRepository repo = new EmployeeRepository(DBConnection.getConnection());
        AssertUtil.assertNull(repo.findById(1), "직원 정보가 삭제되어야 한다.");
    }

    private static void deleteCustomer테스트(DataDeleteService service) {
        service.deleteCustomers("customer_id = 1");
        CustomerRepository repo = new CustomerRepository(DBConnection.getConnection());
        AssertUtil.assertNull(repo.findById(1), "고객 정보가 삭제되어야 한다.");
    }

    private static void deleteExternalRepairShop테스트(DataDeleteService service) {
        service.deleteExternalRepairShops("shop_id = 1");
        ExternalRepairShopRepository repo = new ExternalRepairShopRepository(DBConnection.getConnection());
        AssertUtil.assertNull(repo.findById(1), "외부 정비소가 삭제되어야 한다.");
    }

    private static void deleteExternalRepairRecord테스트(DataDeleteService service) {
        service.deleteExternalRepairRecords("external_repair_id = 1");
        ExternalRepairRecordRepository repo = new ExternalRepairRecordRepository(DBConnection.getConnection());
        AssertUtil.assertNull(repo.findById(1), "외부 정비 기록이 삭제되어야 한다.");
    }

    private static void deleteInternalRepairRecord테스트(DataDeleteService service) {
        service.deleteInternalRepairRecords("internal_repair_id = 1");
        InternalRepairRecordRepository repo = new InternalRepairRecordRepository(DBConnection.getConnection());
        AssertUtil.assertNull(repo.findById(1), "내부 정비 기록이 삭제되어야 한다.");
    }

    private static void deletePart테스트(DataDeleteService service) {
        service.deleteParts("part_id = 1");
        PartRepository repo = new PartRepository(DBConnection.getConnection());
        AssertUtil.assertNull(repo.findById(1), "부품 정보가 삭제되어야 한다.");
    }

    private static void deleteRental테스트(DataDeleteService service) {
        service.deleteRentals("rental_id = 1");
        RentalRepository repo = new RentalRepository(DBConnection.getConnection());
        AssertUtil.assertNull(repo.findById(1), "렌탈 정보가 삭제되어야 한다.");
    }
}