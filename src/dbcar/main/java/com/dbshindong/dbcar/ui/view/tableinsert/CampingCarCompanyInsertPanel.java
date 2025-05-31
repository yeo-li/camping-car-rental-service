package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCarCompany;

import java.awt.*;

public class CampingCarCompanyInsertPanel extends JPanel {

	AppConfig ac = AppConfig.getInstance();

	private final JTextField nameField = new JTextField(10);
	private final JTextField addressField = new JTextField(10);
	private final JTextField phoneField = new JTextField(10);
	private final JTextField managerNameField = new JTextField(10);
	private final JTextField managerEmailField = new JTextField(10);

	private final JButton saveButton = new JButton("저장");
	private final JButton cancelButton = new JButton("취소");
	private final JButton clearButton = new JButton("초기화");

	public CampingCarCompanyInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("🏕 CampingCarCompany 정보 입력"));

		formPanel.add(new JLabel("회사 이름"));
		formPanel.add(nameField);

		formPanel.add(new JLabel("주소"));
		formPanel.add(addressField);

		formPanel.add(new JLabel("전화번호"));
		formPanel.add(phoneField);

		formPanel.add(new JLabel("담당자 이름"));
		formPanel.add(managerNameField);

		formPanel.add(new JLabel("담당자 이메일"));
		formPanel.add(managerEmailField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(clearButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		saveButton.addActionListener(e -> {
			try {
				CampingCarCompany company = ac.dataInsertService().createCampingCarCompany(nameField.getText().trim(),
						addressField.getText().trim(), phoneField.getText().trim(), managerNameField.getText().trim(),
						managerEmailField.getText().trim());

				if (company == null) {
					throw new IllegalArgumentException("입력값을 모두 올바르게 작성해주세요.");
				}

				ac.dataInsertService().insertCampingCarCompany(company);
				JOptionPane.showMessageDialog(this, "저장되었습니다.");
				clearFields();
			} catch (Exception ex) {
				// JOptionPane.showMessageDialog(this, ex.getMessage(), "❗ 오류",
				// JOptionPane.ERROR_MESSAGE);
				GlobalExceptionHandler.handle(ex);
			}
		});

		cancelButton.addActionListener(e -> {
			clearFields();
			ac.appCoordinator().clearContentPanel(); // 입력창 제거
		});

		clearButton.addActionListener(e -> {
			clearFields();
		});
	}

	private void clearFields() {
		nameField.setText("");
		addressField.setText("");
		phoneField.setText("");
		managerNameField.setText("");
		managerEmailField.setText("");
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}
}