package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.UserReservationQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.CampingCarQueryPanel.SwingImageRenderer;

public class UserReservationQueryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private AppConfig ac = AppConfig.getInstance();
	private UserReservationQueryController userReservationQueryController;
	private boolean eventFlag = true; 
	
	public UserReservationQueryPanel() {
		createUI();
	}
	
	public void createUI() {
		setLayout(null);
		addQueryComponent(this);
	}

	private void addQueryComponent(JPanel panel) {

		int presety = (600 - 100) / 2;
		int presetx = (800 - 100) / 2;
		List<Rental> rentList;
		JTable table;

		JButton DeleteButton = new JButton("일괄 삭제");
		DeleteButton.setBounds(25, 50, 120, 30);
		add(DeleteButton);
		

		JLabel tableTitle = new JLabel("예약 목록");
		tableTitle.setBounds(presetx, 25, 100, 40);
		add(tableTitle);
		try {
			rentList = ac.userReservationQueryController().hendleQuery(ac.appCoordinator.getUser());
			
			String[] columnNames = {"삭제", "수정", "상세","정비", "모델명", "시작일", "기간", "가격","추가요금","추가요금내역", "납입기한"};
			Object[][] data = new Object[rentList.size()][11];
	
			for (int i = 0; i < rentList.size(); i++) {
				CampingCar car = ac.userReservationQueryController().findCarById(rentList.get(i).getCar_id());
				data[i][0] = Boolean.FALSE;
				data[i][1] = "[수정]";
				data[i][2] = "[조회]";
				data[i][3] = "[의뢰]";
				data[i][4] = car.getName();
				data[i][5] = rentList.get(i).getStart_date();
				data[i][6] = rentList.get(i).getRental_period();
				data[i][7] = rentList.get(i).getTotal_charge();
				data[i][8] = rentList.get(i).getExtra_charge();
				data[i][9] = rentList.get(i).getExtra_charge_detail();
				data[i][10] = rentList.get(i).getDue_date();
				
			}
	
			table = new JTable(data, columnNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 0;
				}
				 @Override
				 public Class<?> getColumnClass(int column) {
				    if (column == 0) {
				        return Boolean.class; // 체크박스 열
				    } else {
				        return String.class;
				    }
				}
			};
	
			table.getColumn("삭제").setPreferredWidth(40);
			table.getColumn("수정").setPreferredWidth(40);
			table.getColumn("상세").setPreferredWidth(40);
			table.getColumn("정비").setPreferredWidth(40);
			
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds((800-750)/2, 100, 750, 450);
			
			add(scrollPane);
	
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int row = table.rowAtPoint(e.getPoint());
			        int col = table.columnAtPoint(e.getPoint());
			        
			        if (row >= 0) {
			            String columnName = table.getColumnName(col);

			            if ("상세".equals(columnName)) {
			                // 조회 버튼처럼 동작
			            	CampingCar car = ac.userReservationQueryController().findCarById(rentList.get(row).getCar_id());
			            	byte[] imgBytes = car.getImage();
			            	JLabel imageLabel = null;
			            	try {
			            		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imgBytes));
			                    ImageIcon icon = new ImageIcon(bufferedImage.getScaledInstance(300, 200, java.awt.Image.SCALE_SMOOTH));
			                    imageLabel = new JLabel(icon);
			            	}catch(Exception ex) {
			            		
			            		//GlobalExceptionHandler.handle(ex);
			            		//이미지 라벨을 불러오지 못했을 경우에는 그냥 표시하지 않기 떄문에 별개의 헨들러 필요 X
			            	}
			            	Object[] message = {
			            			"상세조회: " + car.getName() ,
					                "\n번호판: " + car.getPlate_number(),
					                "\n승차인원: " + car.getCapacity()+"명",
					                "\n이미지: " + (imageLabel == null ? "이미지 없음" : ""),
					                imageLabel,
					                "\n가격: " + car.getRental_price() +"원/1일",
					                "\n회사명: " + ac.userReservationQueryController().findCompanyById(car.getCompany_id()).getName(),
					                "\n상세정보: " + car.getDescription()
			            	};
			                JOptionPane.showMessageDialog(null,message);
			                 

			            } else if ("수정".equals(columnName)) {
			                // 수정 버튼처럼 동작
			                //ac.appCoordinator().showUserReservationModifyView(rentList.get(row), ac.userReservationQueryController().findCarById(rentList.get(row).getCar_id()).getName());
			            	ac.userReservationQueryController().onSelectedModify(rentList.get(row));
			            } else if("삭제".equals(columnName)) {
			            	//삭제 버튼처럼 동작
			            } else if("정비".equals(columnName)){
			            	ac.userReservationQueryController().onSelectedRepair(rentList.get(row), ac.appCoordinator().getUser());
			            }
			        }
				}
			});
			
			table.getModel().addTableModelListener(e -> {
				if(this.eventFlag == false) return;
				
	            if (e.getColumn() == 0) {
	            	this.eventFlag = false;
	            	if(LocalDate.now().isAfter(rentList.get(e.getFirstRow()).getStart_date().toLocalDate())) {
	            		boolean current = (Boolean) table.getValueAt(e.getFirstRow(), 0);
	            		table.setValueAt(!current, e.getFirstRow(), 0);
	            		JOptionPane.showMessageDialog(null, "이미 대여가 진행된 예약은 삭제할 수 없습니다!");
	            	}
	                this.eventFlag = true;
	            }
	        });
			
			DeleteButton.addActionListener(e -> {
	        	try {
	        		int result = JOptionPane.showConfirmDialog(
							null, "삭제를 확정하시겠습니까?","삭제 확인",JOptionPane.YES_NO_OPTION
							);
							
					if(result == JOptionPane.YES_OPTION) {
						List<Rental> selected = new ArrayList<>();
						for (int i = 0; i < table.getRowCount(); i++) {
						    Boolean isChecked = (Boolean) table.getValueAt(i, 0);
						    if (Boolean.TRUE.equals(isChecked)) {
						        
						        selected.add(rentList.get(i)); 
						    }
						}
						int res = ac.userReservationQueryController().onSelectDelete(selected);
						if(res == 0) {
							ac.appCoordinator().showUserReservationQueryView();
							JOptionPane.showMessageDialog(null, "모든 예약의 삭제에 성공했습니다.");
						}
						else if(res == -1) {
							JOptionPane.showMessageDialog(null, "선택된 예약이 없습니다.");
						}
						else {
							JOptionPane.showMessageDialog(null, "이미 대여가 진행된" + res +"개의 예약을 제외하고 나머지 일정을 삭제하는데 성공했습니다.");
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "작업을 취소하셨습니다.");
					}
	        	} catch(Exception ex) {
	        		GlobalExceptionHandler.handle(ex);
	        	}
	        });
		}catch(Exception ex) {
			GlobalExceptionHandler.handle(ex);
		}
	}
	
}
