package dbcar.main.java.com.dbshindong.dbcar.application;

import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;

public class LoginService {
	private final CustomerRepository customerRepository;

	public LoginService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer login(String userId, String password) {
		return findValidUser(userId, password);
	}

	public Customer findValidUser(String userId, String password) {
		List<Customer> res = customerRepository.findByUsername(userId);

		for (Customer customer : res) {
			if (customer.getPassword().equals(password)) {
				System.out.println(customer.getPassword());
				return customer;
			}
		}
		return null;
	}

}
