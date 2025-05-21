package dbcar.test.java.infrastructure.customer;

import java.sql.Date;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;

public class RentalRepositoryTest {
	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static RentalRepository rentalRepository;

	public static void main(String[] args) {
		System.out.println("[[RentalRepositoryTest 초기 세팅]]");

		dc = new DBConnection("root", "1234");
		databaseInitService = new DatabaseInitService();
		rentalRepository = new RentalRepository(DBConnection.getConnection());
		databaseInitService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");

		System.out.println("\n[[RentalRepositoryTest]]");

		전체_데이터_조회가_되어야_한다();
		사용자의_아이디로_조회가_되어야_한다();
		사용자의_아이디로_데이터가_삭제_되어야_한다();
		새로운_렌탈을_저장할_수_있어야_한다();
		데이터를_업데이트_할_수_있어야_한다();
	}

	private static void 전체_데이터_조회가_되어야_한다() {
		List<Rental> rentals = rentalRepository.findAll();
		AssertUtil.assertEqual(12, rentals.size(), "전체 데이터가 조회되어야 한다.");
	}

	private static void 사용자의_아이디로_조회가_되어야_한다() {
		Rental rental = rentalRepository.findById(1);
		AssertUtil.assertEqual(1, rental.getCar_id(), "사용자의 아이디로 조회가 되어야 한다.");
	}

	private static void 사용자의_아이디로_데이터가_삭제_되어야_한다() {
		rentalRepository.delete(1);
		Rental rental = rentalRepository.findById(1);
		AssertUtil.assertEqual(null, rental, "사용자의 아이디로 데이터가 삭제되어야 한다.");
	}

	private static void 새로운_렌탈을_저장할_수_있어야_한다() {
		Rental rental = new Rental(1, 1, 1, Date.valueOf("2025-05-01"), 5, 100000, Date.valueOf("2025-05-06"), null, null);
		rentalRepository.save(rental);
		Rental actual = rentalRepository.findById(13);
		AssertUtil.assertEqual(1, actual.getCar_id(), "새로운 렌탈을 저장할 수 있어야 한다.");
	}

	private static void 데이터를_업데이트_할_수_있어야_한다() {
		Rental rental = rentalRepository.findById(13);
		Rental updated = new Rental(rental.getRental_id(), rental.getCar_id(), rental.getCustomer_id(), rental.getCompany_id(),
			rental.getStart_date(), 10, 200000, rental.getDue_date(), rental.getExtra_charge_detail(), 20000);
		rentalRepository.update(13, updated);
		Rental result = rentalRepository.findById(13);
		AssertUtil.assertEqual(10, result.getRental_period(), "데이터를 업데이트 할 수 있어야 한다.");
	}
}
