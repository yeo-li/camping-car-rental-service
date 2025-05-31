package dbcar.main.java.com.dbshindong.dbcar.application;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairRecordRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.repair.external.ExternalRepairShopRepository;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;

public class UserRequestRepairService {
	private ExternalRepairRecordRepository externalRepairRecordRepository;
	private ExternalRepairShopRepository externalRepairShopRepository; 
	private CustomerRepository customerRepository;
	
	private AppConfig ac = AppConfig.getInstance();
	
	
	public UserRequestRepairService(ExternalRepairRecordRepository externalRepairRecordRepository, ExternalRepairShopRepository externalRepairShopRepository, CustomerRepository customerRepository) {
		this.externalRepairRecordRepository = externalRepairRecordRepository;
		this.externalRepairShopRepository = externalRepairShopRepository;
		this.customerRepository = customerRepository;
	}
	
	public List<ExternalRepairShop> findAllShop() {
		try {
		return this.externalRepairShopRepository.findAll();
		} catch(Exception e) {
			throw e;
		}
	}
	
	public List<ExternalRepairRecord> findRecordByCarAndCustomer(int carid, int customerid){
		try {
			return this.externalRepairRecordRepository.findByCarAndCustomer(carid, customerid);
		} catch(Exception e) {
			throw e;
		}
	}
	public void saveRecord(ExternalRepairRecord addNew) {
		try {
			this.externalRepairRecordRepository.save(addNew);
		} catch(Exception e) {
			throw e;
		}
	}
	public Customer findCustomerByUserId(String userId) {
		try {
			return this.customerRepository.findByUsername(userId).getFirst();//하난 있겠지
		} catch(Exception e) {
			throw e;
		}
	}
}
