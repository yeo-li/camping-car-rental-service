package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;
import java.awt.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.Employee;

public class EmployeeInsertPanel extends JPanel {

	AppConfig ac = AppConfig.getInstance();

	private final JTextField nameField = new JTextField(10);
	private final JTextField phoneField = new JTextField(10);
	private final JTextField addressField = new JTextField(10);
	private final JTextField salaryField = new JTextField(10);
	private final JTextField dependentsField = new JTextField(10);
	private final JTextField departmentField = new JTextField(10);
	private final JTextField roleField = new JTextField(10);

	private final JButton saveButton = new JButton("저장");
	private final JButton cancelButton = new JButton("취소");
	private final JButton clearButton = new JButton("초기화");

	private final static String EMPLOYEE_ID = "직원 ID";
	private final static String NAME = "직원명";
	private final static String PHONE = "전화번호";
	private final static String ADDRESS = "주소";
	private final static String SALARY = "월급여";
	private final static String DEPENDENTS = "부양 가족 수";
	private final static String DEPARTMENT = "담당부서";
	private final static String ROLE = "담당 업무";

	public EmployeeInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("Employee 정보 입력"));

		formPanel.add(new JLabel(NAME));
		formPanel.add(nameField);

		formPanel.add(new JLabel(PHONE));
		formPanel.add(phoneField);

		formPanel.add(new JLabel(ADDRESS));
		formPanel.add(addressField);

		formPanel.add(new JLabel(SALARY));
		formPanel.add(salaryField);

		formPanel.add(new JLabel(DEPENDENTS));
		formPanel.add(dependentsField);

		formPanel.add(new JLabel(DEPARTMENT));
		formPanel.add(departmentField);

		formPanel.add(new JLabel(ROLE));
		formPanel.add(roleField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(clearButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		saveButton.addActionListener(e -> {
			try {
				String name = nameField.getText().trim();
				String phone = phoneField.getText().trim();
				String address = addressField.getText().trim();
				Integer salary = safeParseInt(salaryField.getText().trim(), SALARY);
				Integer dependents = safeParseInt(dependentsField.getText().trim(), DEPENDENTS);
				String department = departmentField.getText().trim();
				String role = roleField.getText().trim();

				Employee employee = ac.dataInsertService().createEmployee(name, phone, address, salary, dependents,
						department, role);
				ac.dataInsertService().insertEmployee(employee);

				JOptionPane.showMessageDialog(this, "저장 되었습니다.");
				clearFields();
			} catch (Exception ex) {
				ex.printStackTrace();
				GlobalExceptionHandler.handle(ex);
			}
		});

		cancelButton.addActionListener(e ->

		{
			clearFields();
			ac.appCoordinator().clearContentPanel();
		});

		clearButton.addActionListener(e ->

		clearFields());
	}

	private void clearFields() {
		nameField.setText("");
		phoneField.setText("");
		addressField.setText("");
		salaryField.setText("");
		dependentsField.setText("");
		departmentField.setText("");
		roleField.setText("");
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	private Integer safeParseInt(String input, String fieldName) {
		try {
			return input == null || input.isBlank() ? null : Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(fieldName + "의 입력값은 숫자여야 합니다.");
		}
	}
}