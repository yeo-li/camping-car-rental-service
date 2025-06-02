package dbcar.main.java.com.dbshindong.dbcar.application;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.exception.InvalidCustomerException;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;

public class LoginService {
	private final CustomerRepository customerRepository;

	public LoginService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer login(String userId, String password) {
		try {
			return findValidUser(userId, password);
		} catch(InvalidCustomerException e) {
			throw(e);
		}
	}

	public Customer findValidUser(String userId, String password) throws InvalidCustomerException {
		
		try {
		List<Customer> res = customerRepository.findByUsername(userId);

		for (Customer customer : res) {
			if (customer.getPassword().equals(password)) {
				System.out.println(customer.getPassword());
				return customer;
			}
		}
		return null;
		}
		catch(InvalidCustomerException e){
			throw(e);
		}
		
	}

}
