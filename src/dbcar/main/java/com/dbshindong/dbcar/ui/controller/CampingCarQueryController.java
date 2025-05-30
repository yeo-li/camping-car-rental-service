package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarQueryService;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class CampingCarQueryController {
	private final CampingCarQueryService campingCarQueryService;
	private final AppCoordinator coordinator;
	
	public CampingCarQueryController(CampingCarQueryService campingCarQueryService, AppCoordinator coordinator) {
		this.campingCarQueryService = campingCarQueryService;
		this.coordinator = coordinator;
	}
	
	public List<CampingCar> handleQuery(){
		try {
			return campingCarQueryService.CampingCarQuery();
		}catch(Exception ex) {
			throw(ex);
		}
	}
	public void onCarSelected(int id) {
		try {
			this.coordinator.showCampingCarAvailableDateQueryView(id);
		}catch(Exception ex) {
			throw(ex);
		}
	}
}
