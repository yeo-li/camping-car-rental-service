package dbcar.main.java.com.dbshindong.dbcar.domain.company.exception;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.ConstraintViolationException;

public class InvalidCampingCarCompanyException extends ConstraintViolationException {

	public InvalidCampingCarCompanyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public InvalidCampingCarCompanyException(String message, Exception e) {
		super(message, e);
		// TODO Auto-generated constructor stub
	}

}
