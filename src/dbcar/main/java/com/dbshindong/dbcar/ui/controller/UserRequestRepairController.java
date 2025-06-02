package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import java.time.LocalDate;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.application.UserRequestRepairService;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class UserRequestRepairController {
	UserRequestRepairService userRequestRepairService;
	AppCoordinator coordinator;
	
	public UserRequestRepairController(UserRequestRepairService userRequestRepairService, AppCoordinator coordinator) {
		this.userRequestRepairService = userRequestRepairService;
		this.coordinator = coordinator;
	}
	
	public List<ExternalRepairShop> findAllShop() {
		try {
		return this.userRequestRepairService.findAllShop();
		} catch(Exception e) {
			throw e;
		}
	}
	
	public boolean onShopSelected(int id, Rental rent, String userId) {
		try {
			Customer user = this.userRequestRepairService.findCustomerByUserId(userId);
			List<ExternalRepairRecord> res = this.userRequestRepairService.findRecordByCarAndCustomer(rent.getCar_id(), user.getCustomer_id());
			LocalDate start = rent.getStart_date().toLocalDate();
			LocalDate end = rent.getStart_date().toLocalDate().plusDays(rent.getRental_period() - 1);
			LocalDate fix = end;
			LocalDate today = LocalDate.now();
			boolean flag = !(today.isAfter(fix) || today.isBefore(start));
			for(ExternalRepairRecord r : res) {
				 if (r.getCar_id() == rent.getCar_id()) {
				        if (r.getRepair_date().toLocalDate().isEqual(fix)) {
				        	flag = false;
				        }; //이미 존재
				 }
			}
			if(flag) {
				ExternalRepairRecord addNew = new ExternalRepairRecord(
						(Integer) rent.getCar_id(),
						(Integer) id,
						(Integer) rent.getCompany_id(),
						(Integer) user.getCustomer_id(),
						"정비내역 추가 요망",
						rent.getStart_date().toLocalDate().plusDays(rent.getRental_period() - 1).toString(),
						(Integer) 0,
						rent.getStart_date().toLocalDate().plusDays(rent.getRental_period() - 1).toString(),
						""
						);
				this.userRequestRepairService.saveRecord(addNew);
			}
			return flag;
		} catch(Exception e) {
			throw e;
		}
	}
}
