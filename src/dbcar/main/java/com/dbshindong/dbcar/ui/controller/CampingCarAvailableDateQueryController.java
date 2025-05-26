package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import java.time.LocalDate;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarAvailableDateQueryService;

public class CampingCarAvailableDateQueryController {
	private final CampingCarAvailableDateQueryService campingCarAvailableDateQueryService;
	
	public CampingCarAvailableDateQueryController(CampingCarAvailableDateQueryService campingCarAvailableDateService) {
		this.campingCarAvailableDateQueryService = campingCarAvailableDateService;
	}
	
	public List<LocalDate> handleQuery(int car_id, LocalDate today){
		return campingCarAvailableDateQueryService.findRentalDateById(car_id, today);
	}
	
	
}
