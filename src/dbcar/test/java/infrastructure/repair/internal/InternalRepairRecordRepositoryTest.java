package dbcar.test.java.infrastructure.repair.internal;

import java.sql.Date;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.InternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.InternalRepairRecordRepository;

public class InternalRepairRecordRepositoryTest {
	static AppConfig ac = AppConfig.getInstance();
	static InternalRepairRecordRepository internalRepairRecordRepository;

	public static void main(String[] args) {
		System.out.println("[[InternalRepairRecordRepositoryTest 초기 세팅]]");

		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");
		internalRepairRecordRepository = ac.internalRepairRecordRepository();

		System.out.println("\n[[InternalRepairRecordRepositoryTest]]");

		전체_데이터_조회가_되어야_한다();
		사용자의_아이디로_조회가_되어야_한다();
		사용자의_아이디로_데이터가_삭제_되어야_한다();
		새로운_레코드를_저장할_수_있어야_한다();
		데이터를_업데이트_할_수_있어야_한다();
	}

	private static void 전체_데이터_조회가_되어야_한다() {
		List<InternalRepairRecord> records = internalRepairRecordRepository.findAll();
		AssertUtil.assertEqual(12, records.size(), "전체 데이터가 조회되어야 한다.");
	}

	private static void 사용자의_아이디로_조회가_되어야_한다() {
		InternalRepairRecord record = internalRepairRecordRepository.findById(1);
		AssertUtil.assertEqual(94, record.getDuration_minutes(), "사용자의 아이디로 조회가 되어야 한다.");
	}

	private static void 사용자의_아이디로_데이터가_삭제_되어야_한다() {
		internalRepairRecordRepository.delete(1);
		InternalRepairRecord record = internalRepairRecordRepository.findById(1);
		AssertUtil.assertEqual(null, record, "사용자의 아이디로 데이터가 삭제되어야 한다.");
	}

	private static void 새로운_레코드를_저장할_수_있어야_한다() {
		InternalRepairRecord record = new InternalRepairRecord(1, 1, Date.valueOf("2025-07-01"), 123, 1);
		internalRepairRecordRepository.save(record);
		InternalRepairRecord saved = internalRepairRecordRepository.findById(13);
		AssertUtil.assertEqual(123, saved.getDuration_minutes(), "새로운 레코드를 저장할 수 있어야 한다.");
	}

	private static void 데이터를_업데이트_할_수_있어야_한다() {
		InternalRepairRecord original = internalRepairRecordRepository.findById(13);
		InternalRepairRecord updated = new InternalRepairRecord(original.getCar_id(), original.getPart_id(),
				original.getRepair_date(), 999, original.getEmployee_id());

		internalRepairRecordRepository.update(13, updated);
		InternalRepairRecord record = internalRepairRecordRepository.findById(13);
		AssertUtil.assertEqual(999, record.getDuration_minutes(), "데이터를 업데이트 할 수 있어야 한다.");
	}
}
