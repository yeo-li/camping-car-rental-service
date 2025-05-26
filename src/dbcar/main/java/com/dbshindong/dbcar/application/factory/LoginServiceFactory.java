package dbcar.main.java.com.dbshindong.dbcar.application.factory;

import java.sql.Connection;

import dbcar.main.java.com.dbshindong.dbcar.application.LoginService;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.CustomerRepository;

public class LoginServiceFactory {
    public static LoginService create(String DBId, String password) {
    	System.out.println(DBId + password);
    	DBConnection dc = new DBConnection(DBId,password); 
    	Connection conn = DBConnection.getConnection();
    	if (conn == null) {
            throw new IllegalStateException("DB 연결 실패");
        }
    	
    	return new LoginService(new CustomerRepository(conn));
        
    }
}