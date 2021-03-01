/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.dto;

import java.io.Serializable;

/**
 *
 * @author ACER
 */
public class UserDTO implements Serializable{
    private String userID;
    private String password;
    private String fullName;
    private boolean status;
    private String roleID;

    
    

    public UserDTO(String userID, String password, String fullName, boolean status, String roleID) {
        this.userID = userID;
        this.password = password;
        this.fullName = fullName;
        this.status = status;
        this.roleID = roleID;
    }
    
    
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
    
     
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    
}
