package dbcar.test.java.infrastructure.company;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCarCompany;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarCompanyRepository;

public class CampingCarCompanyRepositoryTest {
	static AppConfig ac = AppConfig.getInstance();
	static CampingCarCompanyRepository companyRepository;

	public static void main(String[] args) {
		System.out.println("[[CampingCarCompanyRepositoryTest 초기 세팅]]");
		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");
		companyRepository = ac.campingCarCompanyRepository();

		System.out.println("\n[[CampingCarCompanyRepositoryTest]]");

		전체_데이터_조회가_되어야_한다();
		사용자의_아이디로_조회가_되어야_한다();
		사용자의_아이디로_데이터가_삭제_되어야_한다();
		새로운_회사를_저장할_수_있어야_한다();
		데이터를_업데이트_할_수_있어야_한다();
	}

	private static void 전체_데이터_조회가_되어야_한다() {
		List<CampingCarCompany> companies = companyRepository.findAll();
		AssertUtil.assertEqual(12, companies.size(), "전체 회사 데이터가 조회되어야 한다.");
	}

	private static void 사용자의_아이디로_조회가_되어야_한다() {
		CampingCarCompany company = companyRepository.findById(1);
		AssertUtil.assertEqual("별빛렌탈 7404", company.getName(), "ID로 조회된 회사의 이름이 일치해야 한다.");
	}

	private static void 사용자의_아이디로_데이터가_삭제_되어야_한다() {
		companyRepository.delete(1);
		CampingCarCompany company = companyRepository.findById(1);
		AssertUtil.assertEqual(null, company, "ID로 삭제 후 데이터가 없어야 한다.");
	}

	private static void 새로운_회사를_저장할_수_있어야_한다() {
		CampingCarCompany company = new CampingCarCompany("소프트캠핑 3000", "서울 강서구 강서로 300", "010-9999-9999", "이강우",
				"kangwoo@example.com");
		companyRepository.save(company);

		CampingCarCompany saved = companyRepository.findById(13);
		AssertUtil.assertEqual("소프트캠핑 3000", saved.getName(), "새로운 회사 저장 후 이름이 일치해야 한다.");
	}

	private static void 데이터를_업데이트_할_수_있어야_한다() {
		CampingCarCompany existing = companyRepository.findById(13);
		CampingCarCompany updated = new CampingCarCompany("하드캠핑 3000", existing.getAddress(), existing.getPhone(),
				existing.getManager_name(), existing.getManager_email());

		companyRepository.update(13, updated);
		CampingCarCompany company = companyRepository.findById(13);
		AssertUtil.assertEqual("하드캠핑 3000", company.getName(), "업데이트된 회사 이름이 일치해야 한다.");
	}
}