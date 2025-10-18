package com.ecommerce.ui;

import javax.swing.*;
import java.awt.*;

/**
 * UI Constants for consistent styling across the application
 */
public class UIConstants {
    
    // Color Palette
    public static final Color PRIMARY_BLUE = new Color(59, 130, 246);      // #3B82F6
    public static final Color PRIMARY_BLUE_DARK = new Color(37, 99, 235);  // #2563EB
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);      // #22C55E
    public static final Color SUCCESS_GREEN_DARK = new Color(21, 128, 61); // #15803D
    public static final Color WARNING_ORANGE = new Color(249, 115, 22);    // #F97316
    public static final Color ERROR_RED = new Color(239, 68, 68);          // #EF4444
    public static final Color BACKGROUND_WHITE = new Color(255, 255, 255); // #FFFFFF
    public static final Color BACKGROUND_GRAY = new Color(249, 250, 251);  // #F9FAFB
    public static final Color BORDER_GRAY = new Color(229, 231, 235);      // #E5E7EB
    public static final Color TEXT_PRIMARY = new Color(17, 24, 39);        // #111827
    public static final Color TEXT_SECONDARY = new Color(107, 114, 128);   // #6B7280
    public static final Color TEXT_MUTED = new Color(156, 163, 175);       // #9CA3AF
    
    // Fonts
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_PRICE = new Font("Segoe UI", Font.BOLD, 16);
    
    // Spacing
    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_LARGE = 24;
    public static final int PADDING_XLARGE = 32;
    
    // Border Radius
    public static final int RADIUS_SMALL = 6;
    public static final int RADIUS_MEDIUM = 8;
    public static final int RADIUS_LARGE = 12;
    
    // Component Sizes
    public static final int BUTTON_HEIGHT = 40;
    public static final int BUTTON_HEIGHT_SMALL = 32;
    public static final int INPUT_HEIGHT = 40;
    public static final int CARD_HEIGHT = 200;
    public static final int NAVBAR_HEIGHT = 60;
    
    // Shadows
    public static final String SHADOW_SMALL = "0 1px 2px 0 rgba(0, 0, 0, 0.05)";
    public static final String SHADOW_MEDIUM = "0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)";
    public static final String SHADOW_LARGE = "0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)";
    
    // Icons (Text-based for cross-platform compatibility)
    public static final String ICON_CART = "CART";
    public static final String ICON_COINS = "COINS";
    public static final String ICON_USER = "USER";
    public static final String ICON_SEARCH = "SEARCH";
    public static final String ICON_PLUS = "+";
    public static final String ICON_MINUS = "-";
    public static final String ICON_GAME = "GAME";
    public static final String ICON_PRODUCTS = "PRODUCTS";
    public static final String ICON_LOGO = "CoinCart";
    public static final String ICON_CHECK = "✓";
    public static final String ICON_CLOSE = "×";
    public static final String ICON_ARROW_RIGHT = "→";
    public static final String ICON_PLAY = "▶";
    public static final String ICON_TROPHY = "🏆";
    
    // Utility Methods
    public static void applyCardStyle(JPanel panel) {
        panel.setBackground(BACKGROUND_WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_GRAY, 1),
            BorderFactory.createEmptyBorder(PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM)
        ));
    }
    
    public static void applyButtonStyle(JButton button, Color backgroundColor, Color textColor) {
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, BUTTON_HEIGHT));
    }
    
    public static void applyPrimaryButtonStyle(JButton button) {
        applyButtonStyle(button, PRIMARY_BLUE, Color.WHITE);
    }
    
    public static void applySecondaryButtonStyle(JButton button) {
        applyButtonStyle(button, BACKGROUND_WHITE, TEXT_PRIMARY);
        button.setBorder(BorderFactory.createLineBorder(BORDER_GRAY, 1));
    }
    
    public static void applySuccessButtonStyle(JButton button) {
        applyButtonStyle(button, SUCCESS_GREEN, Color.WHITE);
    }
    
    public static void applyInputStyle(JTextField field) {
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_GRAY, 1),
            BorderFactory.createEmptyBorder(PADDING_SMALL, PADDING_MEDIUM, PADDING_SMALL, PADDING_MEDIUM)
        ));
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, INPUT_HEIGHT));
    }
    
    public static void applyLabelStyle(JLabel label, Font font, Color color) {
        label.setFont(font);
        label.setForeground(color);
    }
    
    public static void applyHeadingStyle(JLabel label) {
        applyLabelStyle(label, FONT_HEADING, TEXT_PRIMARY);
    }
    
    public static void applySubheadingStyle(JLabel label) {
        applyLabelStyle(label, FONT_SUBHEADING, TEXT_PRIMARY);
    }
    
    public static void applyBodyStyle(JLabel label) {
        applyLabelStyle(label, FONT_BODY, TEXT_PRIMARY);
    }
    
    public static void applySecondaryStyle(JLabel label) {
        applyLabelStyle(label, FONT_BODY, TEXT_SECONDARY);
    }
    
    public static void applyMutedStyle(JLabel label) {
        applyLabelStyle(label, FONT_SMALL, TEXT_MUTED);
    }
}
