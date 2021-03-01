/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.dao;

import huynhntp.dto.UserDTO;
import huynhntp.util.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author ACER
 */
public class UserDAO {

    public UserDTO checkLogin(String userID, String password) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        UserDTO dto = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT userID, fullName,status,roleID FROM tblUsers\n"
                        + "WHERE userID = ? and password = ? and status = 1";
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    dto = new UserDTO(rs.getString("userID"), "", rs.getString("fullName"), rs.getBoolean("status"), rs.getString("roleID"));
                }
            }
        } catch (SQLException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return dto;
    }

    public boolean checkExistGg(String id) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        boolean result = false;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                String sql = "SELECT userID FROM tblUsers"
                        + " WHERE userID = ?";
                stm = conn.prepareStatement(sql);
                stm.setString(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (SQLException | NamingException e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }
    
    public void createGg(String id, String name, String roleID, boolean status) throws SQLException{
         Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "INSERT INTO tblUsers(userID,password,fullName,roleID,status)"
                + " VALUES(?,?,?,?,?)";
        try {
            conn = DBConnect.makeConnection();
            if(conn!=null){
                stm = conn.prepareStatement(sql);
                stm.setString(1, id);
                stm.setString(2, "");
                stm.setString(3, name);
                stm.setString(4, roleID);
                stm.setBoolean(5, status);
                stm.executeUpdate();
            }
        } catch (SQLException | NamingException e) {
        }finally{
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public UserDTO checkLoginGoogle(String userID) throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT userID,fullName,roleID,status FROM tblUsers where userID=?";
        UserDTO user = null;
        try {
            conn = DBConnect.makeConnection();
            if (conn != null) {
                stm = conn.prepareStatement(sql);
                stm.setString(1, userID);
                
                rs = stm.executeQuery();
                if (rs.next()) {
                    user = new UserDTO(rs.getString("userID"), "", rs.getString("fullName"), rs.getBoolean("status"), rs.getString("roleID"));
                }
            }

        } catch (SQLException | NamingException e) {
            
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }
}
