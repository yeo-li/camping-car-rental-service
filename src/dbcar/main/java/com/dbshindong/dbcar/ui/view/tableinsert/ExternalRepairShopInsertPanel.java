package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;

import java.awt.*;

public class ExternalRepairShopInsertPanel extends JPanel {

    AppConfig ac = AppConfig.getInstance();
    private final JTextField nameField = new JTextField(10);
    private final JTextField addressField = new JTextField(10);
    private final JTextField phoneField = new JTextField(10);
    private final JTextField managerNameField = new JTextField(10);
    private final JTextField managerEmailField = new JTextField(10);

    private final JButton saveButton = new JButton("저장");
    private final JButton cancelButton = new JButton("취소");
    private final JButton clearButton = new JButton("초기화");

    public ExternalRepairShopInsertPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("🔧 외부 정비소 정보 입력"));

        formPanel.add(new JLabel("정비소 이름"));
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
                ExternalRepairShop shop = ac.dataInsertService().createExternalRepairShop(
                        nameField.getText().trim(),
                        addressField.getText().trim(),
                        phoneField.getText().trim(),
                        managerNameField.getText().trim(),
                        managerEmailField.getText().trim()
                );

                if (shop == null) {
                    throw new IllegalArgumentException("입력값을 확인해주세요.");
                }

                ac.dataInsertService().insertExternalRepairShop(shop);
                JOptionPane.showMessageDialog(this, "저장 되었습니다.");
                clearFields();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "❗ 오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            clearFields();
            ac.appCoordinator().clearContentPanel();
        });

        clearButton.addActionListener(e -> clearFields());
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