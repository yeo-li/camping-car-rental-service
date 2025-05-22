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
		System.out.println("[[CustomerRepositoryTest 초기 세팅]]");

		dc = new DBConnection("root", "1234");
		databaseInitService = new DatabaseInitService();
		customerRepository = new CustomerRepository(DBConnection.getConnection());
		databaseInitService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");

		System.out.println("\n[[CustomerRepositoryTest]]");

		// findAll Test
		전체_데이터_조회가_되어야_한다();

		// findById Test
		사용자의_아이디로_조회가_되어야_한다();

		// delete Test
		사용자의_아이디로_데이터가_삭제_되어야_한다();

		// save Test
		새로운_사용자를_저장할_수_있어야_한다();

		// update Test
		데이터를_업데이트_할_수_있어야_한다();

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

	private static void 새로운_사용자를_저장할_수_있어야_한다() {
		// given
		Customer customer = new Customer("yeoli", "3014", "L134233212", "박성열", "광진구 천호대로", "010-3075-3014",
				"uio6699@naver.com");

		// when
		customerRepository.save(customer);

		// then
		Customer actual = customerRepository.findById(13);
		AssertUtil.assertEqual("박성열", actual.getName(), "새로운 사용자를 저장할 수 있어야 한다.");
	}

	private static void 데이터를_업데이트_할_수_있어야_한다() {
		// given
		Customer cs = customerRepository.findById(13);
		Customer updateCust = new Customer("kimSeonWoo", cs.getPassword(), cs.getLicense_number(), "김선우",
				cs.getAddress(), cs.getPhone(), cs.getEmail());

		// when
		customerRepository.update(13, updateCust);

		// then
		Customer customer = customerRepository.findById(13);
		AssertUtil.assertEqual("kimSeonWoo", customer.getUsername(), "데이터를 업데이트 할 수 있어야 한다.");
	}
}
