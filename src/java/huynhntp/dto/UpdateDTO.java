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
public class UpdateDTO implements Serializable{
    private String updateID;
    private String userID;
    private String updateDate;
    private String productID;

    public UpdateDTO(String updateID, String userID, String updateDate, String productID) {
        this.updateID = updateID;
        this.userID = userID;
        this.updateDate = updateDate;
        this.productID = productID;
    }

    public String getUpdateID() {
        return updateID;
    }

    public void setUpdateID(String updateID) {
        this.updateID = updateID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    
}
