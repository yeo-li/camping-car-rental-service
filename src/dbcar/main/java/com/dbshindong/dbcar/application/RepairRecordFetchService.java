package dbcar.main.java.com.dbshindong.dbcar.application;

import java.sql.SQLSyntaxErrorException;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.InternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.Part;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairShopRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.InternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.internal.PartRepository;

public class RepairRecordFetchService {
	private final CampingCarRepository campingCarRepository;
	private final ExternalRepairRecordRepository externalRepairRecordRepository;
	private final ExternalRepairShopRepository externalRepairShopRepository;
	private final InternalRepairRecordRepository internalRepairRecordRepository;
	private final PartRepository partRepository;

	public RepairRecordFetchService(CampingCarRepository campingCarRepository,
			ExternalRepairRecordRepository externalRepairRecordRepository,
			ExternalRepairShopRepository externalRepairShopRepository,
			InternalRepairRecordRepository internalRepairRecordRepository, PartRepository partRepository) {
		this.campingCarRepository = campingCarRepository;
		this.externalRepairRecordRepository = externalRepairRecordRepository;
		this.externalRepairShopRepository = externalRepairShopRepository;
		this.internalRepairRecordRepository = internalRepairRecordRepository;
		this.partRepository = partRepository;
	}

	public List<CampingCar> fetchCampingCars() {
		return campingCarRepository.findAll();
	}

	public List<ExternalRepairRecord> fetchExternalRepairRecord(int carId) {

		return externalRepairRecordRepository.findByCondition("car_id = " + Integer.toString(carId));

	}

	public List<InternalRepairRecord> fetchInternalRepairRecord(int carId) {

		return internalRepairRecordRepository.findByCondition("car_id = " + Integer.toString(carId));

	}

	public ExternalRepairShop fetchExternalRepairShop(int shopId) {
		return externalRepairShopRepository.findById(shopId);
	}

	public Part fetchPart(int partId) {
		return partRepository.findById(partId);
	}

}
