package dbcar.test.java.ui;

import dbcar.main.java.com.dbshindong.dbcar.application.DataFetchService;
import dbcar.main.java.com.dbshindong.dbcar.application.DatabaseInitService;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.*;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.*;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.SqlQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.admin.SqlQueryView;

import javax.swing.*;

public class SqlQueryViewTestLauncher {

	public static void main(String[] args) {
		// DB 연
		new DBConnection("root", "1234");
        DatabaseInitService initService = new DatabaseInitService();
        initService.initDatabase(DBConnection.getConnection(), "dbcar/main/java/resources/DatabaseInit.sql");


		// 서비스 & 컨트롤러 생성
		DataFetchService fetchService = new DataFetchService();

		SqlQueryController controller = new SqlQueryController();
		SqlQueryView view = new SqlQueryView();

		// 프레임으로 띄우기
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("🎨 SQL View 화면 테스트");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(900, 600);
			frame.add(view);
			frame.setLocationRelativeTo(null); // 중앙 정렬
			frame.setVisible(true);
		});
	}
}
