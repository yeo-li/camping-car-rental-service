package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;

public class UserReservationModifyPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private AppConfig ac = AppConfig.getInstance();

    private JScrollPane scrollPane;
    private JTable currentTable;
    private boolean eventflag = true;

    public UserReservationModifyPanel(Rental rent, String carname) {
        createUI(rent, carname);
    }

    private void createUI(Rental rent, String carname) {
        setLayout(null);

        int panelWidth = 800;
        int panelHeight = 600;

        // 로그아웃 버튼
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(panelWidth - 80 - 25, 10, 80, 25);
        add(logoutButton);

        // 차량 및 날짜 정보 라벨
        JLabel infoLabel = new JLabel("이름: " + carname + ", 기간: " + rent.getStart_date() + " - " + rent.getStart_date().toLocalDate().plusDays(rent.getRental_period()), JLabel.CENTER);
        infoLabel.setBounds((panelWidth - 750)/2, 30, 750, 20);
        add(infoLabel);

        // 라디오 버튼으로 보기 전환
        JRadioButton carOption = new JRadioButton("차량 보기", true);
        JRadioButton dateOption = new JRadioButton("날짜 보기");
        carOption.setBounds(300, 60, 100, 25);
        dateOption.setBounds(400, 60, 100, 25);
        ButtonGroup viewGroup = new ButtonGroup();
        viewGroup.add(carOption);
        viewGroup.add(dateOption);
        add(carOption);
        add(dateOption);

        // 테이블 제목
        JLabel tableTitle = new JLabel("목록", JLabel.CENTER);
        tableTitle.setBounds((panelWidth - 770)/2, 90, 750, 20);
        add(tableTitle);

        // 스크롤 영역 초기화
        scrollPane = new JScrollPane();
        scrollPane.setBounds((panelWidth - 750)/2, 120, 750, 450);
        add(scrollPane);

        // 이벤트 처리
        carOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCarTable();
            }
        });

        dateOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDateTable(rent);
            }
        });

        // 초기 테이블
        showCarTable();
    }

    private void showCarTable() {
        List<CampingCar> carList = ac.campingCarQueryController().handleQuery();

        String[] columnNames = {"선택","모델명", "차량번호", "탑승인원", "사진", "가격", "상세정보"};
        Object[][] data = new Object[carList.size()][7];

        for (int i = 0; i < carList.size(); i++) {
        	CampingCar car = carList.get(i);
            data[i][0] = Boolean.FALSE;
            data[i][1] = car.getName();
            data[i][2] = car.getPlate_number();
            data[i][3] = car.getCapacity() + "인승";
            data[i][4] = car.getImage();
            data[i][5] = car.getRental_price() + "원";
            data[i][6] = car.getDescription();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : super.getColumnClass(column);
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 4) return new SwingImageRenderer();
                return super.getCellRenderer(row, column);
            }
        };
        table.setRowHeight(60);
        table.getColumn("선택").setPreferredWidth(30);
		table.getColumn("모델명").setPreferredWidth(70);
		table.getColumn("탑승인원").setPreferredWidth(50);
		table.getColumn("가격").setPreferredWidth(40);
		table.getColumn("상세정보").setPreferredWidth(190);
		
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
		
        scrollPane.setViewportView(table);
        currentTable = table;
    }

    private void showDateTable(Rental rent) {
        List<LocalDate> availList = ac.campingCarAvailableDateQueryController().handleQuery(rent.getCar_id());

        String[] columnNames = {"선택", "날짜"};
        Object[][] data = new Object[availList.size()][2];
        for (int i = 0; i < availList.size(); i++) {
            data[i][0] = Boolean.FALSE;
            data[i][1] = availList.get(i).toString();
        }

        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
        };
        table.setRowHeight(25);
        scrollPane.setViewportView(table);
        currentTable = table;
    }

    static class SwingImageRenderer extends DefaultTableCellRenderer {
        @Override
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
