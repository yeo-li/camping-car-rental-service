package dbcar.test.java.infrastructure.company;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.Employee;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.EmployeeRepository;

public class EmployeeRepositoryTest {
	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static EmployeeRepository EmployeeRepository;

	public static void main(String[] args) {
		System.out.println("[[EmployeeRepositoryTest 초기 세팅]]");

		dc = new DBConnection("root", "1234");
		databaseInitService = new DatabaseInitService();
		EmployeeRepository = new EmployeeRepository(DBConnection.getConnection());
		databaseInitService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");

		System.out.println("\n[[EmployeeRepositoryTest]]");

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
		List<Employee> customers = EmployeeRepository.findAll();
		AssertUtil.assertEqual(12, customers.size(), "전체 데이터가 조회되어야 한다.");
	}

	private static void 사용자의_아이디로_조회가_되어야_한다() {
		Employee customer = EmployeeRepository.findById(1);
		AssertUtil.assertEqual("조현우", customer.getName(), "사용자의 아이디로 조회가 되어야 한다.");
	}

	private static void 사용자의_아이디로_데이터가_삭제_되어야_한다() {
		// when
		EmployeeRepository.delete(1);
		Employee customer = EmployeeRepository.findById(1);

		// then
		AssertUtil.assertEqual(null, customer, "사용자의 아이디로 데이터가 삭제 되어야 한다.");
	}

	private static void 새로운_사용자를_저장할_수_있어야_한다() {
		// given
		Employee employee = new Employee("박성열", "010-3075-3014", "광진구 천호대로", 60000000, 2, "인사팀",
				"인사");

		// when
		EmployeeRepository.save(employee);

		// then
		Employee actual = EmployeeRepository.findById(13);
		AssertUtil.assertEqual("박성열", actual.getName(), "새로운 사용자를 저장할 수 있어야 한다.");
	}

	private static void 데이터를_업데이트_할_수_있어야_한다() {
		// given
		Employee em = EmployeeRepository.findById(13);
		Employee updateEmp = new Employee("김선우", em.getPhone(), em.getAddress(), 70000000,
				em.getDependents(), em.getDepartment(), em.getRole());

		// when
		EmployeeRepository.update(13, updateEmp);

		// then
		Employee employee = EmployeeRepository.findById(13);
		AssertUtil.assertEqual("김선우", employee.getName(), "데이터를 업데이트 할 수 있어야 한다.");
	}
}
