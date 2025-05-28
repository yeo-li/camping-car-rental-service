package dbcar.test.java.application;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.common.*;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.application.*;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairShopRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.InternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;

public class RepairRecordFetchServiceTest {
	static AppConfig ac = AppConfig.getInstance();
	static RepairRecordFetchService service;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 초기화
		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");

		service = ac.repairRecordFetchService();

		캠핑카_아이디로_외부정비소_기록이_조회되어야_한다();
		캠핑카_아이디로_내정비소_기록이_조회되어야_한다();
	}

	public static void 캠핑카_아이디로_외부정비소_기록이_조회되어야_한다() {
		// given
		int carId = 3;

		// when
		List<ExternalRepairRecord> actual = service.fetchExternalRepairRecord(carId);

		// then
		AssertUtil.assertEqual(3, actual.get(0).getExternal_repair_id(), "캠핑카 아이디로 외부정비소 기록이 조회되어야 한다.");
	}

	public static void 캠핑카_아이디로_내정비소_기록이_조회되어야_한다() {
		// given
		int carId = 3;

		// when
		List<ExternalRepairRecord> actual = service.fetchExternalRepairRecord(carId);

		// then
		AssertUtil.assertEqual(3, actual.get(0).getExternal_repair_id(), "캠핑카 아이디로 내부정비소 기록이 조회되어야 한다.");
	}

}
