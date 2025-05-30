package dbcar.test.java.infrastructure.company;

import java.sql.Date;
import java.util.List;
import java.sql.*;

import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.RepositoryException;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;

public class CampingCarRepositoryTest {
	static AppConfig ac = AppConfig.getInstance();
	static CampingCarRepository campingCarRepository;

	public static void main(String[] args) {
		System.out.println("[[CampingCarRepositoryTest 초기 세팅]]");

		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");
		campingCarRepository = ac.campingCarRepository();

		System.out.println("\n[[CampingCarRepositoryTest]]");

		전체_데이터_조회가_되어야_한다();
		사용자의_아이디로_조회가_되어야_한다();
		사용자의_아이디로_데이터가_삭제_되어야_한다();
		새로운_사용자를_저장할_수_있어야_한다();
		데이터를_업데이트_할_수_있어야_한다();
		조건식을_입력받아_데이터를_조회할_수_있어야_한다();
		조건식의_문법이_틀리면_오류를_발생시켜야_한다();
	}

	private static void 전체_데이터_조회가_되어야_한다() {
		List<CampingCar> cars = campingCarRepository.findAll();
		AssertUtil.assertEqual(12, cars.size(), "전체 데이터가 조회되어야 한다.");
	}

	private static void 사용자의_아이디로_조회가_되어야_한다() {
		CampingCar car = campingCarRepository.findById(1);
		AssertUtil.assertEqual("썬무버-MPO", car.getName(), "사용자의 아이디로 조회가 되어야 한다.");
	}

	private static void 사용자의_아이디로_데이터가_삭제_되어야_한다() {
		campingCarRepository.delete(1);
		CampingCar car = campingCarRepository.findById(1);
		AssertUtil.assertEqual(null, car, "사용자의 아이디로 데이터가 삭제 되어야 한다.");
	}

	private static void 새로운_사용자를_저장할_수_있어야_한다() {
		CampingCar newCar = new CampingCar("에버그린-X1", "서울34나9999", 4, new byte[] { 0x01, 0x02 }, "작고 실용적인 캠핑카입니다.",
				99999, 2, "2025-01-01");

		campingCarRepository.save(newCar);

		CampingCar actual = campingCarRepository.findById(13);
		AssertUtil.assertEqual("에버그린-X1", actual.getName(), "새로운 사용자를 저장할 수 있어야 한다.");
	}

	private static void 데이터를_업데이트_할_수_있어야_한다() {
		CampingCar oldCar = campingCarRepository.findById(13);
		CampingCar updatedCar = new CampingCar("레전드그린-X2", oldCar.getPlate_number(), oldCar.getCapacity(),
				oldCar.getImage(), oldCar.getDescription(), 123456, oldCar.getCompany_id(),
				oldCar.getRegistered_date().toString());

		campingCarRepository.update(13, updatedCar);

		CampingCar actual = campingCarRepository.findById(13);
		AssertUtil.assertEqual("레전드그린-X2", actual.getName(), "데이터를 업데이트 할 수 있어야 한다.");
	}

	private static void 조건식을_입력받아_데이터를_조회할_수_있어야_한다() {

		// given
		String sql = "car_id > 4";

		// when
		List<CampingCar> cars = null;

		cars = campingCarRepository.findByCondition(sql);

		// then
		int actual = cars.size();
		AssertUtil.assertEqual(9, actual, "조건식을 입력받아 데이터를 조회할 수 있어야 한다.");
	}

	private static void 조건식의_문법이_틀리면_오류를_발생시켜야_한다() {

		// given
		String sql = "name lik '다이나믹%'";

		// when
		List<CampingCar> cars = null;
		try {
			cars = campingCarRepository.findByCondition(sql);
		} catch (RepositoryException e) {
			AssertUtil.assertEqual(-1, -1, "조건식의 문법이 틀리면 오류를 발생시켜야 한다.");
			return;
		}

		// then
		AssertUtil.assertEqual(0, -1, "조건식의 문법이 틀리면 오류를 발생시켜야 한다.");
	}
}