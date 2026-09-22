package com.ecoaccess.dao;

import com.ecoaccess.model.Staff;
import com.ecoaccess.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    public boolean save(Staff s) {
        String sql = "INSERT INTO staff (id, employee_id, name, password, role, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,s.getId()); p.setString(2,s.getEmployeeId()); p.setString(3,s.getName());
            p.setString(4,s.getPassword()); p.setString(5,s.getRole()); p.setString(6,s.getStatus());
            return p.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public Staff findById(String id) { return findOne("id", id); }
    public Staff findByEmployeeId(String employeeId) { return findOne("employee_id", employeeId); }
    private Staff findOne(String column, String value) {
        String sql="SELECT id,employee_id,name,password,role,status FROM staff WHERE "+column+"=?";
        try (Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,value); try(ResultSet r=p.executeQuery()) { return r.next()?map(r):null; }
        } catch(SQLException e){e.printStackTrace(); return null;}
    }
    public List<Staff> findAll() {
        List<Staff> out=new ArrayList<>();
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("SELECT id,employee_id,name,password,role,status FROM staff ORDER BY employee_id"); ResultSet r=p.executeQuery()) {
            while(r.next()) out.add(map(r));
        } catch(SQLException e){e.printStackTrace();} return out;
    }
    public boolean update(Staff s) {
        String sql="UPDATE staff SET employee_id=?,name=?,password=?,role=?,status=?,updated_at=CURRENT_TIMESTAMP WHERE id=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,s.getEmployeeId());p.setString(2,s.getName());p.setString(3,s.getPassword());p.setString(4,s.getRole());p.setString(5,s.getStatus());p.setString(6,s.getId()); return p.executeUpdate()>0;
        } catch(SQLException e){e.printStackTrace();return false;}
    }
    public boolean delete(String id) {
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM staff WHERE id=?")){p.setString(1,id);return p.executeUpdate()>0;}catch(SQLException e){e.printStackTrace();return false;}
    }
    public boolean existsByEmployeeId(String employeeId){return findByEmployeeId(employeeId)!=null;}
    private Staff map(ResultSet r)throws SQLException{return new Staff(r.getString("id"),r.getString("employee_id"),r.getString("name"),r.getString("password"),r.getString("role"),r.getString("status"));}
}
