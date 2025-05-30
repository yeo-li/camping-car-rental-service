package dbcar.main.java.com.dbshindong.dbcar.domain.customer.exception;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.ConstraintViolationException;

public class InvalidRentalException extends ConstraintViolationException {

	public InvalidRentalException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidRentalException(String message, Exception e) {
		super(message, e);
		// TODO Auto-generated constructor stub
	}

}
