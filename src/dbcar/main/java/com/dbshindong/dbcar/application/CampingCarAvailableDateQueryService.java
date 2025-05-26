package dbcar.main.java.com.dbshindong.dbcar.application;

import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;

public class CampingCarAvailableDateQueryService {
	private final RentalRepository rentalRepository;
	
	public CampingCarAvailableDateQueryService(RentalRepository rentalRepository) {
		this.rentalRepository = rentalRepository;
	}
	
	public List<LocalDate> findRentalDateById(int id, LocalDate today){
		List<Rental> queryResult = rentalRepository.findByCarId(id);
		List<LocalDate> avail = new ArrayList<LocalDate>();
		
		for (int day = 0; day < 31; day++) {
	        LocalDate targetDate = today.plusDays(day);
	        boolean isAvailable = true;

	        for (Rental rental : queryResult) {
	            LocalDate start = rental.getStart_date().toLocalDate();
	            LocalDate end = rental.getDue_date().toLocalDate();

	            // 예약된 구간 안에 targetDate가 포함된다면 사용 불가능
	            if ((targetDate.isEqual(start) || targetDate.isAfter(start)) &&
	                (targetDate.isEqual(end) || targetDate.isBefore(end))) {
	                isAvailable = false;
	                break;
	            }
	        }

	        if (isAvailable) {
	            avail.add(targetDate);
	        }
		}
		return avail;
	}
	public void insertRental(int car_id, List<LocalDate> date, Customer me) {
		
	}
}
