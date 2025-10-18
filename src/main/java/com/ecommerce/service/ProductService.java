package com.ecommerce.service;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.models.Product;

import java.util.List;

/**
 * Service class for Product operations
 */
public class ProductService {
    private ProductDAO productDAO;
    
    public ProductService() {
        this.productDAO = new ProductDAO();
    }
    
    /**
     * Get all products
     * @return List of all products
     */
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
    
    /**
     * Get product by ID
     * @param productId product ID
     * @return Product object if found, null otherwise
     */
    public Product getProductById(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        return productDAO.getProductById(productId);
    }
    
    /**
     * Get products by category
     * @param category product category
     * @return List of products in the category
     */
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        return productDAO.getProductsByCategory(category.trim());
    }
    
    /**
     * Search products
     * @param searchTerm search term
     * @return List of matching products
     */
    public List<Product> searchProducts(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllProducts();
        }
        return productDAO.searchProducts(searchTerm.trim());
    }
    
    /**
     * Update product stock
     * @param productId product ID
     * @param newStock new stock quantity
     * @return true if successful, false otherwise
     */
    public boolean updateStock(int productId, int newStock) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        return productDAO.updateStock(productId, newStock);
    }
    
    /**
     * Get all categories
     * @return List of unique categories
     */
    public List<String> getAllCategories() {
        return productDAO.getAllCategories();
    }
    
    /**
     * Check if product has sufficient stock
     * @param productId product ID
     * @param quantity required quantity
     * @return true if sufficient stock, false otherwise
     */
    public boolean hasSufficientStock(int productId, int quantity) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        return productDAO.hasSufficientStock(productId, quantity);
    }
    
    /**
     * Get product details for display
     * @param productId product ID
     * @return formatted product details string
     */
    public String getProductDetails(int productId) {
        Product product = getProductById(productId);
        if (product == null) {
            return "Product not found";
        }
        
        StringBuilder details = new StringBuilder();
        details.append("Name: ").append(product.getName()).append("\n");
        details.append("Price: $").append(String.format("%.2f", product.getPrice())).append("\n");
        details.append("Stock: ").append(product.getStock()).append("\n");
        details.append("Category: ").append(product.getCategory()).append("\n");
        details.append("Description: ").append(product.getDescription());
        
        return details.toString();
    }
}
