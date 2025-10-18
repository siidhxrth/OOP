package com.ecommerce.ui.components;

import com.ecommerce.models.User;
import com.ecommerce.ui.UIConstants;
import com.ecommerce.ui.components.LineIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Top Navigation Bar Component
 */
public class NavigationBar extends JPanel {
    private JLabel logoLabel;
    private JButton productsButton;
    private JButton gamesButton;
    private JLabel coinCountLabel;
    private JButton cartButton;
    private JLabel cartCountLabel;
    private JButton userButton;
    private User currentUser;
    
    // Panel components
    private JPanel logoPanel;
    private JPanel productsPanel;
    private JPanel gamesPanel;
    private JPanel coinPanel;
    private JPanel cartPanel;
    private JPanel userPanel;
    
    private ActionListener productsListener;
    private ActionListener gamesListener;
    private ActionListener cartListener;
    private ActionListener userListener;
    
    public NavigationBar(User user) {
        this.currentUser = user;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, UIConstants.NAVBAR_HEIGHT));
        setBackground(UIConstants.BACKGROUND_WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, UIConstants.BORDER_GRAY),
            BorderFactory.createEmptyBorder(0, UIConstants.PADDING_LARGE, 0, UIConstants.PADDING_LARGE)
        ));
        
        // Logo with icon
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        logoPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        logoPanel.add(LineIcon.createLogoIcon());
        logoLabel = new JLabel("CoinCart");
        UIConstants.applyHeadingStyle(logoLabel);
        logoLabel.setForeground(UIConstants.PRIMARY_BLUE);
        logoPanel.add(logoLabel);
        
        // Navigation buttons with icons
        JPanel productsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        productsPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        productsPanel.add(LineIcon.createProductsIcon());
        productsButton = new JButton("Products");
        UIConstants.applySecondaryButtonStyle(productsButton);
        productsButton.setBorderPainted(false);
        productsButton.setContentAreaFilled(false);
        productsButton.setFocusPainted(false);
        productsPanel.add(productsButton);
        
        JPanel gamesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        gamesPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        gamesPanel.add(LineIcon.createGameIcon());
        gamesButton = new JButton("Games");
        UIConstants.applySecondaryButtonStyle(gamesButton);
        gamesButton.setBorderPainted(false);
        gamesButton.setContentAreaFilled(false);
        gamesButton.setFocusPainted(false);
        gamesPanel.add(gamesButton);
        
        // Coin count with icon
        JPanel coinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        coinPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        coinPanel.add(LineIcon.createCoinsIcon());
        coinCountLabel = new JLabel("0 Coins");
        UIConstants.applyBodyStyle(coinCountLabel);
        coinCountLabel.setForeground(UIConstants.WARNING_ORANGE);
        coinPanel.add(coinCountLabel);
        
        // Cart button with icon and count
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        cartPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        cartPanel.add(LineIcon.createCartIcon());
        cartButton = new JButton("Cart");
        UIConstants.applySecondaryButtonStyle(cartButton);
        cartButton.setBorderPainted(false);
        cartButton.setContentAreaFilled(false);
        cartButton.setFocusPainted(false);
        cartPanel.add(cartButton);
        
        cartCountLabel = new JLabel("0");
        cartCountLabel.setFont(UIConstants.FONT_SMALL);
        cartCountLabel.setForeground(UIConstants.ERROR_RED);
        cartCountLabel.setBackground(UIConstants.BACKGROUND_WHITE);
        cartCountLabel.setOpaque(true);
        cartCountLabel.setBorder(BorderFactory.createLineBorder(UIConstants.ERROR_RED, 1));
        cartCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cartCountLabel.setPreferredSize(new Dimension(20, 20));
        
        // User button with icon
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        userPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        userPanel.add(LineIcon.createUserIcon());
        userButton = new JButton(currentUser.getName());
        UIConstants.applySecondaryButtonStyle(userButton);
        userButton.setBorderPainted(false);
        userButton.setContentAreaFilled(false);
        userButton.setFocusPainted(false);
        userPanel.add(userButton);
    }
    
    private void setupLayout() {
        // Left side - Logo and navigation
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        leftPanel.add(logoPanel);
        leftPanel.add(Box.createHorizontalStrut(UIConstants.PADDING_LARGE));
        leftPanel.add(productsPanel);
        leftPanel.add(Box.createHorizontalStrut(UIConstants.PADDING_SMALL));
        leftPanel.add(gamesPanel);
        
        // Right side - Coins, cart, user
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        
        // Cart with count badge
        JPanel cartContainer = new JPanel(new BorderLayout());
        cartContainer.setBackground(UIConstants.BACKGROUND_WHITE);
        cartContainer.add(cartPanel, BorderLayout.CENTER);
        cartContainer.add(cartCountLabel, BorderLayout.EAST);
        
        rightPanel.add(coinPanel);
        rightPanel.add(Box.createHorizontalStrut(UIConstants.PADDING_SMALL));
        rightPanel.add(cartContainer);
        rightPanel.add(Box.createHorizontalStrut(UIConstants.PADDING_SMALL));
        rightPanel.add(userPanel);
        
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }
    
    private void setupEventHandlers() {
        productsButton.addActionListener(e -> {
            if (productsListener != null) {
                productsListener.actionPerformed(e);
            }
        });
        
        gamesButton.addActionListener(e -> {
            if (gamesListener != null) {
                gamesListener.actionPerformed(e);
            }
        });
        
        cartButton.addActionListener(e -> {
            if (cartListener != null) {
                cartListener.actionPerformed(e);
            }
        });
        
        userButton.addActionListener(e -> {
            if (userListener != null) {
                userListener.actionPerformed(e);
            }
        });
    }
    
    // Getters and Setters for listeners
    public void setProductsListener(ActionListener listener) {
        this.productsListener = listener;
    }
    
    public void setGamesListener(ActionListener listener) {
        this.gamesListener = listener;
    }
    
    public void setCartListener(ActionListener listener) {
        this.cartListener = listener;
    }
    
    public void setUserListener(ActionListener listener) {
        this.userListener = listener;
    }
    
    // Update methods
    public void updateCoinCount(double coins) {
        coinCountLabel.setText(String.format("%.0f", coins) + " Coins");
    }
    
    public void updateCartCount(int count) {
        cartCountLabel.setText(String.valueOf(count));
        cartCountLabel.setVisible(count > 0);
    }
    
    public void updateUser(User user) {
        this.currentUser = user;
        userButton.setText(user.getName());
    }
}
