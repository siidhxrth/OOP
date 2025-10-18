package com.ecommerce.dao;

import com.ecommerce.DBConnection;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Order operations
 */
public class OrderDAO {
    
    /**
     * Create a new order
     * @param order Order object to create
     * @return Order object with generated ID if successful, null otherwise
     */
    public Order createOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, total_amount, coins_used, final_amount, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, order.getUserId());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.setDouble(3, order.getCoinsUsed());
            stmt.setDouble(4, order.getFinalAmount());
            stmt.setString(5, order.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setId(generatedKeys.getInt(1));
                        return order;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating order: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Add order item
     * @param orderItem OrderItem object to add
     * @return true if successful, false otherwise
     */
    public boolean addOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getPrice());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding order item: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Update order status
     * @param orderId order ID
     * @param status new status
     * @return true if successful, false otherwise
     */
    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get order by ID
     * @param orderId order ID
     * @return Order object if found, null otherwise
     */
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("total_amount"),
                        rs.getDouble("coins_used"),
                        rs.getDouble("final_amount"),
                        rs.getString("status"),
                        rs.getTimestamp("order_date")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting order by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all orders for a user
     * @param userId user ID
     * @return List of orders
     */
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("total_amount"),
                        rs.getDouble("coins_used"),
                        rs.getDouble("final_amount"),
                        rs.getString("status"),
                        rs.getTimestamp("order_date")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting orders by user ID: " + e.getMessage());
        }
        return orders;
    }
    
    /**
     * Get order items for an order
     * @param orderId order ID
     * @return List of order items with product details
     */
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT oi.*, p.name, p.description, p.category " +
                    "FROM order_items oi " +
                    "JOIN products p ON oi.product_id = p.id " +
                    "WHERE oi.order_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, orderId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem orderItem = new OrderItem(
                        rs.getInt("id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                    );
                    
                    // Set product details for display
                    com.ecommerce.models.Product product = new com.ecommerce.models.Product();
                    product.setId(rs.getInt("product_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setCategory(rs.getString("category"));
                    orderItem.setProduct(product);
                    
                    orderItems.add(orderItem);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting order items: " + e.getMessage());
        }
        return orderItems;
    }
    
    /**
     * Get all orders (for admin purposes)
     * @return List of all orders
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                orders.add(new Order(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getDouble("total_amount"),
                    rs.getDouble("coins_used"),
                    rs.getDouble("final_amount"),
                    rs.getString("status"),
                    rs.getTimestamp("order_date")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all orders: " + e.getMessage());
        }
        return orders;
    }
    
    /**
     * Get order count for a user
     * @param userId user ID
     * @return number of orders
     */
    public int getOrderCount(int userId) {
        String sql = "SELECT COUNT(*) FROM orders WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting order count: " + e.getMessage());
        }
        return 0;
    }
}
