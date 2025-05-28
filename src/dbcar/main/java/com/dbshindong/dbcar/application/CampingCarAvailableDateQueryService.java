package dbcar.main.java.com.dbshindong.dbcar.application;

import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;

public class CampingCarAvailableDateQueryService {
	private final RentalRepository rentalRepository;
	private final AppConfig ac = AppConfig.getInstance();
	
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
	public void insertRental(int carid, List<LocalDate> target) {
		LocalDate start = target.get(0);
		LocalDate end = target.get(0);
		LocalDate prev = null;
		CampingCar car = ac.campingCarRepository().findById(carid);
		Customer me = ac.customerRepository().findByUsername(ac.appCoordinator().getUser()).getFirst();
		
		int car_id = carid;
		int customer_id = me.getCustomer_id();
		int company_id = car.getCompany_id();
		Date start_date;
		int rental_period = 1;
		int total_charge = car.getRental_price();
		Date due_date;
		String extra_charge_detail = "";
		Integer extra_charge = 0;
		
		Rental rent;
		
		for (int i = 1; i <= target.size(); i++) {
		    boolean isEnd = i == target.size();
		    boolean isBreak = !isEnd && !target.get(i - 1).plusDays(1).equals(target.get(i));

		    if (isBreak || isEnd) {
		        // 저장
		        start_date = Date.valueOf(start);
		        due_date = Date.valueOf(end);
		        rent = new Rental(car_id, customer_id, company_id, start_date, rental_period, total_charge * rental_period, due_date, extra_charge_detail, extra_charge);
		        ac.rentalRepository().save(rent);

		        if (!isEnd) {
		            start = target.get(i);
		            end = start;
		            rental_period = 1;
		        }
		    } else {
		        end = target.get(i);
		        rental_period++;
		    }
		}
	}
}
