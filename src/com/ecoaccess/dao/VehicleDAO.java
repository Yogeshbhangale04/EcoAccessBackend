package com.ecoaccess.dao;

import com.ecoaccess.model.Vehicle;
import com.ecoaccess.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
    public boolean save(Vehicle v){return change("INSERT INTO vehicles (id,station_id,quantity) VALUES (?,?,?)",v,false);}
    public boolean update(Vehicle v){return change("UPDATE vehicles SET station_id=?,quantity=? WHERE id=?",v,true);}
    private boolean change(String sql,Vehicle v,boolean update){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){if(update){p.setString(1,v.getStationId());p.setInt(2,v.getQuantity());p.setString(3,v.getId());}else{p.setString(1,v.getId());p.setString(2,v.getStationId());p.setInt(3,v.getQuantity());}return p.executeUpdate()>0;}catch(SQLException e){e.printStackTrace();return false;}}
    public Vehicle findById(String id){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT id,station_id,quantity FROM vehicles WHERE id=?")){p.setString(1,id);try(ResultSet r=p.executeQuery()){return r.next()?map(r):null;}}catch(SQLException e){e.printStackTrace();return null;}}
    public List<Vehicle> findAll(){List<Vehicle> out=new ArrayList<>();try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT id,station_id,quantity FROM vehicles ORDER BY station_id");ResultSet r=p.executeQuery()){while(r.next())out.add(map(r));}catch(SQLException e){e.printStackTrace();}return out;}
    public boolean delete(String id){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM vehicles WHERE id=?")){p.setString(1,id);return p.executeUpdate()>0;}catch(SQLException e){e.printStackTrace();return false;}}
    public int getAvailableQuantity(String stationId){Vehicle v=findByStation(stationId);return v==null?0:v.getQuantity();}
    private Vehicle findByStation(String stationId){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT id,station_id,quantity FROM vehicles WHERE station_id=?")){p.setString(1,stationId);try(ResultSet r=p.executeQuery()){return r.next()?map(r):null;}}catch(SQLException e){e.printStackTrace();return null;}}
    private Vehicle map(ResultSet r)throws SQLException{return new Vehicle(r.getString("id"),r.getString("station_id"),r.getInt("quantity"));}
}
