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
public class PurchaseHistoryDTO implements Serializable{
    private String productName;
    private String image;
    private int quantity;
    private float price;

    public PurchaseHistoryDTO(String productName, String image, int quantity, float price) {
        this.productName = productName;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
    }
    

    

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
    
}
