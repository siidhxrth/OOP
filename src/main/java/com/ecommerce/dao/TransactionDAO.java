package com.ecommerce.dao;

import com.ecommerce.DBConnection;
import com.ecommerce.models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Transaction operations
 */
public class TransactionDAO {
    
    /**
     * Record a new transaction
     * @param transaction Transaction object to record
     * @return true if successful, false otherwise
     */
    public boolean recordTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, type, amount, description) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, transaction.getUserId());
            stmt.setString(2, transaction.getType());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getDescription());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error recording transaction: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get transaction history for a user
     * @param userId user ID
     * @return List of transactions
     */
    public List<Transaction> getTransactionHistory(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getTimestamp("transaction_date")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaction history: " + e.getMessage());
        }
        return transactions;
    }
    
    /**
     * Get transaction history for a user with limit
     * @param userId user ID
     * @param limit maximum number of transactions to return
     * @return List of transactions
     */
    public List<Transaction> getTransactionHistory(int userId, int limit) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC LIMIT ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getTimestamp("transaction_date")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaction history: " + e.getMessage());
        }
        return transactions;
    }
    
    /**
     * Get transactions by type for a user
     * @param userId user ID
     * @param type transaction type (earned, spent, refund)
     * @return List of transactions
     */
    public List<Transaction> getTransactionsByType(int userId, String type) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? AND type = ? ORDER BY transaction_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setString(2, type);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getTimestamp("transaction_date")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transactions by type: " + e.getMessage());
        }
        return transactions;
    }
    
    /**
     * Get total earned amount for a user
     * @param userId user ID
     * @return total earned amount
     */
    public double getTotalEarned(int userId) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = 'earned'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting total earned: " + e.getMessage());
        }
        return 0.0;
    }
    
    /**
     * Get total spent amount for a user
     * @param userId user ID
     * @return total spent amount
     */
    public double getTotalSpent(int userId) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE user_id = ? AND type = 'spent'";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting total spent: " + e.getMessage());
        }
        return 0.0;
    }
    
    /**
     * Get transaction count for a user
     * @param userId user ID
     * @return number of transactions
     */
    public int getTransactionCount(int userId) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting transaction count: " + e.getMessage());
        }
        return 0;
    }
}
