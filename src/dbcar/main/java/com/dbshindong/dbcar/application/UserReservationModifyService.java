package dbcar.main.java.com.dbshindong.dbcar.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;

public class UserReservationModifyService {
	RentalRepository rentalRepository;
	CampingCarRepository campingCarRepository;
	CustomerRepository customerRepository;
	public UserReservationModifyService(RentalRepository rentalRepository, CampingCarRepository campingCarRepository, CustomerRepository customerRepository) {
		this.rentalRepository = rentalRepository;
		this.campingCarRepository = campingCarRepository;
		this.customerRepository = customerRepository;
	}
	public List<Integer> findUnavailableCarId(Rental rent){
		try {
			List<Integer> availCars = new ArrayList<>();
			availCars = rentalRepository.findCarNotInPeriod(rent);
			return availCars;
		} catch(Exception e) {
			throw e;
		}
	}
	
	public List<Rental> findRentByCarId(int id){
		try {
			return this.rentalRepository.findByCarId(id);
		} catch(Exception e) {
			throw e;
		}
	}
	public CampingCar findCarById(int id) {
		try {
			return this.campingCarRepository.findById(id);
		} catch(Exception e) {
			throw e;
		}
	}
	public List<CampingCar> findAllCars() {
		try {
			return this.campingCarRepository.findAll();
		} catch(Exception e) {
			throw e;
		}
	}
	
	public int findCustomerById(String id) {
		try {
			return this.customerRepository.findByUsername(id).getFirst().getCustomer_id();
		} catch(Exception e) {
			throw e;
		}
	}
}
