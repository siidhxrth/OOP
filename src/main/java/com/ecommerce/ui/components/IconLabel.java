package com.ecommerce.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Custom icon label that displays icons as styled text or images
 */
public class IconLabel extends JLabel {
    public enum IconType {
        CART, COINS, USER, SEARCH, PLUS, MINUS, GAME, PRODUCTS, 
        LOGO, CHECK, CLOSE, ARROW_RIGHT, PLAY, TROPHY, HOME
    }
    
    private IconType iconType;
    private Color iconColor;
    private int iconSize;
    
    public IconLabel(IconType iconType) {
        this(iconType, Color.BLACK, 16);
    }
    
    public IconLabel(IconType iconType, Color color, int size) {
        this.iconType = iconType;
        this.iconColor = color;
        this.iconSize = size;
        setIconText();
        setForeground(color);
        setFont(new Font("Segoe UI", Font.PLAIN, size));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }
    
    private void setIconText() {
        switch (iconType) {
            case CART:
                setText("🛒");
                break;
            case COINS:
                setText("💰");
                break;
            case USER:
                setText("👤");
                break;
            case SEARCH:
                setText("🔍");
                break;
            case PLUS:
                setText("+");
                break;
            case MINUS:
                setText("-");
                break;
            case GAME:
                setText("🎮");
                break;
            case PRODUCTS:
                setText("🏠");
                break;
            case LOGO:
                setText("🛒");
                break;
            case CHECK:
                setText("✓");
                break;
            case CLOSE:
                setText("×");
                break;
            case ARROW_RIGHT:
                setText("→");
                break;
            case PLAY:
                setText("▶");
                break;
            case TROPHY:
                setText("🏆");
                break;
            case HOME:
                setText("🏠");
                break;
            default:
                setText("?");
                break;
        }
    }
    
    public void setIconType(IconType iconType) {
        this.iconType = iconType;
        setIconText();
    }
    
    public void setIconColor(Color color) {
        this.iconColor = color;
        setForeground(color);
    }
    
    public void setIconSize(int size) {
        this.iconSize = size;
        setFont(new Font("Segoe UI", Font.PLAIN, size));
    }
    
    // Static factory methods for common icons
    public static IconLabel createCartIcon() {
        return new IconLabel(IconType.CART, new Color(59, 130, 246), 16);
    }
    
    public static IconLabel createCoinsIcon() {
        return new IconLabel(IconType.COINS, new Color(249, 115, 22), 16);
    }
    
    public static IconLabel createUserIcon() {
        return new IconLabel(IconType.USER, new Color(107, 114, 128), 16);
    }
    
    public static IconLabel createSearchIcon() {
        return new IconLabel(IconType.SEARCH, new Color(107, 114, 128), 16);
    }
    
    public static IconLabel createGameIcon() {
        return new IconLabel(IconType.GAME, new Color(59, 130, 246), 16);
    }
    
    public static IconLabel createProductsIcon() {
        return new IconLabel(IconType.PRODUCTS, new Color(59, 130, 246), 16);
    }
    
    public static IconLabel createLogoIcon() {
        return new IconLabel(IconType.LOGO, new Color(59, 130, 246), 24);
    }
    
    public static IconLabel createTrophyIcon() {
        return new IconLabel(IconType.TROPHY, new Color(34, 197, 94), 16);
    }
}
