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
		System.out.println("[[CustomerRepositoryTest 초기 셋팅]]");
		
		dc = new DBConnection("root", "1234");
		databaseInitService = new DatabaseInitService();
		customerRepository = new CustomerRepository(DBConnection.getConnection());
		databaseInitService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");

		System.out.println("\n[[CustomerRepositoryTest 시작]]");
		
		// findAll Test
		전체_데이터_조회가_되어야_한다();
		
		// findById Test
		// 사용자의_아이디로_조회가_되어야_한다();
		
		// delete Test
		
		// save Test
		
		// update Test
		
		System.out.println("\n[[CustomerRepositoryTest 완료]]");
	}
	
	
	private static void 전체_데이터_조회가_되어야_한다() {
		List<Customer> customers = customerRepository.findAll();
		AssertUtil.assertEqual(12, customers.size(), "전체 데이터가 조회되어야 한다.");
	}
}
