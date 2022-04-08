/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emart.pojo;

/**
 *
 * @author Admin
 */
public class OrdersPojo {
    public OrdersPojo()
    {
       
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String[] getProductId() {
        return ProductId;
    }

    public void setProductId(String[] ProductId) {
        this.ProductId = ProductId;
    }

    public int[] getQuantity() {
        return quantity;
    }

    public void setQuantity(int[] quantity) {
        this.quantity = quantity;
    }

    public String[] getUserId() {
        return userId;
    }

    public void setUserId(String[] userId) {
        this.userId = userId;
    }
    private String orderId;
    private String[] ProductId;
    private int[] quantity;
    private String[] userId;
}
