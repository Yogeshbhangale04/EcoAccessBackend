package com.ecoaccess.dao;

import com.ecoaccess.model.Wheelchair;
import com.ecoaccess.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WheelchairDAO {
    public boolean save(Wheelchair w){return change("INSERT INTO wheelchairs (id,station_id,quantity) VALUES (?,?,?)",w,false);}
    public boolean update(Wheelchair w){return change("UPDATE wheelchairs SET station_id=?,quantity=? WHERE id=?",w,true);}
    private boolean change(String sql,Wheelchair w,boolean update){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){if(update){p.setString(1,w.getStationId());p.setInt(2,w.getQuantity());p.setString(3,w.getId());}else{p.setString(1,w.getId());p.setString(2,w.getStationId());p.setInt(3,w.getQuantity());}return p.executeUpdate()>0;}catch(SQLException e){e.printStackTrace();return false;}}
    public Wheelchair findById(String id){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT id,station_id,quantity FROM wheelchairs WHERE id=?")){p.setString(1,id);try(ResultSet r=p.executeQuery()){return r.next()?map(r):null;}}catch(SQLException e){e.printStackTrace();return null;}}
    public List<Wheelchair> findAll(){List<Wheelchair> out=new ArrayList<>();try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT id,station_id,quantity FROM wheelchairs ORDER BY station_id");ResultSet r=p.executeQuery()){while(r.next())out.add(map(r));}catch(SQLException e){e.printStackTrace();}return out;}
    public boolean delete(String id){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM wheelchairs WHERE id=?")){p.setString(1,id);return p.executeUpdate()>0;}catch(SQLException e){e.printStackTrace();return false;}}
    public int getAvailableQuantity(String stationId){Wheelchair w=findByStation(stationId);return w==null?0:w.getQuantity();}
    private Wheelchair findByStation(String stationId){try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("SELECT id,station_id,quantity FROM wheelchairs WHERE station_id=?")){p.setString(1,stationId);try(ResultSet r=p.executeQuery()){return r.next()?map(r):null;}}catch(SQLException e){e.printStackTrace();return null;}}
    private Wheelchair map(ResultSet r)throws SQLException{return new Wheelchair(r.getString("id"),r.getString("station_id"),r.getInt("quantity"));}
}
