package dbcar.main.java.com.dbshindong.dbcar.application;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;

public class UserReservationQueryService {
	private RentalRepository rentalRepository;
	private CampingCarRepository campingCarRepository;
	private CustomerRepository customerRepository;
	private AppConfig ac = AppConfig.getInstance();
	
	public UserReservationQueryService(RentalRepository rentalRepository,CampingCarRepository campingCarRepository, CustomerRepository customerRepository) {
		this.rentalRepository = rentalRepository;
		this.campingCarRepository = campingCarRepository;
		this.customerRepository = customerRepository;
	}
	
	public List<Rental> findRentByCustomer(String username) {
		try {
			return ac.rentalRepository().findByUserId(customerRepository.findByUsername(username).getFirst().getCustomer_id());
		} catch(Exception e) {
			throw e;
		}
	}
	public CampingCar findCarById(int id) {
		try {
			return ac.campingCarRepository().findById(id);
		} catch(Exception e) {
			throw e;
		}
	}
}
