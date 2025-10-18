package com.ecommerce.models;

import java.sql.Timestamp;
import java.util.List;

/**
 * Order model class representing an order in the system
 */
public class Order {
    private int id;
    private int userId;
    private double totalAmount;
    private double coinsUsed;
    private double finalAmount;
    private String status;
    private Timestamp orderDate;
    private List<OrderItem> orderItems;
    
    // Constructors
    public Order() {}
    
    public Order(int userId, double totalAmount, double coinsUsed, double finalAmount, String status) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.coinsUsed = coinsUsed;
        this.finalAmount = finalAmount;
        this.status = status;
    }
    
    public Order(int id, int userId, double totalAmount, double coinsUsed, double finalAmount, 
                 String status, Timestamp orderDate) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.coinsUsed = coinsUsed;
        this.finalAmount = finalAmount;
        this.status = status;
        this.orderDate = orderDate;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public double getCoinsUsed() {
        return coinsUsed;
    }
    
    public void setCoinsUsed(double coinsUsed) {
        this.coinsUsed = coinsUsed;
    }
    
    public double getFinalAmount() {
        return finalAmount;
    }
    
    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", coinsUsed=" + coinsUsed +
                ", finalAmount=" + finalAmount +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
