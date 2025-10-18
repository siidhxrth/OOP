package com.ecommerce.service;

import com.ecommerce.dao.TransactionDAO;
import com.ecommerce.models.Transaction;

import java.util.List;

/**
 * Service class for Transaction operations
 */
public class TransactionService {
    private TransactionDAO transactionDAO;
    
    public TransactionService() {
        this.transactionDAO = new TransactionDAO();
    }
    
    /**
     * Record a new transaction
     * @param userId user ID
     * @param type transaction type (earned, spent, refund)
     * @param amount transaction amount
     * @param description transaction description
     * @return true if successful, false otherwise
     */
    public boolean recordTransaction(int userId, String type, double amount, String description) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (type == null || (!type.equals("earned") && !type.equals("spent") && !type.equals("refund"))) {
            throw new IllegalArgumentException("Invalid transaction type");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        
        Transaction transaction = new Transaction(userId, type, amount, description.trim());
        return transactionDAO.recordTransaction(transaction);
    }
    
    /**
     * Record earned transaction (coins earned from quiz game)
     * @param userId user ID
     * @param amount amount earned
     * @param description description of earning
     * @return true if successful, false otherwise
     */
    public boolean recordEarned(int userId, double amount, String description) {
        return recordTransaction(userId, "earned", amount, description);
    }
    
    /**
     * Record spent transaction (coins used for discount)
     * @param userId user ID
     * @param amount amount spent
     * @param description description of spending
     * @return true if successful, false otherwise
     */
    public boolean recordSpent(int userId, double amount, String description) {
        return recordTransaction(userId, "spent", amount, description);
    }
    
    /**
     * Record refund transaction (coins refunded)
     * @param userId user ID
     * @param amount amount refunded
     * @param description description of refund
     * @return true if successful, false otherwise
     */
    public boolean recordRefund(int userId, double amount, String description) {
        return recordTransaction(userId, "refund", amount, description);
    }
    
    /**
     * Get transaction history for a user
     * @param userId user ID
     * @return List of transactions
     */
    public List<Transaction> getTransactionHistory(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return transactionDAO.getTransactionHistory(userId);
    }
    
    /**
     * Get recent transaction history for a user
     * @param userId user ID
     * @param limit maximum number of transactions to return
     * @return List of recent transactions
     */
    public List<Transaction> getRecentTransactionHistory(int userId, int limit) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be positive");
        }
        
        return transactionDAO.getTransactionHistory(userId, limit);
    }
    
    /**
     * Get transactions by type for a user
     * @param userId user ID
     * @param type transaction type (earned, spent, refund)
     * @return List of transactions
     */
    public List<Transaction> getTransactionsByType(int userId, String type) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (type == null || (!type.equals("earned") && !type.equals("spent") && !type.equals("refund"))) {
            throw new IllegalArgumentException("Invalid transaction type");
        }
        
        return transactionDAO.getTransactionsByType(userId, type);
    }
    
    /**
     * Get total earned amount for a user
     * @param userId user ID
     * @return total earned amount
     */
    public double getTotalEarned(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return transactionDAO.getTotalEarned(userId);
    }
    
    /**
     * Get total spent amount for a user
     * @param userId user ID
     * @return total spent amount
     */
    public double getTotalSpent(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return transactionDAO.getTotalSpent(userId);
    }
    
    /**
     * Get transaction count for a user
     * @param userId user ID
     * @return number of transactions
     */
    public int getTransactionCount(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return transactionDAO.getTransactionCount(userId);
    }
    
    /**
     * Get transaction summary for a user
     * @param userId user ID
     * @return formatted transaction summary
     */
    public String getTransactionSummary(int userId) {
        double totalEarned = getTotalEarned(userId);
        double totalSpent = getTotalSpent(userId);
        int transactionCount = getTransactionCount(userId);
        
        StringBuilder summary = new StringBuilder();
        summary.append("Transaction Summary:\n");
        summary.append("Total Earned: $").append(String.format("%.2f", totalEarned)).append("\n");
        summary.append("Total Spent: $").append(String.format("%.2f", totalSpent)).append("\n");
        summary.append("Net Balance: $").append(String.format("%.2f", totalEarned - totalSpent)).append("\n");
        summary.append("Total Transactions: ").append(transactionCount);
        
        return summary.toString();
    }
}
