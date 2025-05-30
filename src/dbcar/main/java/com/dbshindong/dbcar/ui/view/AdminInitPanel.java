package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DatabaseException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;

import java.awt.*;

public class AdminInitPanel extends JPanel {
	private AppConfig ac = AppConfig.getInstance();
	private static final long serialVersionUID = 1L;

	public AdminInitPanel() {
		createAdminInitPanel();
	}

	public void createAdminInitPanel() {
		setLayout(new GridBagLayout());
		addComponents(this);
	}

	private void addComponents(JPanel panel) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel mainLabel = new JLabel("관리자 모드", SwingConstants.CENTER);
		mainLabel.setFont(mainLabel.getFont().deriveFont(40f));
		gbc.gridy = 0;
		panel.add(mainLabel, gbc);

		Dimension buttonSize = new Dimension(300, 45);

		String[] buttonLabels = { "데이터베이스 초기화", "테이블 데이터 입력", "테이블 데이터 삭제/변경", "전체 테이블 보기", "캠핑카 정비 기록 조회",
				"SQL 질의 실행 및 결과 보기" };

		for (int i = 0; i < buttonLabels.length; i++) {
			JButton button = new JButton(buttonLabels[i]);
			button.setPreferredSize(buttonSize);
			gbc.gridy = i + 1;
			panel.add(button, gbc);

			int index = i; // 람다에서 사용하려면 final 또는 effectively final 변수 필요

			try {
				button.addActionListener(e -> {
					switch (index) {
					case 0 -> showDatabaseResetConfirmation();
					case 1 -> System.out.println("테이블 데이터 입력 화면으로 이동");
					case 2 -> System.out.println("테이블 데이터 삭제/변경 화면으로 이동");
					case 3 -> System.out.println("전체 테이블 보기 화면으로 이동");
					case 4 -> System.out.println("캠핑카 정비 기록 조회 화면으로 이동");
					case 5 -> System.out.println("SQL 질의 실행 및 결과 보기 화면으로 이동");
					}
				});
			} catch (Exception e) {
				GlobalExceptionHandler.handle(e);
			}
		}
	}

	private void showDatabaseResetConfirmation() {
		int result = JOptionPane.showConfirmDialog(this, "데이터베이스를 초기화하시겠습니까?\n지금까지 저장된 데이터는 복구되지 않습니다.",
				"⚠ 데이터베이스 초기화 경고", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			ac.databaseInitService().initDatabase(ac.dbConnection().getConnection(),
					"dbcar/main/java/resources/DatabaseInit.sql");
			JOptionPane.showMessageDialog(this, "데이터베이스가 성공적으로 초기화되었습니다.");
		} else {
			JOptionPane.showMessageDialog(this, "취소되었습니다.");
		}
	}
}
