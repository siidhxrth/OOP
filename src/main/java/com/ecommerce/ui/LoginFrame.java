package com.ecommerce.ui;

import com.ecommerce.service.UserService;
import com.ecommerce.models.User;
import com.ecommerce.ui.UIConstants;
import com.ecommerce.ui.components.PlaceholderTextField;
import com.ecommerce.ui.components.LineIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Modern Login frame matching CoinCart design exactly
 */
public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel signupLink;
    private JLabel logoLabel;
    private JLabel titleLabel;
    private JLabel taglineLabel;
    private JLabel demoLabel;
    private JLabel dividerLabel;
    
    private boolean isLoginMode = true;
    private UserService userService;
    
    public LoginFrame() {
        this.userService = new UserService();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CoinCart - Login");
        setSize(500, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(248, 250, 252)); // Light blue-gray background
    }
    
    private void initializeComponents() {
        // Logo - Blue circle with professional icon
        logoLabel = new JLabel();
        logoLabel.setPreferredSize(new Dimension(80, 80));
        logoLabel.setOpaque(true);
        logoLabel.setBackground(UIConstants.PRIMARY_BLUE);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        logoLabel.setLayout(new BorderLayout());
        
        // Add the line icon to the center of the logo
        LineIcon logoIcon = LineIcon.createLogoIcon();
        logoIcon.setIconColor(Color.WHITE);
        logoIcon.setIconSize(40);
        logoLabel.add(logoIcon, BorderLayout.CENTER);
        
        // Title
        titleLabel = new JLabel("CoinCart", JLabel.CENTER);
        UIConstants.applyHeadingStyle(titleLabel);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        
        // Tagline
        taglineLabel = new JLabel("Shop, Play & Save with Coins!", JLabel.CENTER);
        UIConstants.applySecondaryStyle(taglineLabel);
        taglineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        // Email field
        emailField = new PlaceholderTextField("your.email@example.com");
        emailField.setPreferredSize(new Dimension(350, 45));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        // Password field
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(350, 45));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        // Login button
        loginButton = new JButton("Login");
        UIConstants.applyPrimaryButtonStyle(loginButton);
        loginButton.setPreferredSize(new Dimension(300, UIConstants.BUTTON_HEIGHT));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        // Signup link
        signupLink = new JLabel("<html>Don't have an account? <a href='#'>Sign Up</a></html>", JLabel.CENTER);
        signupLink.setFont(UIConstants.FONT_BODY);
        signupLink.setForeground(UIConstants.TEXT_SECONDARY);
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Divider line
        dividerLabel = new JLabel();
        dividerLabel.setPreferredSize(new Dimension(300, 1));
        dividerLabel.setBackground(UIConstants.BORDER_GRAY);
        dividerLabel.setOpaque(true);
        
        // Demo text
        demoLabel = new JLabel("Demo: Use any email and password (min 6 chars)", JLabel.CENTER);
        UIConstants.applyMutedStyle(demoLabel);
        demoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Main card panel with proper sizing
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1),
            BorderFactory.createEmptyBorder(50, 50, 50, 50)
        ));
        cardPanel.setPreferredSize(new Dimension(450, 600));
        cardPanel.setMaximumSize(new Dimension(450, 600));
        
        // Logo
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);
        logoPanel.add(logoLabel);
        cardPanel.add(logoPanel);
        cardPanel.add(Box.createVerticalStrut(25));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);
        cardPanel.add(titlePanel);
        cardPanel.add(Box.createVerticalStrut(15));
        
        // Tagline
        JPanel taglinePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        taglinePanel.setBackground(Color.WHITE);
        taglinePanel.add(taglineLabel);
        cardPanel.add(taglinePanel);
        cardPanel.add(Box.createVerticalStrut(40));
        
        // Email field with proper layout
        JPanel emailContainer = new JPanel(new BorderLayout());
        emailContainer.setBackground(Color.WHITE);
        emailContainer.setPreferredSize(new Dimension(350, 80));
        
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setForeground(UIConstants.TEXT_PRIMARY);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        
        emailField.setPreferredSize(new Dimension(350, 45));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        emailContainer.add(emailLabel, BorderLayout.NORTH);
        emailContainer.add(emailField, BorderLayout.CENTER);
        cardPanel.add(emailContainer);
        cardPanel.add(Box.createVerticalStrut(25));
        
        // Password field with proper layout
        JPanel passwordContainer = new JPanel(new BorderLayout());
        passwordContainer.setBackground(Color.WHITE);
        passwordContainer.setPreferredSize(new Dimension(350, 80));
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordLabel.setForeground(UIConstants.TEXT_PRIMARY);
        passwordLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        
        passwordField.setPreferredSize(new Dimension(350, 45));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        
        passwordContainer.add(passwordLabel, BorderLayout.NORTH);
        passwordContainer.add(passwordField, BorderLayout.CENTER);
        cardPanel.add(passwordContainer);
        cardPanel.add(Box.createVerticalStrut(35));
        
        // Login button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(350, 50));
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        buttonPanel.add(loginButton);
        cardPanel.add(buttonPanel);
        cardPanel.add(Box.createVerticalStrut(25));
        
        // Signup link
        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupPanel.setBackground(Color.WHITE);
        signupPanel.add(signupLink);
        cardPanel.add(signupPanel);
        cardPanel.add(Box.createVerticalStrut(25));
        
        // Divider
        JPanel dividerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dividerPanel.setBackground(Color.WHITE);
        dividerLabel.setPreferredSize(new Dimension(350, 1));
        dividerPanel.add(dividerLabel);
        cardPanel.add(dividerPanel);
        cardPanel.add(Box.createVerticalStrut(25));
        
        // Demo text
        JPanel demoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        demoPanel.setBackground(Color.WHITE);
        demoPanel.add(demoLabel);
        cardPanel.add(demoPanel);
        
        // Center the card
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(248, 250, 252));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(cardPanel, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        signupLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleSignup();
            }
        });
        
        // Enter key handling
        KeyStroke enterKey = KeyStroke.getKeyStroke("ENTER");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(enterKey, "enter");
        getRootPane().getActionMap().put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }
    
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            User user = userService.loginUser(email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getName(), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Open main application
                SwingUtilities.invokeLater(() -> {
                    new MainFrame(user).setVisible(true);
                    dispose();
                });
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Login failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleSignup() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Generate a name from email
        String name = email.split("@")[0];
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        
        try {
            User user = userService.registerUser(name, email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, 
                    "Registration successful! Welcome, " + user.getName() + 
                    "\nYou received 10 coins as a welcome bonus!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields
                emailField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Registration failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}