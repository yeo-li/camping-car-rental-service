package dbcar.test.java.infrastructure.repair.internal;

import java.sql.Date;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.Part;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;

public class PartRepositoryTest {
	static AppConfig ac = AppConfig.getInstance();
	static PartRepository partRepository;

	public static void main(String[] args) {
		System.out.println("[[PartRepositoryTest 초기 세팅]]");

		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");
		partRepository = ac.partRepository();

		System.out.println("\n[[PartRepositoryTest]]");

		전체_데이터_조회가_되어야_한다();
		사용자의_아이디로_조회가_되어야_한다();
		사용자의_아이디로_데이터가_삭제_되어야_한다();
		새로운_부품을_저장할_수_있어야_한다();
		데이터를_업데이트_할_수_있어야_한다();
	}

	private static void 전체_데이터_조회가_되어야_한다() {
		List<Part> parts = partRepository.findAll();
		AssertUtil.assertEqual(12, parts.size(), "전체 데이터가 조회되어야 한다.");
	}

	private static void 사용자의_아이디로_조회가_되어야_한다() {
		Part part = partRepository.findById(1);
		AssertUtil.assertEqual("에어컨 필터 587", part.getName(), "사용자의 아이디로 조회가 되어야 한다.");
	}

	private static void 사용자의_아이디로_데이터가_삭제_되어야_한다() {
		partRepository.delete(1);
		Part part = partRepository.findById(1);
		AssertUtil.assertEqual(null, part, "사용자의 아이디로 데이터가 삭제 되어야 한다.");
	}

	private static void 새로운_부품을_저장할_수_있어야_한다() {
		Part part = new Part("테스트 부품", 99999, 10, Date.valueOf("2025-07-01"), "테스트공급자");
		partRepository.save(part);

		Part actual = partRepository.findById(13);
		AssertUtil.assertEqual("테스트 부품", actual.getName(), "새로운 부품을 저장할 수 있어야 한다.");
	}

	private static void 데이터를_업데이트_할_수_있어야_한다() {
		Part origin = partRepository.findById(13);
		Part update = new Part("수정된 부품", 12345, origin.getStock_quantity(), origin.getStock_date(),
				origin.getSupplier_name());

		partRepository.update(13, update);

		Part updated = partRepository.findById(13);
		AssertUtil.assertEqual("수정된 부품", updated.getName(), "데이터를 업데이트 할 수 있어야 한다.");
	}
}
