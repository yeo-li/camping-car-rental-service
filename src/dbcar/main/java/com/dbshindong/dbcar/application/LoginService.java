package dbcar.main.java.com.dbshindong.dbcar.application;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;


public class LoginService {
	private final CustomerRepository customerRepository;
	
	public LoginService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public boolean login(String userId, String password) {
		boolean loginResult = isExistUser(userId, password);
		return loginResult;
	}
	
	public boolean isExistUser(String userId, String password) {
		List<Customer> res = customerRepository.findByUsername(userId);
		
		for(Customer custmer : res) {
			if(custmer.getPassword().equals(password)) {
				System.out.println(custmer.getPassword());
				return true;
			}
		}
		return false;
		
	}
	
}
