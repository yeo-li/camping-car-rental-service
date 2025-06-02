package dbcar.main.java.com.dbshindong.dbcar.application;

import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;

public class CampingCarQueryService {
	private final CampingCarRepository campingCarRepository;
	
	public CampingCarQueryService(CampingCarRepository campingCarRepository) {
		this.campingCarRepository = campingCarRepository;
	}
	
	public List<CampingCar> CampingCarQuery() {
		try {
			List<CampingCar> queryResult = campingCarRepository.findAll();
		return queryResult;
		} catch(Exception ex) {
			throw(ex);
		}
	}
}
