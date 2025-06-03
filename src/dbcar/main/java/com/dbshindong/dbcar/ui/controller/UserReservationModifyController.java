package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dbcar.main.java.com.dbshindong.dbcar.application.UserReservationModifyService;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.DateAvailabilityRequest;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class UserReservationModifyController {
	UserReservationModifyService userReservationModifyService;
	AppCoordinator coordinator;
	public UserReservationModifyController(UserReservationModifyService userReservationModifyService, AppCoordinator coordinator) {
		this.userReservationModifyService = userReservationModifyService;
		this.coordinator = coordinator;
	}
	
	public List<Integer> findAvailableCarId(Rental rent){
		try {
			List<Integer> unableCarsId = this.userReservationModifyService.findUnavailableCarId(rent);
			List<CampingCar> allCars = this.userReservationModifyService.findAllCars();
			List<Integer> allCarsId = new ArrayList<>();
			
			for(CampingCar i : allCars) { 
				allCarsId.add(i.getCar_id());
			}
			
			Set<Integer> unableset = new HashSet<>(unableCarsId);
			for(int i : unableset) { 
				System.out.println(i);
			}
			Set<Integer>  ableset= new HashSet<>(allCarsId);
			ableset.removeAll(unableset);
			
			return new ArrayList<>(ableset);
		} catch(Exception e) {
			throw e;
		}
	}
	
	public List<DateAvailabilityRequest> findUnavailableDate(int id, String username, Rental selected){
		try {
			List<Rental> rentals = this.userReservationModifyService.findRentByCarId(id);//그 자동차의 대여이력 불러와서
		    
			rentals.sort(Comparator.comparing(Rental::getStart_date));
			int customer_id = this.userReservationModifyService.findCustomerById(username);
			LocalDate today = LocalDate.now();
		    List<LocalDate> dateRange = new ArrayList<>();
		    for (int i = 1; i <= 30; i++) {
		        dateRange.add(today.plusDays(i));
		    }
			List<DateAvailabilityRequest> result = new ArrayList<>();
		    int rentalIndex = 0;
			for (LocalDate date : dateRange) {
				boolean available = true;
			        // rentalList는 정렬되어 있으므로 현재 날짜보다 이전에 끝나는 예약은 건너뛰기
		        while (rentalIndex < rentals.size()) {
		            Rental r = rentals.get(rentalIndex);
		            LocalDate start = r.getStart_date().toLocalDate();
		            LocalDate end = start.plusDays(r.getRental_period());
			        if (end.isBefore(date)) {
		                rentalIndex++;
		                continue;
		            }
			        if (!date.isBefore(start) && date.isBefore(end) && !selected.getStart_date().toLocalDate().isEqual(start)) {
		                available = false;
		            }
			        break; // 이 날짜에 대한 판단이 끝났으면 다음 날짜로
		        }
		        result.add(new DateAvailabilityRequest(date, available));
		    }
			 
			for(int i = 0; i < result.size(); i++) {
				
			}
			return result;
		} catch(Exception e) {
			throw e;
		}
	}

	public CampingCar findCarById(int id) {
		try {
			return this.userReservationModifyService.findCarById(id);
		} catch(Exception e) {
			throw e;
		}
	}
	
	public boolean changeReservationCar(List<Integer>selected, Rental rent) {
		if(selected.size() == 0) return false;
		try {
			CampingCar newCar = this.userReservationModifyService.findCarById(selected.getFirst());
			
			Rental newRent = new Rental(selected.getFirst(), 
					(Integer) rent.getCustomer_id(),
					(Integer) rent.getCompany_id(),
					rent.getStart_date().toString(),
					(Integer) rent.getRental_period(),
					(Integer) newCar.getRental_price() * rent.getRental_period() + rent.getExtra_charge(),
					rent.getDue_date().toString(),
					rent.getExtra_charge_detail(),
					(Integer) rent.getExtra_charge());
			this.userReservationModifyService.changeReservation(rent.getRental_id(), newRent);
			return true;
		} catch(Exception e) {
			throw e;
		}
	}
	
	public boolean changeReservationDate(LocalDate start, LocalDate end, Rental rent) {
		if(start == null || end == null || start.isAfter(end)) return false;
		try {
			int days = (int) ChronoUnit.DAYS.between(start, end) + 1;
			CampingCar car = this.userReservationModifyService.findCarById(rent.getCar_id());
			
			
			Rental newRent = new Rental(
					(Integer) rent.getCar_id(), 
					(Integer) rent.getCustomer_id(),
					(Integer) rent.getCompany_id(),
					start.toString(),
					(Integer) days,
					(Integer) car.getRental_price() * days + rent.getExtra_charge(),
					start.plusDays(days - 1).toString(),
					rent.getExtra_charge_detail(),
					(Integer) rent.getExtra_charge());
			this.userReservationModifyService.changeReservation(rent.getRental_id(), newRent);
			return true;
		} catch(Exception e) {
			throw e;
		}
	}
}
