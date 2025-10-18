package com.ecommerce;

import com.ecommerce.ui.LoginFrame;

import javax.swing.*;

/**
 * Main application launcher for the E-Commerce Application with Quiz Game
 */
public class ECommerceApplication {
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error setting look and feel: " + e.getMessage());
        }
        
        // Test database connection
        if (!DBConnection.testConnection()) {
            JOptionPane.showMessageDialog(null, 
                "Failed to connect to database!\n" +
                "Please ensure:\n" +
                "1. MySQL server is running\n" +
                "2. Database 'ecommerce_quiz' exists\n" +
                "3. Username and password in DBConnection.java are correct\n" +
                "4. MySQL JDBC driver is in classpath", 
                "Database Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        // Start the application
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginFrame().setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error starting application: " + e.getMessage(), 
                    "Application Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
