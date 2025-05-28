package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import java.time.LocalDate;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarAvailableDateQueryService;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class CampingCarAvailableDateQueryController {
	private final CampingCarAvailableDateQueryService campingCarAvailableDateQueryService;
	private final AppCoordinator coordinator;
	
	
	public CampingCarAvailableDateQueryController(CampingCarAvailableDateQueryService campingCarAvailableDateService, AppCoordinator coordinator) {
		this.campingCarAvailableDateQueryService = campingCarAvailableDateService;
		this.coordinator = coordinator;
	}
	
	public List<LocalDate> handleQuery(int car_id){
		return campingCarAvailableDateQueryService.findRentalDateById(car_id, LocalDate.now());
	}
	
	public Boolean saveReservation(List<LocalDate> target, int car_id) {
		
		if(target.size() == 0) return false;
		
		try {
		this.campingCarAvailableDateQueryService.insertRental(car_id, target);
		return true;
		} catch(Exception e){
		return false;
		}
	}
}
