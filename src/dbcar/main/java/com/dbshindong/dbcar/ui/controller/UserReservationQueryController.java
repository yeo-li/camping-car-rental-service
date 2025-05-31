package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import java.util.List;

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
	public void onSelectDelete(List<Rental> targets) {
		try {
			ac.userReservationQueryService().deleteRental(targets);
		} catch(Exception e) {
			throw e;
		}
	}
	
	public void onSelectedModify(Rental target) {
		try {
			ac.appCoordinator().showUserReservationModifyView(target);
		} catch(Exception e) {
			throw e;
		}
	}
	
}
