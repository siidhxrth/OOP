package com.ecommerce.service;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.CartItem;
import com.ecommerce.models.Product;

import java.util.List;

/**
 * Service class for Cart operations
 */
public class CartService {
    private CartDAO cartDAO;
    private ProductDAO productDAO;
    
    public CartService() {
        this.cartDAO = new CartDAO();
        this.productDAO = new ProductDAO();
    }
    
    /**
     * Add product to cart
     * @param userId user ID
     * @param productId product ID
     * @param quantity quantity to add
     * @return true if successful, false otherwise
     */
    public boolean addProduct(int userId, int productId, int quantity) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        // Check if product exists and has sufficient stock
        Product product = productDAO.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        
        // Check current cart quantity + new quantity doesn't exceed stock
        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        int currentQuantity = 0;
        for (CartItem item : cartItems) {
            if (item.getProductId() == productId) {
                currentQuantity = item.getQuantity();
                break;
            }
        }
        
        if (currentQuantity + quantity > product.getStock()) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + product.getStock() + 
                ", Requested: " + (currentQuantity + quantity));
        }
        
        return cartDAO.addProduct(userId, productId, quantity);
    }
    
    /**
     * Remove product from cart
     * @param userId user ID
     * @param productId product ID
     * @return true if successful, false otherwise
     */
    public boolean removeProduct(int userId, int productId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        
        return cartDAO.removeProduct(userId, productId);
    }
    
    /**
     * Update product quantity in cart
     * @param userId user ID
     * @param productId product ID
     * @param quantity new quantity
     * @return true if successful, false otherwise
     */
    public boolean updateQuantity(int userId, int productId, int quantity) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        
        if (quantity == 0) {
            return removeProduct(userId, productId);
        }
        
        // Check if product has sufficient stock
        Product product = productDAO.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        
        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + product.getStock() + 
                ", Requested: " + quantity);
        }
        
        return cartDAO.updateQuantity(userId, productId, quantity);
    }
    
    /**
     * Get all cart items for a user
     * @param userId user ID
     * @return List of cart items with product details
     */
    public List<CartItem> getCartItems(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return cartDAO.getCartItems(userId);
    }
    
    /**
     * Clear all items from cart
     * @param userId user ID
     * @return true if successful, false otherwise
     */
    public boolean clearCart(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return cartDAO.clearCart(userId);
    }
    
    /**
     * Calculate total amount for cart
     * @param userId user ID
     * @return total amount
     */
    public double calculateTotal(int userId) {
        List<CartItem> cartItems = getCartItems(userId);
        double total = 0.0;
        
        for (CartItem item : cartItems) {
            total += item.getTotalPrice();
        }
        
        return total;
    }
    
    /**
     * Get cart item count for a user
     * @param userId user ID
     * @return number of items in cart
     */
    public int getCartItemCount(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return cartDAO.getCartItemCount(userId);
    }
    
    /**
     * Get total quantity of items in cart
     * @param userId user ID
     * @return total quantity
     */
    public int getTotalQuantity(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return cartDAO.getTotalQuantity(userId);
    }
    
    /**
     * Check if cart is empty
     * @param userId user ID
     * @return true if empty, false otherwise
     */
    public boolean isCartEmpty(int userId) {
        return getCartItemCount(userId) == 0;
    }
}
