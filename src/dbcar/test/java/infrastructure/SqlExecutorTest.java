package dbcar.test.java.infrastructure;

import java.sql.SQLSyntaxErrorException;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.common.AssertUtil;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarCompanyRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.SqlExecutor;

public class SqlExecutorTest {
	static AppConfig ac = AppConfig.getInstance();
	static SqlExecutor infra;

	public static void main(String[] args) {

		// 데이터베이스 초기화
		ac.dbConnection().setConnection("root", "1234");
		ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
				"dbcar/main/java/resources/DatabaseInit.sql");
		infra = ac.sqlExecutor();

		SELECT문을_조회하고_결과를_반환해야_한다();
		조인된_테이블의_속성은_유지_되어야_한다();
	}

	static public void SELECT문을_조회하고_결과를_반환해야_한다() {
		// given
		String sql = "SELECT\n" + "    cu.name AS customer_name,\n" + "    ca.name AS campingcar_name,\n"
				+ "    ers.name AS repair_shop_name,\n" + "    COUNT(err.external_repair_id) AS repair_count,\n"
				+ "    SUM(err.cost) AS total_cost\n" + "FROM\n" + "    ExternalRepairRecord err\n"
				+ "JOIN Customer cu ON err.customer_id = cu.customer_id\n"
				+ "JOIN CampingCar ca ON err.car_id = ca.car_id\n"
				+ "JOIN ExternalRepairShop ers ON err.shop_id = ers.shop_id\n"
				+ "GROUP BY cu.customer_id, ca.car_id, ers.shop_id\n" + "ORDER BY total_cost DESC;";

		// when
		List<Map<String, Object>> actual = null;
		try {
			actual = infra.findData(sql);
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}

		// then
		AssertUtil.assertEqual(12, actual.size(), "SELECT문을 조회하고 결과를 반환해야 한다.");
	}

	static public void 조인된_테이블의_속성은_유지_되어야_한다() {
		String sql = "select * from part, internalrepairrecord;";

		List<Map<String, Object>> actual = null;
		try {
			actual = infra.findData(sql);
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		}

		AssertUtil.assertEqual(12, actual.getFirst().size(), "조인된 테이블의 속성은 유지 되어야 한다.");
	}

}
