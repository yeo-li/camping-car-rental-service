package dbcar.main.java.com.dbshindong.dbcar.application;

import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;


public class LoginService {
	private final CustomerRepository customerRepository;
	
	public LoginService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public boolean login(String userId, String password) {
		boolean loginResult = customerRepository.isExistUser(userId, password);
		return loginResult;
	}
	
}
