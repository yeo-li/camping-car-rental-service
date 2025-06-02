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
			boolean flag = true;
			for(ExternalRepairRecord r : res) {
				if(r.getRepair_date().toLocalDate().isEqual(LocalDate.now()) || r.getRepair_date().toLocalDate().isBefore(LocalDate.now()) && r.getDue_date().toLocalDate().isAfter(LocalDate.now())) {
					flag = false;
					break;
				}
			}
			if(flag) {
				ExternalRepairRecord addNew = new ExternalRepairRecord(
						(Integer) rent.getCar_id(),
						(Integer) id,
						(Integer) rent.getCompany_id(),
						(Integer) user.getCustomer_id(),
						"정비내역 추가 요망",
						LocalDate.now().toString(),
						(Integer) 0,
						LocalDate.now().toString(),
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
