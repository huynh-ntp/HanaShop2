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
public class ErrorProductDTO implements Serializable{
    private String productIDError;
    private String nameError;
    private String descriptionError;

    public ErrorProductDTO(String productIDError, String nameError, String descriptionError) {
        this.productIDError = productIDError;
        this.nameError = nameError;
        this.descriptionError = descriptionError;
    }

    public String getProductIDError() {
        return productIDError;
    }

    public void setProductIDError(String productIDError) {
        this.productIDError = productIDError;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }
    
  
}
