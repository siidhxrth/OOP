package com.ecommerce.service;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;
import com.ecommerce.models.CartItem;

import java.sql.Timestamp;
import java.util.List;

/**
 * Service class for Order operations
 */
public class OrderService {
    private OrderDAO orderDAO;
    private CartDAO cartDAO;
    private ProductDAO productDAO;
    private WalletService walletService;
    
    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.cartDAO = new CartDAO();
        this.productDAO = new ProductDAO();
        this.walletService = new WalletService();
    }
    
    /**
     * Place an order from cart items
     * @param userId user ID
     * @param coinsToUse coins to use for discount
     * @return Order object if successful, null otherwise
     */
    public Order placeOrder(int userId, double coinsToUse) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (coinsToUse < 0) {
            throw new IllegalArgumentException("Coins to use cannot be negative");
        }
        
        // Get cart items
        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        
        // Calculate total amount
        double totalAmount = 0.0;
        for (CartItem item : cartItems) {
            totalAmount += item.getTotalPrice();
        }
        
        // Check if user has sufficient coins
        if (coinsToUse > 0) {
            if (!walletService.hasSufficientBalance(userId, coinsToUse)) {
                throw new IllegalArgumentException("Insufficient wallet balance");
            }
        }
        
        // Calculate final amount
        double finalAmount = Math.max(0, totalAmount - coinsToUse);
        
        // Create order
        Order order = new Order(userId, totalAmount, coinsToUse, finalAmount, "pending");
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        
        // Save order to database
        Order savedOrder = orderDAO.createOrder(order);
        if (savedOrder == null) {
            throw new RuntimeException("Failed to create order");
        }
        
        // Add order items
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(
                savedOrder.getId(),
                cartItem.getProductId(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice()
            );
            
            if (!orderDAO.addOrderItem(orderItem)) {
                throw new RuntimeException("Failed to add order item");
            }
            
            // Update product stock
            int newStock = cartItem.getProduct().getStock() - cartItem.getQuantity();
            if (!productDAO.updateStock(cartItem.getProductId(), newStock)) {
                throw new RuntimeException("Failed to update product stock");
            }
        }
        
        // Deduct coins from wallet if used
        if (coinsToUse > 0) {
            if (!walletService.deductCoins(userId, coinsToUse, "Order #" + savedOrder.getId() + " discount")) {
                throw new RuntimeException("Failed to deduct coins from wallet");
            }
        }
        
        // Clear cart
        cartDAO.clearCart(userId);
        
        // Update order status to confirmed
        orderDAO.updateStatus(savedOrder.getId(), "confirmed");
        savedOrder.setStatus("confirmed");
        
        return savedOrder;
    }
    
    /**
     * Update order status
     * @param orderId order ID
     * @param status new status
     * @return true if successful, false otherwise
     */
    public boolean updateStatus(int orderId, String status) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be empty");
        }
        
        String[] validStatuses = {"pending", "confirmed", "shipped", "delivered", "cancelled"};
        boolean validStatus = false;
        for (String valid : validStatuses) {
            if (valid.equals(status)) {
                validStatus = true;
                break;
            }
        }
        
        if (!validStatus) {
            throw new IllegalArgumentException("Invalid status");
        }
        
        return orderDAO.updateStatus(orderId, status);
    }
    
    /**
     * Get order by ID
     * @param orderId order ID
     * @return Order object if found, null otherwise
     */
    public Order getOrderById(int orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        
        return orderDAO.getOrderById(orderId);
    }
    
    /**
     * Get all orders for a user
     * @param userId user ID
     * @return List of orders
     */
    public List<Order> getOrdersByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return orderDAO.getOrdersByUserId(userId);
    }
    
    /**
     * Get order items for an order
     * @param orderId order ID
     * @return List of order items
     */
    public List<OrderItem> getOrderItems(int orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        
        return orderDAO.getOrderItems(orderId);
    }
    
    /**
     * Get all orders (for admin purposes)
     * @return List of all orders
     */
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }
    
    /**
     * Get order count for a user
     * @param userId user ID
     * @return number of orders
     */
    public int getOrderCount(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return orderDAO.getOrderCount(userId);
    }
    
    /**
     * Cancel an order
     * @param orderId order ID
     * @param userId user ID (for security)
     * @return true if successful, false otherwise
     */
    public boolean cancelOrder(int orderId, int userId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        
        if (order.getUserId() != userId) {
            throw new IllegalArgumentException("You can only cancel your own orders");
        }
        
        if (!"pending".equals(order.getStatus()) && !"confirmed".equals(order.getStatus())) {
            throw new IllegalArgumentException("Cannot cancel order with status: " + order.getStatus());
        }
        
        // Refund coins if any were used
        if (order.getCoinsUsed() > 0) {
            walletService.addCoins(userId, order.getCoinsUsed(), "Order #" + orderId + " cancellation refund");
        }
        
        // Restore product stock
        List<OrderItem> orderItems = orderDAO.getOrderItems(orderId);
        for (OrderItem item : orderItems) {
            com.ecommerce.models.Product product = productDAO.getProductById(item.getProductId());
            if (product != null) {
                int newStock = product.getStock() + item.getQuantity();
                productDAO.updateStock(item.getProductId(), newStock);
            }
        }
        
        return orderDAO.updateStatus(orderId, "cancelled");
    }
    
    /**
     * Get order summary for a user
     * @param userId user ID
     * @return formatted order summary
     */
    public String getOrderSummary(int userId) {
        List<Order> orders = getOrdersByUserId(userId);
        int totalOrders = orders.size();
        int pendingOrders = 0;
        int confirmedOrders = 0;
        int deliveredOrders = 0;
        double totalSpent = 0.0;
        
        for (Order order : orders) {
            switch (order.getStatus()) {
                case "pending":
                    pendingOrders++;
                    break;
                case "confirmed":
                    confirmedOrders++;
                    break;
                case "delivered":
                    deliveredOrders++;
                    break;
            }
            totalSpent += order.getFinalAmount();
        }
        
        StringBuilder summary = new StringBuilder();
        summary.append("Order Summary:\n");
        summary.append("Total Orders: ").append(totalOrders).append("\n");
        summary.append("Pending: ").append(pendingOrders).append("\n");
        summary.append("Confirmed: ").append(confirmedOrders).append("\n");
        summary.append("Delivered: ").append(deliveredOrders).append("\n");
        summary.append("Total Spent: $").append(String.format("%.2f", totalSpent));
        
        return summary.toString();
    }
}
