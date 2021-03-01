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
public class ProductDTO implements Serializable {

    private String productID;
    private String name;
    private String image;
    private String description;
    private float price;
    private String createDate;
    private String categoryID;
    private String categoryName;
    private int quantity;
    private boolean status;

    public ProductDTO(String productID, String name, String image) {
        this.productID = productID;
        this.name = name;
        this.image = image;
    }

    public ProductDTO(String productID, String name, String image, String description, float price, String createDate, String categoryID, int quantity) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.categoryID = categoryID;
        this.quantity = quantity;
    }

    public ProductDTO(String productID, String name, String image, String description, float price, String createDate, String categoryID, int quantity, boolean status) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.categoryID = categoryID;
        this.quantity = quantity;
        this.status = status;
    }

    public ProductDTO(String productID, String name, String image, String description, float price, String categoryID, int quantity, boolean status) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
        this.quantity = quantity;
        this.status = status;
    }

    public ProductDTO(String productID, String name, String image, String description, float price) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;       
    }

    public ProductDTO(String productID, String name, String image, float price, int quantity) {
        this.productID = productID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }
    
    
    

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
