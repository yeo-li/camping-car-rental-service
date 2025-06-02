package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import dbcar.main.java.com.dbshindong.dbcar.application.UserReservationQueryService;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCarCompany;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class UserReservationQueryController {
	private UserReservationQueryService userReservationQueryService;
	private AppConfig ac = AppConfig.getInstance();
	private AppCoordinator coordinator;
	
	public UserReservationQueryController(UserReservationQueryService userReservationQueryService, AppCoordinator coordinator) {
		this.userReservationQueryService = userReservationQueryService;
		this.coordinator = coordinator;
	}
	
	public List<Rental> hendleQuery(String username){
		try {
			return ac.userReservationQueryService().findRentByCustomer(username);
		} catch(Exception e) {
			throw e;
		}
	}
	public CampingCar findCarById(int id){
		try {
			return ac.userReservationQueryService().findCarById(id);
		} catch(Exception e) {
			throw e;
		}
	}
	public CampingCarCompany findCompanyById(int id) {
		return ac.userReservationQueryService().findCompanyById(id);
	}
	
	public void onSelectedModify(Rental target) {
		try {
			LocalDate start = target.getStart_date().toLocalDate();
			LocalDate end = target.getStart_date().toLocalDate().plusDays(target.getRental_period() - 1);
			if(end.isBefore(LocalDate.now())){
				JOptionPane.showMessageDialog(null,"이미 반납이 끝난 캠핑카에 대해서는 수정할 수 없습니다.");
			}
			else if((start.isEqual(LocalDate.now()) || start.isBefore(LocalDate.now())) && (end.isAfter(LocalDate.now()) || (start.isEqual(LocalDate.now())))) {
				JOptionPane.showMessageDialog(null,"현재 대여중인 캠핑카에 대해서는 수정할 수 없습니다.");
			}
			else {
				ac.appCoordinator().showUserReservationModifyView(target, findCarById(target.getCar_id()).getName());
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	public int onSelectDelete(List<Rental> target) {
		try {
			LocalDate now = LocalDate.now();
			if(target.size() == 0) return -1;
			int cnt = 0;
			for(Rental rent : target) {
				if(rent.getStart_date().toLocalDate().isBefore(now)) {
					cnt += 1;
				}
				else {
					this.userReservationQueryService.deleteRental(rent);
				}
			}
			return cnt;
		} catch(Exception e) {
			throw e;
		}
	}
	public void onSelectedRepair(Rental rent, String id) {
		LocalDate start = rent.getStart_date().toLocalDate();
		LocalDate end = rent.getStart_date().toLocalDate().plusDays(rent.getRental_period() - 1);
		LocalDate fix = end;
		LocalDate today = LocalDate.now();
		try {
			boolean flag = !(today.isAfter(fix) || today.isBefore(start));
			if(flag) ac.appCoordinator().showUserRequestRepairView(rent, id);
			else {
				JOptionPane.showMessageDialog(null, "현재 대여중인 캠핑카에 대해서만 수리를 신청할 수 있습니다.");
			}
		} catch(Exception e) {
			throw e;
		}
	}
}
