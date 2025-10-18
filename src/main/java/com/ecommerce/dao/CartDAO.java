package com.ecommerce.dao;

import com.ecommerce.DBConnection;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Cart operations
 */
public class CartDAO {
    
    /**
     * Add product to cart
     * @param userId user ID
     * @param productId product ID
     * @param quantity quantity to add
     * @return true if successful, false otherwise
     */
    public boolean addProduct(int userId, int productId, int quantity) {
        // Check if item already exists in cart
        String checkSql = "SELECT id, quantity FROM cart WHERE user_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateSql = "UPDATE cart SET quantity = quantity + ? WHERE user_id = ? AND product_id = ?";
        
        try (Connection conn = DBConnection.getConnection()) {
            // Check if item exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setInt(2, productId);
                
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        // Item exists, update quantity
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, quantity);
                            updateStmt.setInt(2, userId);
                            updateStmt.setInt(3, productId);
                            return updateStmt.executeUpdate() > 0;
                        }
                    } else {
                        // Item doesn't exist, insert new
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                            insertStmt.setInt(1, userId);
                            insertStmt.setInt(2, productId);
                            insertStmt.setInt(3, quantity);
                            return insertStmt.executeUpdate() > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding product to cart: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Remove product from cart
     * @param userId user ID
     * @param productId product ID
     * @return true if successful, false otherwise
     */
    public boolean removeProduct(int userId, int productId) {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error removing product from cart: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Update product quantity in cart
     * @param userId user ID
     * @param productId product ID
     * @param quantity new quantity
     * @return true if successful, false otherwise
     */
    public boolean updateQuantity(int userId, int productId, int quantity) {
        if (quantity <= 0) {
            return removeProduct(userId, productId);
        }
        
        String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating cart quantity: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get all cart items for a user
     * @param userId user ID
     * @return List of cart items with product details
     */
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT c.*, p.name, p.price, p.stock, p.description, p.category, p.image_url " +
                    "FROM cart c " +
                    "JOIN products p ON c.product_id = p.id " +
                    "WHERE c.user_id = ? " +
                    "ORDER BY c.created_at DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartItem cartItem = new CartItem(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                    );
                    
                    // Set product details
                    Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getString("image_url")
                    );
                    cartItem.setProduct(product);
                    
                    cartItems.add(cartItem);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting cart items: " + e.getMessage());
        }
        return cartItems;
    }
    
    /**
     * Clear all items from cart
     * @param userId user ID
     * @return true if successful, false otherwise
     */
    public boolean clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            return stmt.executeUpdate() >= 0; // >= 0 because cart might be empty
        } catch (SQLException e) {
            System.err.println("Error clearing cart: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get cart item count for a user
     * @param userId user ID
     * @return number of items in cart
     */
    public int getCartItemCount(int userId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting cart item count: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get total quantity of items in cart
     * @param userId user ID
     * @return total quantity
     */
    public int getTotalQuantity(int userId) {
        String sql = "SELECT SUM(quantity) FROM cart WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting total quantity: " + e.getMessage());
        }
        return 0;
    }
}
