package dbcar.main.java.com.dbshindong.dbcar.domain.customer.exception;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.ConstraintViolationException;

public class InvalidCustomerException extends ConstraintViolationException {

	public InvalidCustomerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidCustomerException(String message, Exception e) {
		super(message, e);
		// TODO Auto-generated constructor stub
	}

}
