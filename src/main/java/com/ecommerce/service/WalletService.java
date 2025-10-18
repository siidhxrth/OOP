package com.ecommerce.service;

import com.ecommerce.dao.UserDAO;
import com.ecommerce.models.User;

/**
 * Service class for Wallet operations
 */
public class WalletService {
    private UserDAO userDAO;
    private TransactionService transactionService;
    
    public WalletService() {
        this.userDAO = new UserDAO();
        this.transactionService = new TransactionService();
    }
    
    /**
     * Get wallet balance for a user
     * @param userId user ID
     * @return wallet balance
     */
    public double getBalance(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        User user = userDAO.getUserById(userId);
        return user != null ? user.getWalletBalance() : 0.0;
    }
    
    /**
     * Add coins to wallet
     * @param userId user ID
     * @param amount amount to add
     * @param description description of the addition
     * @return true if successful, false otherwise
     */
    public boolean addCoins(int userId, double amount, String description) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        double newBalance = user.getWalletBalance() + amount;
        
        // Update wallet balance
        if (userDAO.updateWalletBalance(userId, newBalance)) {
            // Record transaction
            transactionService.recordEarned(userId, amount, description.trim());
            return true;
        }
        
        return false;
    }
    
    /**
     * Deduct coins from wallet
     * @param userId user ID
     * @param amount amount to deduct
     * @param description description of the deduction
     * @return true if successful, false otherwise
     */
    public boolean deductCoins(int userId, double amount, String description) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        if (user.getWalletBalance() < amount) {
            throw new IllegalArgumentException("Insufficient wallet balance. Available: " + 
                user.getWalletBalance() + ", Required: " + amount);
        }
        
        double newBalance = user.getWalletBalance() - amount;
        
        // Update wallet balance
        if (userDAO.updateWalletBalance(userId, newBalance)) {
            // Record transaction
            transactionService.recordSpent(userId, amount, description.trim());
            return true;
        }
        
        return false;
    }
    
    /**
     * Check if user has sufficient balance
     * @param userId user ID
     * @param amount required amount
     * @return true if sufficient, false otherwise
     */
    public boolean hasSufficientBalance(int userId, double amount) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        
        return getBalance(userId) >= amount;
    }
    
    /**
     * Get wallet summary for a user
     * @param userId user ID
     * @return formatted wallet summary
     */
    public String getWalletSummary(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        double balance = getBalance(userId);
        double totalEarned = transactionService.getTotalEarned(userId);
        double totalSpent = transactionService.getTotalSpent(userId);
        
        StringBuilder summary = new StringBuilder();
        summary.append("Wallet Summary:\n");
        summary.append("Current Balance: $").append(String.format("%.2f", balance)).append("\n");
        summary.append("Total Earned: $").append(String.format("%.2f", totalEarned)).append("\n");
        summary.append("Total Spent: $").append(String.format("%.2f", totalSpent)).append("\n");
        summary.append("Net Earnings: $").append(String.format("%.2f", totalEarned - totalSpent));
        
        return summary.toString();
    }
    
    /**
     * Transfer coins between users (for future use)
     * @param fromUserId sender user ID
     * @param toUserId receiver user ID
     * @param amount amount to transfer
     * @param description transfer description
     * @return true if successful, false otherwise
     */
    public boolean transferCoins(int fromUserId, int toUserId, double amount, String description) {
        if (fromUserId <= 0 || toUserId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (fromUserId == toUserId) {
            throw new IllegalArgumentException("Cannot transfer to yourself");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        
        // Check if sender has sufficient balance
        if (!hasSufficientBalance(fromUserId, amount)) {
            throw new IllegalArgumentException("Insufficient balance for transfer");
        }
        
        // Deduct from sender
        if (!deductCoins(fromUserId, amount, "Transfer to user " + toUserId + ": " + description)) {
            return false;
        }
        
        // Add to receiver
        return addCoins(toUserId, amount, "Transfer from user " + fromUserId + ": " + description);
    }
}
