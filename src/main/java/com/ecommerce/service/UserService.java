package com.ecommerce.service;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;

/**
 * Service class for User operations
 */
public class UserService {
    private UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Register a new user
     * @param name user name
     * @param email user email
     * @param password user password
     * @return User object if successful, null otherwise
     */
    public User registerUser(String name, String email, String password) {
        // Validate input
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        
        // Check if email already exists
        if (userDAO.emailExists(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        // Create new user
        User user = new User(name.trim(), email.trim().toLowerCase(), password);
        user.setWalletBalance(10.0); // Welcome bonus
        
        if (userDAO.createUser(user)) {
            return user;
        }
        
        return null;
    }
    
    /**
     * Login user
     * @param email user email
     * @param password user password
     * @return User object if successful, null otherwise
     */
    public User loginUser(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        
        return userDAO.getUserByEmailAndPassword(email.trim().toLowerCase(), password);
    }
    
    /**
     * Update user profile
     * @param user User object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateProfile(User user) {
        if (user == null || user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user");
        }
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty() || !isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        
        return userDAO.updateUser(user);
    }
    
    /**
     * Get user wallet balance
     * @param userId user ID
     * @return wallet balance
     */
    public double getWalletBalance(int userId) {
        User user = userDAO.getUserById(userId);
        return user != null ? user.getWalletBalance() : 0.0;
    }
    
    /**
     * Update user wallet balance
     * @param userId user ID
     * @param newBalance new balance
     * @return true if successful, false otherwise
     */
    public boolean updateWalletBalance(int userId, double newBalance) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (newBalance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        
        return userDAO.updateWalletBalance(userId, newBalance);
    }
    
    /**
     * Get user by ID
     * @param userId user ID
     * @return User object if found, null otherwise
     */
    public User getUserById(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return userDAO.getUserById(userId);
    }
    
    /**
     * Validate email format
     * @param email email to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    }
}
