package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class TableEntrySelectorPanel extends JPanel {
    private final JComboBox<String> tableComboBox;
    private final JButton selectButton;

    public TableEntrySelectorPanel(Consumer<String> onSelectTable) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("🗂 입력할 테이블을 선택하세요", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        String[] tables = {
                "CampingCarCompany",
                "CampingCar",
                "Customer",
                "Employee",
                "Rental",
                "InternalRepairRecord",
                "ExternalRepairRecord",
                "ExternalRepairShop",
                "Part"
        };

        tableComboBox = new JComboBox<>(tables);
        selectButton = new JButton("선택");

        JPanel centerPanel = new JPanel(new FlowLayout());
        centerPanel.add(new JLabel("테이블 목록: "));
        centerPanel.add(tableComboBox);
        centerPanel.add(selectButton);

        add(titleLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // 테이블 선택 시 콜백 전달
        selectButton.addActionListener(e -> {
            String selected = (String) tableComboBox.getSelectedItem();
            onSelectTable.accept(selected);  // 외부에서 화면 전환 제어
        });
    }
}