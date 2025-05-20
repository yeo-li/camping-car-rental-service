package dbcar.main.java.com.dbshindong.dbcar.infrastructure.company;

import java.sql.*;
import java.util.*;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCarCompany;

public class CampingCarCompanyRepository {
    private final Connection conn;

    public CampingCarCompanyRepository(Connection conn) {
        this.conn = conn;
    }

    public List<CampingCarCompany> findAll() {
        List<CampingCarCompany> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM CampingCarCompany";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CampingCarCompany company = new CampingCarCompany(
                    rs.getInt("company_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("manager_name"),
                    rs.getString("manager_email")
                );
                list.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public CampingCarCompany findById(int id) {
        try {
            String sql = "SELECT * FROM CampingCarCompany WHERE company_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new CampingCarCompany(
                    rs.getInt("company_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("manager_name"),
                    rs.getString("manager_email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(CampingCarCompany company) {
        try {
            String sql = "INSERT INTO CampingCarCompany (name, address, phone, manager_name, manager_email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, company.getName());
            pstmt.setString(2, company.getAddress());
            pstmt.setString(3, company.getPhone());
            pstmt.setString(4, company.getManager_name());
            pstmt.setString(5, company.getManager_email());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            String sql = "DELETE FROM CampingCarCompany WHERE company_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, CampingCarCompany company) {
        try {
            String sql = "UPDATE CampingCarCompany SET name = ?, address = ?, phone = ?, manager_name = ?, manager_email = ? WHERE company_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, company.getName());
            pstmt.setString(2, company.getAddress());
            pstmt.setString(3, company.getPhone());
            pstmt.setString(4, company.getManager_name());
            pstmt.setString(5, company.getManager_email());
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}