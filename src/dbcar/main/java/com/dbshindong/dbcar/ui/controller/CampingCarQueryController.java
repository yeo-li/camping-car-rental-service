package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarQueryService;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;

public class CampingCarQueryController {
	private final CampingCarQueryService campingCarQueryService;
	
	public CampingCarQueryController(CampingCarQueryService campingCarQueryService) {
		this.campingCarQueryService = campingCarQueryService;
	}
	
	public List<CampingCar> handleQuery(){
		return campingCarQueryService.CampingCarQuery();
	}
}
