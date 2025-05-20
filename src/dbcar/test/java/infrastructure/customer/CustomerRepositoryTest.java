package dbcar.test.java.infrastructure.customer;

import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import java.util.*;

public class CustomerRepositoryTest {
	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static CustomerRepository customerRepository;
	public static void main(String[] args) {
		System.out.println("[[CustomerRepositoryTest 초기 팅]]");
		
		dc = new DBConnection("root", "1234");
		databaseInitService = new DatabaseInitService();
		customerRepository = new CustomerRepository(DBConnection.getConnection());
		databaseInitService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");

		System.out.println("\n[[CustomerRepositoryTest 시작]]");
		
		// findAll Test
		전체_데이터_조회가_되어야_한다();
		
		// findById Test
		사용자의_아이디로_조회가_되어야_한다();
		
		// delete Test
		사용자의_아이디로_데이터가_삭제_되어야_한다();
		
		// save Test
		
		// update Test
		
		System.out.println("\n[[CustomerRepositoryTest 완료]]");
	}
	
	
	private static void 전체_데이터_조회가_되어야_한다() {
		List<Customer> customers = customerRepository.findAll();
		AssertUtil.assertEqual(12, customers.size(), "전체 데이터가 조회되어야 한다.");
	}
	
	private static void 사용자의_아이디로_조회가_되어야_한다() {
		Customer customer = customerRepository.findById(1);
		AssertUtil.assertEqual("임현우", customer.getName(), "사용자의 아이디로 조회가 되어야 한다.");
	}
	
	private static void 사용자의_아이디로_데이터가_삭제_되어야_한다() {
		// when
		customerRepository.delete(1);
		Customer customer = customerRepository.findById(1);
		
		// then
		AssertUtil.assertEqual(null, customer, "사용자의 아이디로 데이터가 삭제 되어야 한다.");
	}
}
