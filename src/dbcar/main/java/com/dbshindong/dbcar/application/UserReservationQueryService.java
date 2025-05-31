package dbcar.main.java.com.dbshindong.dbcar.application;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCarCompany;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarCompanyRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;

public class UserReservationQueryService {
	private RentalRepository rentalRepository;
	private CampingCarRepository campingCarRepository;
	private CustomerRepository customerRepository;
	private CampingCarCompanyRepository campingCarCompanyRepository;
	
	private AppConfig ac = AppConfig.getInstance();
	
	public UserReservationQueryService(RentalRepository rentalRepository,CampingCarRepository campingCarRepository, CustomerRepository customerRepository, CampingCarCompanyRepository campingCarCompanyRepository) {
		this.rentalRepository = rentalRepository;
		this.campingCarRepository = campingCarRepository;
		this.customerRepository = customerRepository;
		this.campingCarCompanyRepository = campingCarCompanyRepository;
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
	
	public CampingCarCompany findCompanyById(int id) {
		try {
			return ac.campingCarCompanyRepository().findById(id);
		} catch(Exception e) {
			throw e;
		}
	}
	
	public void deleteRental(List<Rental> targets) {
		try {
			for(int i = 0; i < targets.size(); i++) {
				ac.rentalRepository().delete(targets.get(i).getRental_id());
			}
		} catch(Exception e) {
			throw e;
		}
	}
}
