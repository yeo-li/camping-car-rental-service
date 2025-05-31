// 라디오 버튼을 유지한 상태에서 보기 모드마다 상단 버튼(또는 콤보박스)을 다르게 보여주는 방식 적용

package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import dbcar.main.java.com.dbshindong.dbcar.application.dto.DateAvailabilityRequest;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;

public class UserReservationModifyPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private AppConfig ac = AppConfig.getInstance();

    private JScrollPane scrollPane;
    private JTable currentTable;
    private boolean eventflag = true;
    private Rental rent;

    private JPanel actionPanel; // 상단 버튼/콤보박스 영역
    private JButton carChangeButton, dateChangeButton;
    
    private JComboBox<LocalDate> startComboBox;
    private JComboBox<LocalDate> endComboBox;
    private List<DateAvailabilityRequest> availableDates; // 미리 받아온 날짜 + 가능여부 리스트


    public UserReservationModifyPanel(Rental rent, String carname) {
        this.rent = rent;
        createUI(rent, carname);
    }

    private void createUI(Rental rent, String carname) {
        setLayout(null);

        int panelWidth = 800;

        JLabel infoLabel = new JLabel("이름: " + carname + ", 기간: " + rent.getStart_date() + " - " + rent.getStart_date().toLocalDate().plusDays(rent.getRental_period() - 1), JLabel.CENTER);
        infoLabel.setBounds((panelWidth - 750)/2, 30, 750, 20);
        add(infoLabel);

        JRadioButton carOption = new JRadioButton("차량 수정", true);
        JRadioButton dateOption = new JRadioButton("날짜 수정");
        carOption.setBounds(300, 60, 100, 25);
        dateOption.setBounds(400, 60, 100, 25);
        ButtonGroup viewGroup = new ButtonGroup();
        viewGroup.add(carOption);
        viewGroup.add(dateOption);
        add(carOption);
        add(dateOption);

        actionPanel = new JPanel(null);
        actionPanel.setBounds((panelWidth - 750)/2, 90, 750, 30);
        add(actionPanel);

        JLabel tableTitle = new JLabel("목록", JLabel.CENTER);
        tableTitle.setBounds((panelWidth - 750)/2, 120, 750, 20);
        add(tableTitle);

        scrollPane = new JScrollPane();
        scrollPane.setBounds((panelWidth - 750)/2, 150, 750, 420);
        add(scrollPane);

        carOption.addActionListener(e -> {
            switchActionPanel("car");
            showCarTable();
        });

        dateOption.addActionListener(e -> {
            switchActionPanel("date");
            showDateTable();
        });

        switchActionPanel("car");
        showCarTable();
    }
    
    
    private void switchActionPanel(String mode) {
        actionPanel.removeAll();

        if (mode.equals("car")) {
            carChangeButton = new JButton("차량 변경");
            carChangeButton.setBounds(0, 0, 120, 30);
            actionPanel.add(carChangeButton);
        } else if (mode.equals("date")) {
        	startComboBox = new JComboBox<>();
            endComboBox = new JComboBox<>();
            
            setupDateComboBoxes();
            
            startComboBox.setBounds(0, 0, 120, 30);
            endComboBox.setBounds(130, 0, 120, 30);
            dateChangeButton = new JButton("일정 변경");
            dateChangeButton.setBounds(270, 0, 120, 30);
            actionPanel.add(startComboBox);
            actionPanel.add(endComboBox);
            actionPanel.add(dateChangeButton);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }
    private void setupDateComboBoxes() {
        availableDates = ac.userReservationModifyController().findUnavailableDate(this.rent.getCar_id(), ac.appCoordinator().getUser())
            .stream()
            .toList();

        startComboBox = new JComboBox<>();
        endComboBox = new JComboBox<>();

        for (DateAvailabilityRequest dar : availableDates) {
        	if(dar.available()) startComboBox.addItem(dar.date());
        }

        startComboBox.addActionListener(e -> {
        	updateEndDateComboBox();
        	});

    }

    private void updateEndDateComboBox() {
        endComboBox.removeAllItems();

        LocalDate selectedStart = (LocalDate) startComboBox.getSelectedItem();
        if (selectedStart == null) return;

        boolean startAdding = false;
        for (DateAvailabilityRequest dar : availableDates) {
            if (dar.date().isEqual(selectedStart)) {
                startAdding = true;
            }
            if (startAdding) {
                if (dar.available()) {
                    endComboBox.addItem(dar.date());
                } else {
                    return; // 연속 불가 날짜가 오면 중단
                }
            }
        }
    }

    
    private void showCarTable() {
        try {
            List<Integer> carList = ac.userReservationModifyController().findAvailableCarId(this.rent);
            String[] columnNames = {"선택", "모델명", "차량번호", "탑승인원", "사진", "가격", "상세정보"};
            Object[][] data = new Object[carList.size()][7];

            for (int i = 0; i < carList.size(); i++) {
                CampingCar car = ac.userReservationModifyController().findCarById(carList.get(i));
                data[i][0] = Boolean.FALSE;
                data[i][1] = car.getName();
                data[i][2] = car.getPlate_number();
                data[i][3] = car.getCapacity() + "인승";
                data[i][4] = car.getImage();
                data[i][5] = car.getRental_price() + "원";
                data[i][6] = car.getDescription();
            }

            JTable table = new JTable(data, columnNames) {
                public boolean isCellEditable(int row, int column) {
                    return column == 0;
                }
                public Class<?> getColumnClass(int column) {
                    return column == 0 ? Boolean.class : super.getColumnClass(column);
                }
                public TableCellRenderer getCellRenderer(int row, int column) {
                    if (column == 4) return new SwingImageRenderer();
                    return super.getCellRenderer(row, column);
                }
            };
            
            table.getModel().addTableModelListener(e -> {
				if(eventflag == false) return;
				
	            if (e.getColumn() == 0) {
	            	eventflag = false;
	                for (int i = 0; i < table.getRowCount(); i++) {
	                    if (i != e.getFirstRow()) {
	                        table.setValueAt(false, i, 0);
	                    }
	                }
	                eventflag = true;
	            }
	        });
            
            table.setRowHeight(60);
            scrollPane.setViewportView(table);
            currentTable = table;
        } catch (Exception ex) {
            GlobalExceptionHandler.handle(ex);
        }
    }

    private void showDateTable() {
        try {
            List<DateAvailabilityRequest> list = ac.userReservationModifyController().findUnavailableDate(this.rent.getCar_id(), ac.appCoordinator().getUser());
            String[] columnNames = {"선택", "날짜"};
            Object[][] data = new Object[list.size()][2];
            for (int i = 0; i < list.size(); i++) {
                data[i][0] = list.get(i).available() ? "O" : "X";
                data[i][1] = list.get(i).date();
            }

            JTable table = new JTable(data, columnNames) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
                public Class<?> getColumnClass(int column) {
                    return String.class;
                }
            };

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            table.setRowHeight(25);
            scrollPane.setViewportView(table);
            currentTable = table;
        } catch (Exception ex) {
            GlobalExceptionHandler.handle(ex);
        }
    }

    static class SwingImageRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            if (value instanceof byte[]) {
                try {
                    ImageIcon icon = new ImageIcon((byte[]) value);
                    Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaled));
                } catch (Exception e) {
                    label.setText("이미지 오류");
                }
            }
            return label;
        }
    }
}
