/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huynhntp.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ACER
 */
public class CartDTO implements Serializable {
    private Map<String,ProductDTO> cart;

    public CartDTO() {
    }

    public CartDTO(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }

    public Map<String, ProductDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, ProductDTO> cart) {
        this.cart = cart;
    }
    
    public void add(ProductDTO dto){
        if(cart==null){
            cart = new HashMap<>();
        }
        if(cart.containsKey(dto.getProductID())){
            int quantity = this.cart.get(dto.getProductID()).getQuantity();
            dto.setQuantity(quantity+1);
        }
        cart.put(dto.getProductID(), dto);
    }
    
    public void delete(String productID){
        if(cart==null) return;
        if(cart.containsKey(productID)) cart.remove(productID);
    }
    
    public void update(int quantity,String prodcutID){
        if(cart==null) return;
        if(cart.containsKey(prodcutID)){
            this.cart.get(prodcutID).setQuantity(quantity);
        }
        
    }
    
}
