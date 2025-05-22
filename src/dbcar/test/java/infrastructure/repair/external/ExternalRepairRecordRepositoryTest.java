package dbcar.test.java.infrastructure.repair.external;

import java.sql.Date;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairRecordRepository;

public class ExternalRepairRecordRepositoryTest {
	static DBConnection dc;
	static DatabaseInitService databaseInitService;
	static ExternalRepairRecordRepository externalRepairRecordRepository;

	public static void main(String[] args) {
		System.out.println("[[ExternalRepairRecordRepositoryTest 초기 세팅]]");

		dc = new DBConnection("root", "1234");
		databaseInitService = new DatabaseInitService();
		externalRepairRecordRepository = new ExternalRepairRecordRepository(DBConnection.getConnection());
		databaseInitService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");

		System.out.println("\n[[ExternalRepairRecordRepositoryTest]]");

		전체_데이터_조회가_되어야_한다();
		사용자의_아이디로_조회가_되어야_한다();
		사용자의_아이디로_데이터가_삭제_되어야_한다();
		새로운_레코드를_저장할_수_있어야_한다();
		데이터를_업데이트_할_수_있어야_한다();
	}

	private static void 전체_데이터_조회가_되어야_한다() {
		List<ExternalRepairRecord> records = externalRepairRecordRepository.findAll();
		AssertUtil.assertEqual(12, records.size(), "전체 데이터가 조회되어야 한다.");
	}

	private static void 사용자의_아이디로_조회가_되어야_한다() {
		ExternalRepairRecord record = externalRepairRecordRepository.findById(1);
		AssertUtil.assertEqual("외부 수리 내용 1", record.getContent(), "사용자의 아이디로 조회가 되어야 한다.");
	}

	private static void 사용자의_아이디로_데이터가_삭제_되어야_한다() {
		externalRepairRecordRepository.delete(1);
		ExternalRepairRecord record = externalRepairRecordRepository.findById(1);
		AssertUtil.assertEqual(null, record, "사용자의 아이디로 데이터가 삭제 되어야 한다.");
	}

	private static void 새로운_레코드를_저장할_수_있어야_한다() {
		ExternalRepairRecord record = new ExternalRepairRecord(
				1, 1, 1, 1, "새로운 외부 수리 내용", Date.valueOf("2025-06-10"), 123456, Date.valueOf("2025-07-10"), null);

		externalRepairRecordRepository.save(record);
		ExternalRepairRecord actual = externalRepairRecordRepository.findById(13);
		AssertUtil.assertEqual("새로운 외부 수리 내용", actual.getContent(), "새로운 레코드를 저장할 수 있어야 한다.");
	}

	private static void 데이터를_업데이트_할_수_있어야_한다() {
		ExternalRepairRecord oldRecord = externalRepairRecordRepository.findById(13);
		ExternalRepairRecord updated = new ExternalRepairRecord(
				oldRecord.getCar_id(), oldRecord.getShop_id(), oldRecord.getCompany_id(), oldRecord.getCustomer_id(),
				"수정된 수리 내용", oldRecord.getRepair_date(), oldRecord.getCost(), oldRecord.getDue_date(), "집가고싶");

		externalRepairRecordRepository.update(13, updated);
		ExternalRepairRecord result = externalRepairRecordRepository.findById(13);
		AssertUtil.assertEqual("수정된 수리 내용", result.getContent(), "데이터를 업데이트 할 수 있어야 한다.");
	}
}
