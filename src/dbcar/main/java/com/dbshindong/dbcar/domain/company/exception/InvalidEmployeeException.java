package dbcar.main.java.com.dbshindong.dbcar.domain.company.exception;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.ConstraintViolationException;

public class InvalidEmployeeException extends ConstraintViolationException {

	public InvalidEmployeeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
