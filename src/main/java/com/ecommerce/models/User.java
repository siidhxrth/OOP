package com.ecommerce.models;

/**
 * User model class representing a user in the system
 */
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private double walletBalance;
    
    // Constructors
    public User() {}
    
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.walletBalance = 0.0;
    }
    
    public User(int id, String name, String email, String password, double walletBalance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.walletBalance = walletBalance;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public double getWalletBalance() {
        return walletBalance;
    }
    
    public void setWalletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", walletBalance=" + walletBalance +
                '}';
    }
}
