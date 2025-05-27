package dbcar.test.java.infrastructure.repair.external;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairShopRepository;

public class ExternalRepairShopRepositoryTest {
	static AppConfig ac = AppConfig.getInstance();
	static ExternalRepairShopRepository externalRepairShopRepository;

	public static void main(String[] args) {
		System.out.println("[[ExternalRepairShopRepositoryTest 초기 세팅]]");

		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");
		externalRepairShopRepository = ac.externalRepairShopRepository();

		System.out.println("\n[[ExternalRepairShopRepositoryTest]]");

		전체_데이터_조회가_되어야_한다();
		사용자의_아이디로_조회가_되어야_한다();
		사용자의_아이디로_데이터가_삭제_되어야_한다();
		새로운_사용자를_저장할_수_있어야_한다();
		데이터를_업데이트_할_수_있어야_한다();
	}

	private static void 전체_데이터_조회가_되어야_한다() {
		List<ExternalRepairShop> shops = externalRepairShopRepository.findAll();
		AssertUtil.assertEqual(12, shops.size(), "전체 데이터가 조회되어야 한다.");
	}

	private static void 사용자의_아이디로_조회가_되어야_한다() {
		ExternalRepairShop shop = externalRepairShopRepository.findById(1);
		AssertUtil.assertEqual("정비소1", shop.getName(), "사용자의 아이디로 조회가 되어야 한다.");
	}

	private static void 사용자의_아이디로_데이터가_삭제_되어야_한다() {
		externalRepairShopRepository.delete(1);
		ExternalRepairShop shop = externalRepairShopRepository.findById(1);
		AssertUtil.assertEqual(null, shop, "사용자의 아이디로 데이터가 삭제 되어야 한다.");
	}

	private static void 새로운_사용자를_저장할_수_있어야_한다() {
		ExternalRepairShop shop = new ExternalRepairShop("서울정비소", "서울시 종로구", "010-1111-2222", "홍길동", "hong@test.com");
		externalRepairShopRepository.save(shop);
		ExternalRepairShop actual = externalRepairShopRepository.findById(13);
		AssertUtil.assertEqual("서울정비소", actual.getName(), "새로운 사용자를 저장할 수 있어야 한다.");
	}

	private static void 데이터를_업데이트_할_수_있어야_한다() {
		ExternalRepairShop origin = externalRepairShopRepository.findById(13);
		ExternalRepairShop updated = new ExternalRepairShop("업데이트정비소", origin.getAddress(), origin.getPhone(),
				origin.getManager_name(), origin.getManager_email());
		externalRepairShopRepository.update(13, updated);
		ExternalRepairShop after = externalRepairShopRepository.findById(13);
		AssertUtil.assertEqual("업데이트정비소", after.getName(), "데이터를 업데이트 할 수 있어야 한다.");
	}
}
