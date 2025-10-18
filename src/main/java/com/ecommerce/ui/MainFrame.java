package com.ecommerce.ui;

import com.ecommerce.models.User;
import com.ecommerce.service.UserService;
import com.ecommerce.service.WalletService;
import com.ecommerce.service.CartService;
import com.ecommerce.ui.components.NavigationBar;
import com.ecommerce.ui.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main application frame for the E-Commerce Application with modern CoinCart UI
 */
public class MainFrame extends JFrame {
    private User currentUser;
    private UserService userService;
    private WalletService walletService;
    private CartService cartService;
    
    private NavigationBar navigationBar;
    private JPanel contentPanel;
    private JLabel welcomeLabel;
    private JLabel walletLabel;
    private JButton productsButton;
    private JButton cartButton;
    private JButton walletButton;
    private JButton ordersButton;
    private JButton quizGameButton;
    private JButton profileButton;
    private JButton logoutButton;
    
    public MainFrame(User user) {
        this.currentUser = user;
        this.userService = new UserService();
        this.walletService = new WalletService();
        this.cartService = new CartService();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        updateWalletDisplay();
        updateCartCount();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CoinCart - " + user.getName());
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
        
        // Welcome label
        welcomeLabel = new JLabel("Welcome back, " + currentUser.getName() + "!", JLabel.CENTER);
        UIConstants.applyHeadingStyle(welcomeLabel);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING_XLARGE, 0, UIConstants.PADDING_LARGE, 0));
        
        // Wallet label
        walletLabel = new JLabel("Your wallet balance", JLabel.CENTER);
        UIConstants.applySecondaryStyle(walletLabel);
        walletLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, UIConstants.PADDING_LARGE, 0));
        
        // Menu buttons with modern styling
        productsButton = createMenuButton(UIConstants.ICON_PRODUCTS + " Browse Products", "Discover amazing products");
        cartButton = createMenuButton(UIConstants.ICON_CART + " Shopping Cart", "Review your cart items");
        walletButton = createMenuButton(UIConstants.ICON_COINS + " Wallet", "Manage your coins");
        ordersButton = createMenuButton("📦 Order History", "View your past orders");
        quizGameButton = createMenuButton(UIConstants.ICON_GAME + " Play Games", "Earn coins by playing");
        profileButton = createMenuButton(UIConstants.ICON_USER + " Profile", "Update your information");
        logoutButton = createMenuButton("🚪 Logout", "Sign out of your account");
    }
    
    private JButton createMenuButton(String text, String tooltip) {
        JButton button = new JButton(text);
        UIConstants.applyPrimaryButtonStyle(button);
        button.setPreferredSize(new Dimension(250, UIConstants.BUTTON_HEIGHT));
        button.setToolTipText(tooltip);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(UIConstants.PRIMARY_BLUE_DARK);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(UIConstants.PRIMARY_BLUE);
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Add navigation bar at top
        add(navigationBar, BorderLayout.NORTH);
        
        // Header panel with welcome message
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIConstants.BACKGROUND_GRAY);
        headerPanel.add(welcomeLabel, BorderLayout.NORTH);
        headerPanel.add(walletLabel, BorderLayout.SOUTH);
        
        // Menu panel with modern grid layout
        JPanel menuPanel = new JPanel(new GridLayout(2, 3, UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE));
        menuPanel.setBackground(UIConstants.BACKGROUND_GRAY);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING_XLARGE, UIConstants.PADDING_XLARGE, UIConstants.PADDING_XLARGE, UIConstants.PADDING_XLARGE));
        
        menuPanel.add(productsButton);
        menuPanel.add(cartButton);
        menuPanel.add(walletButton);
        menuPanel.add(ordersButton);
        menuPanel.add(quizGameButton);
        menuPanel.add(profileButton);
        
        // Center content
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(menuPanel, BorderLayout.CENTER);
        
        // Logout button at bottom
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(UIConstants.BACKGROUND_GRAY);
        bottomPanel.add(logoutButton);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, UIConstants.PADDING_LARGE, 0));
        
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Navigation bar listeners
        navigationBar.setProductsListener(e -> openProductsWindow());
        navigationBar.setGamesListener(e -> openQuizGameWindow());
        navigationBar.setCartListener(e -> openCartWindow());
        navigationBar.setUserListener(e -> openProfileWindow());
        
        // Main menu button listeners
        productsButton.addActionListener(e -> openProductsWindow());
        cartButton.addActionListener(e -> openCartWindow());
        walletButton.addActionListener(e -> openWalletWindow());
        ordersButton.addActionListener(e -> openOrdersWindow());
        quizGameButton.addActionListener(e -> openQuizGameWindow());
        profileButton.addActionListener(e -> openProfileWindow());
        logoutButton.addActionListener(e -> logout());
    }
    
    private void openProductsWindow() {
        SwingUtilities.invokeLater(() -> {
            new ProductsFrame(currentUser).setVisible(true);
        });
    }
    
    private void openCartWindow() {
        SwingUtilities.invokeLater(() -> {
            new CartFrame(currentUser).setVisible(true);
        });
    }
    
    private void openWalletWindow() {
        SwingUtilities.invokeLater(() -> {
            new WalletFrame(currentUser).setVisible(true);
        });
    }
    
    private void openOrdersWindow() {
        SwingUtilities.invokeLater(() -> {
            new OrdersFrame(currentUser).setVisible(true);
        });
    }
    
    private void openQuizGameWindow() {
        SwingUtilities.invokeLater(() -> {
            new GamesFrame(currentUser).setVisible(true);
        });
    }
    
    private void openProfileWindow() {
        SwingUtilities.invokeLater(() -> {
            new ProfileFrame(currentUser).setVisible(true);
        });
    }
    
    private void logout() {
        int choice = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Logout Confirmation", 
            JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
        }
    }
    
    private void updateWalletDisplay() {
        try {
            double balance = walletService.getBalance(currentUser.getId());
            walletLabel.setText("$" + String.format("%.2f", balance));
            navigationBar.updateCoinCount(balance);
        } catch (Exception e) {
            walletLabel.setText("Error loading");
        }
    }
    
    private void updateCartCount() {
        try {
            int count = cartService.getTotalQuantity(currentUser.getId());
            navigationBar.updateCartCount(count);
        } catch (Exception e) {
            navigationBar.updateCartCount(0);
        }
    }
    
    public void refreshWalletDisplay() {
        updateWalletDisplay();
    }
    
    public void refreshCartCount() {
        updateCartCount();
    }
}
