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

	private static final String NAME = "회사 이름";
	private static final String ADDRESS = "주소";
	private static final String PHONE = "전화번호";
	private static final String MANAGER_NAME = "담당자 이름";
	private static final String MANAGER_EMAIL = "담당자 이메일";

	public CampingCarCompanyInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("CampingCarCompany 정보 입력"));

		formPanel.add(new JLabel(NAME));
		formPanel.add(nameField);

		formPanel.add(new JLabel(ADDRESS));
		formPanel.add(addressField);

		formPanel.add(new JLabel(PHONE));
		formPanel.add(phoneField);

		formPanel.add(new JLabel(MANAGER_NAME));
		formPanel.add(managerNameField);

		formPanel.add(new JLabel(MANAGER_EMAIL));
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

				ac.dataInsertService().insertCampingCarCompany(company);
				JOptionPane.showMessageDialog(this, "저장되었습니다.");
				clearFields();
			} catch (Exception ex) {
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