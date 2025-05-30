package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminInitPanel extends JPanel {

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

			switch (buttonLabels[i]) {
			case "데이터베이스 초기화" -> button.addActionListener(e -> System.out.println(">> DB 초기화 실행됨"));

			case "테이블 데이터 입력" -> button.addActionListener(e -> System.out.println(">> 테이블 데이터 입력 화면 전환"));

			case "테이블 데이터 삭제/변경" -> button.addActionListener(e -> System.out.println(">> 데이터 삭제/변경 화면 전환"));

			case "전체 테이블 보기" -> button.addActionListener(e -> System.out.println(">> 전체 테이블 보기로 이동"));

			case "캠핑카 정비 기록 조회" -> button.addActionListener(e -> System.out.println(">> 정비 기록 조회 화면 전환"));

			case "SQL 질의 실행 및 결과 보기" -> button.addActionListener(e -> System.out.println(">> SQL 질의 실행 화면 전환"));
			}
		}
	}
}