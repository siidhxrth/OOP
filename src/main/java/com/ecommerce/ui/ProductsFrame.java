package com.ecommerce.ui;

import com.ecommerce.models.User;
import com.ecommerce.models.Product;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.CartService;
import com.ecommerce.ui.components.ProductCard;
import com.ecommerce.ui.components.NavigationBar;
import com.ecommerce.ui.components.PlaceholderTextField;
import com.ecommerce.ui.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Modern Products browsing frame with grid layout and filters
 */
public class ProductsFrame extends JFrame {
    private User currentUser;
    private ProductService productService;
    private CartService cartService;
    
    private NavigationBar navigationBar;
    private JPanel contentPanel;
    private JTextField searchField;
    private JButton allButton;
    private JButton foodButton;
    private JButton groceryButton;
    private JButton electronicsButton;
    private JPanel productsGrid;
    private JScrollPane scrollPane;
    private List<Product> allProducts;
    private List<Product> filteredProducts;
    
    public ProductsFrame(User user) {
        this.currentUser = user;
        this.productService = new ProductService();
        this.cartService = new CartService();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadProducts();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Products - CoinCart");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(UIConstants.BACKGROUND_GRAY);
    }
    
    private void initializeComponents() {
        // Navigation bar
        navigationBar = new NavigationBar(currentUser);
        
        // Content panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UIConstants.BACKGROUND_GRAY);
        
        // Search field
        searchField = new PlaceholderTextField("Search products...");
        UIConstants.applyInputStyle(searchField);
        searchField.setPreferredSize(new Dimension(300, UIConstants.INPUT_HEIGHT));
        
        // Category filter buttons
        allButton = new JButton("All");
        UIConstants.applyPrimaryButtonStyle(allButton);
        
        foodButton = new JButton("Food");
        UIConstants.applySecondaryButtonStyle(foodButton);
        
        groceryButton = new JButton("Grocery");
        UIConstants.applySecondaryButtonStyle(groceryButton);
        
        electronicsButton = new JButton("Electronics");
        UIConstants.applySecondaryButtonStyle(electronicsButton);
        
        // Products grid with modern layout
        productsGrid = new JPanel();
        productsGrid.setLayout(new GridLayout(0, 4, UIConstants.PADDING_MEDIUM, UIConstants.PADDING_MEDIUM));
        productsGrid.setBackground(UIConstants.BACKGROUND_GRAY);
        
        // Scroll pane for products
        scrollPane = new JScrollPane(productsGrid);
        scrollPane.setBorder(null);
        scrollPane.setBackground(UIConstants.BACKGROUND_GRAY);
        scrollPane.getViewport().setBackground(UIConstants.BACKGROUND_GRAY);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Add navigation bar at top
        add(navigationBar, BorderLayout.NORTH);
        
        // Top panel - Search and filters
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIConstants.BACKGROUND_GRAY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE, UIConstants.PADDING_MEDIUM, UIConstants.PADDING_LARGE));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchPanel.setBackground(UIConstants.BACKGROUND_GRAY);
        searchPanel.add(searchField);
        
        // Category filters panel
        JPanel filtersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, UIConstants.PADDING_SMALL, 0));
        filtersPanel.setBackground(UIConstants.BACKGROUND_GRAY);
        filtersPanel.add(allButton);
        filtersPanel.add(foodButton);
        filtersPanel.add(groceryButton);
        filtersPanel.add(electronicsButton);
        
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(filtersPanel, BorderLayout.CENTER);
        
        // Center panel - Products grid
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Navigation bar listeners
        navigationBar.setProductsListener(e -> {}); // Already in products
        navigationBar.setGamesListener(e -> openQuizGameWindow());
        navigationBar.setCartListener(e -> openCartWindow());
        navigationBar.setUserListener(e -> openProfileWindow());
        
        // Search field listener
        searchField.addActionListener(e -> filterProducts());
        
        // Category filter buttons
        allButton.addActionListener(e -> filterByCategory("All"));
        foodButton.addActionListener(e -> filterByCategory("Food"));
        groceryButton.addActionListener(e -> filterByCategory("Grocery"));
        electronicsButton.addActionListener(e -> filterByCategory("Electronics"));
    }
    
    private void loadProducts() {
        try {
            allProducts = productService.getAllProducts();
            filteredProducts = allProducts;
            displayProducts(filteredProducts);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void filterProducts() {
        String searchTerm = searchField.getText().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            filteredProducts = allProducts;
        } else {
            filteredProducts = allProducts.stream()
                .filter(p -> p.getName().toLowerCase().contains(searchTerm) ||
                           p.getDescription().toLowerCase().contains(searchTerm))
                .collect(java.util.stream.Collectors.toList());
        }
        
        displayProducts(filteredProducts);
    }
    
    private void filterByCategory(String category) {
        // Update button styles
        resetCategoryButtons();
        switch (category) {
            case "All":
                UIConstants.applyPrimaryButtonStyle(allButton);
                filteredProducts = allProducts;
                break;
            case "Food":
                UIConstants.applyPrimaryButtonStyle(foodButton);
                filteredProducts = allProducts.stream()
                    .filter(p -> "Food".equals(p.getCategory()))
                    .collect(java.util.stream.Collectors.toList());
                break;
            case "Grocery":
                UIConstants.applyPrimaryButtonStyle(groceryButton);
                filteredProducts = allProducts.stream()
                    .filter(p -> "Grocery".equals(p.getCategory()))
                    .collect(java.util.stream.Collectors.toList());
                break;
            case "Electronics":
                UIConstants.applyPrimaryButtonStyle(electronicsButton);
                filteredProducts = allProducts.stream()
                    .filter(p -> "Electronics".equals(p.getCategory()))
                    .collect(java.util.stream.Collectors.toList());
                break;
        }
        
        displayProducts(filteredProducts);
    }
    
    private void resetCategoryButtons() {
        UIConstants.applySecondaryButtonStyle(allButton);
        UIConstants.applySecondaryButtonStyle(foodButton);
        UIConstants.applySecondaryButtonStyle(groceryButton);
        UIConstants.applySecondaryButtonStyle(electronicsButton);
    }
    
    private void displayProducts(List<Product> products) {
        productsGrid.removeAll();
        
        for (Product product : products) {
            ProductCard card = new ProductCard(product);
            card.setAddToCartListener(e -> addToCart(product));
            productsGrid.add(card);
        }
        
        productsGrid.revalidate();
        productsGrid.repaint();
    }
    
    private void addToCart(Product product) {
        try {
            boolean success = cartService.addProduct(currentUser.getId(), product.getId(), 1);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    product.getName() + " added to cart!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to add product to cart!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error adding to cart: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openCartWindow() {
        SwingUtilities.invokeLater(() -> {
            new CartFrame(currentUser).setVisible(true);
        });
    }
    
    private void openQuizGameWindow() {
        SwingUtilities.invokeLater(() -> {
            new QuizGameFrame(currentUser).setVisible(true);
        });
    }
    
    private void openProfileWindow() {
        SwingUtilities.invokeLater(() -> {
            new ProfileFrame(currentUser).setVisible(true);
        });
    }
}
