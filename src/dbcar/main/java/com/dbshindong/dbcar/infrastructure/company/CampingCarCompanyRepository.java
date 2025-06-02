package dbcar.main.java.com.dbshindong.dbcar.infrastructure.company;

import java.sql.*;
import java.util.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataDeleteException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataInsertException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.DataUpdateException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.InvalidQueryException;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.RepositoryException;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCarCompany;

public class CampingCarCompanyRepository {

	private final AppConfig ac = AppConfig.getInstance();

	public List<CampingCarCompany> findAll() {
		List<CampingCarCompany> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM CampingCarCompany";
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CampingCarCompany company = new CampingCarCompany(rs.getInt("company_id"), rs.getString("name"),
						rs.getString("address"), rs.getString("phone"), rs.getString("manager_name"),
						rs.getString("manager_email"));
				list.add(company);
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return list;
	}

	public CampingCarCompany findById(int id) throws RepositoryException {
		try {
			String sql = "SELECT * FROM CampingCarCompany WHERE company_id = ?";
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return new CampingCarCompany(rs.getInt("company_id"), rs.getString("name"), rs.getString("address"),
						rs.getString("phone"), rs.getString("manager_name"), rs.getString("manager_email"));
			}
		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return null;
	}

	public List<CampingCarCompany> findByCondition(String condition) throws RepositoryException {
		List<CampingCarCompany> companies = new ArrayList<>();

		try {
			String sql = "SELECT * FROM CampingCarCompany WHERE " + condition;
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				int companyId = rs.getInt("company_id");
				String name = rs.getString("name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");
				String managerName = rs.getString("manager_name");
				String managerEmail = rs.getString("manager_email");

				CampingCarCompany company = new CampingCarCompany(companyId, name, address, phone, managerName,
						managerEmail);
				companies.add(company);
			}

		} catch (SQLException e) {
			if (e.getSQLState() != null && e.getSQLState().startsWith("42")) {
				throw new InvalidQueryException("SQL 문법 오류입니다.", e);
			}
			throw new InvalidQueryException("DB 오류입니다.", e);
		}

		return companies;
	}

	public void save(CampingCarCompany company) throws RepositoryException {
		try {
			String sql = "INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getAddress());
			pstmt.setString(3, company.getPhone());
			pstmt.setString(4, company.getManager_name());
			pstmt.setString(5, company.getManager_email());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				throw new DataInsertException("데이터 저장에 실패했습니다.");
			}
		} catch (SQLException e) {
			throw new DataInsertException("데이터 저장 중 오류가 발생했습니다.", e);
		}
	}

	public void delete(int id) {
		try {
			String sql = "DELETE FROM CampingCarCompany WHERE company_id = ?";
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataDeleteException("삭제 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataDeleteException("데이터 삭제 중 오류가 발생했습니다.", e);
		}
	}

	public void update(int id, CampingCarCompany company) {
		try {
			String sql = "UPDATE CampingCarCompany SET name = ?, address = ?, phone = ?, manager_name = ?, manager_email = ? WHERE company_id = ?";
			PreparedStatement pstmt = ac.dbConnection().getConnection().prepareStatement(sql);
			pstmt.setString(1, company.getName());
			pstmt.setString(2, company.getAddress());
			pstmt.setString(3, company.getPhone());
			pstmt.setString(4, company.getManager_name());
			pstmt.setString(5, company.getManager_email());
			pstmt.setInt(6, id);

			int result = pstmt.executeUpdate();

			if (result == 0) {
				throw new DataUpdateException("업데이트 대상이 존재하지 않습니다.");
			}

		} catch (SQLException e) {
			throw new DataUpdateException("데이터 업데이트 중 오류가 발생했습니다.", e);
		}
	}
}