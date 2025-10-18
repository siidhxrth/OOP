package com.ecommerce.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Professional line icon component inspired by LineIcons design
 */
public class LineIcon extends JComponent {
    public enum IconType {
        CART, COINS, USER, SEARCH, HOME, GAME, PRODUCTS, 
        LOGO, CHECK, CLOSE, ARROW_RIGHT, PLAY, TROPHY,
        PLUS, MINUS, HEART, STAR, SHOPPING_BAG, GIFT
    }
    
    private IconType iconType;
    private Color iconColor;
    private int iconSize;
    private float strokeWidth;
    
    public LineIcon(IconType iconType) {
        this(iconType, new Color(59, 130, 246), 24, 2.0f);
    }
    
    public LineIcon(IconType iconType, Color color, int size, float strokeWidth) {
        this.iconType = iconType;
        this.iconColor = color;
        this.iconSize = size;
        this.strokeWidth = strokeWidth;
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        // Set color and stroke
        g2d.setColor(iconColor);
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        // Calculate center and scale
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int scale = Math.min(getWidth(), getHeight()) - 4;
        
        // Draw icon based on type
        drawIcon(g2d, centerX, centerY, scale);
        
        g2d.dispose();
    }
    
    private void drawIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        switch (iconType) {
            case CART:
                drawCartIcon(g2d, centerX, centerY, scale);
                break;
            case COINS:
                drawCoinsIcon(g2d, centerX, centerY, scale);
                break;
            case USER:
                drawUserIcon(g2d, centerX, centerY, scale);
                break;
            case SEARCH:
                drawSearchIcon(g2d, centerX, centerY, scale);
                break;
            case HOME:
                drawHomeIcon(g2d, centerX, centerY, scale);
                break;
            case GAME:
                drawGameIcon(g2d, centerX, centerY, scale);
                break;
            case PRODUCTS:
                drawProductsIcon(g2d, centerX, centerY, scale);
                break;
            case LOGO:
                drawLogoIcon(g2d, centerX, centerY, scale);
                break;
            case CHECK:
                drawCheckIcon(g2d, centerX, centerY, scale);
                break;
            case CLOSE:
                drawCloseIcon(g2d, centerX, centerY, scale);
                break;
            case ARROW_RIGHT:
                drawArrowRightIcon(g2d, centerX, centerY, scale);
                break;
            case PLAY:
                drawPlayIcon(g2d, centerX, centerY, scale);
                break;
            case TROPHY:
                drawTrophyIcon(g2d, centerX, centerY, scale);
                break;
            case PLUS:
                drawPlusIcon(g2d, centerX, centerY, scale);
                break;
            case MINUS:
                drawMinusIcon(g2d, centerX, centerY, scale);
                break;
            case HEART:
                drawHeartIcon(g2d, centerX, centerY, scale);
                break;
            case STAR:
                drawStarIcon(g2d, centerX, centerY, scale);
                break;
            case SHOPPING_BAG:
                drawShoppingBagIcon(g2d, centerX, centerY, scale);
                break;
            case GIFT:
                drawGiftIcon(g2d, centerX, centerY, scale);
                break;
        }
    }
    
    private void drawCartIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        int h = scale / 2;
        
        // Cart body
        g2d.drawRoundRect(centerX - w, centerY - h/2, w*2, h, 4, 4);
        
        // Cart handle
        g2d.drawLine(centerX + w, centerY - h/2, centerX + w + 4, centerY - h/2 - 4);
        
        // Cart wheels
        g2d.drawOval(centerX - w + 2, centerY + h/2 - 2, 4, 4);
        g2d.drawOval(centerX + w - 6, centerY + h/2 - 2, 4, 4);
    }
    
    private void drawCoinsIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int r = scale / 3;
        
        // Main coin
        g2d.drawOval(centerX - r, centerY - r, r*2, r*2);
        
        // Dollar sign
        g2d.drawLine(centerX, centerY - r/2, centerX, centerY + r/2);
        g2d.drawLine(centerX - r/3, centerY - r/4, centerX + r/3, centerY - r/4);
        g2d.drawLine(centerX - r/3, centerY + r/4, centerX + r/3, centerY + r/4);
    }
    
    private void drawUserIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int r = scale / 3;
        
        // Head
        g2d.drawOval(centerX - r/2, centerY - r, r, r);
        
        // Body
        g2d.drawLine(centerX, centerY, centerX, centerY + r);
        
        // Arms
        g2d.drawLine(centerX, centerY - r/2, centerX - r, centerY);
        g2d.drawLine(centerX, centerY - r/2, centerX + r, centerY);
        
        // Legs
        g2d.drawLine(centerX, centerY + r, centerX - r/2, centerY + r + r/2);
        g2d.drawLine(centerX, centerY + r, centerX + r/2, centerY + r + r/2);
    }
    
    private void drawSearchIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int r = scale / 3;
        
        // Search circle
        g2d.drawOval(centerX - r, centerY - r, r*2, r*2);
        
        // Search handle
        int handleX = centerX + r;
        int handleY = centerY + r;
        g2d.drawLine(handleX, handleY, handleX + r/2, handleY + r/2);
    }
    
    private void drawHomeIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 2;
        int h = scale / 2;
        
        // House shape
        int[] xPoints = {centerX, centerX - w, centerX + w};
        int[] yPoints = {centerY - h, centerY + h/2, centerY + h/2};
        g2d.drawPolygon(xPoints, yPoints, 3);
        
        // House base
        g2d.drawRect(centerX - w/2, centerY, w, h/2);
    }
    
    private void drawGameIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 2;
        
        // Game controller
        g2d.drawRoundRect(centerX - w, centerY - w/2, w*2, w, 8, 8);
        
        // Buttons
        g2d.drawOval(centerX - w/2, centerY - w/4, w/4, w/4);
        g2d.drawOval(centerX + w/4, centerY - w/4, w/4, w/4);
    }
    
    private void drawProductsIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        
        // Product boxes
        g2d.drawRect(centerX - w, centerY - w, w, w);
        g2d.drawRect(centerX, centerY - w, w, w);
        g2d.drawRect(centerX - w, centerY, w, w);
        g2d.drawRect(centerX, centerY, w, w);
    }
    
    private void drawLogoIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        // "C" for CoinCart
        int r = scale / 3;
        g2d.drawArc(centerX - r, centerY - r, r*2, r*2, 45, 270);
    }
    
    private void drawCheckIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        g2d.drawLine(centerX - w, centerY, centerX - w/2, centerY + w/2);
        g2d.drawLine(centerX - w/2, centerY + w/2, centerX + w, centerY - w);
    }
    
    private void drawCloseIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        g2d.drawLine(centerX - w, centerY - w, centerX + w, centerY + w);
        g2d.drawLine(centerX + w, centerY - w, centerX - w, centerY + w);
    }
    
    private void drawArrowRightIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        g2d.drawLine(centerX - w, centerY, centerX + w, centerY);
        g2d.drawLine(centerX + w/2, centerY - w/2, centerX + w, centerY);
        g2d.drawLine(centerX + w/2, centerY + w/2, centerX + w, centerY);
    }
    
    private void drawPlayIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        int[] xPoints = {centerX - w, centerX - w, centerX + w};
        int[] yPoints = {centerY - w, centerY + w, centerY};
        g2d.drawPolygon(xPoints, yPoints, 3);
    }
    
    private void drawTrophyIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        int h = scale / 2;
        
        // Trophy cup
        g2d.drawRoundRect(centerX - w/2, centerY - h/2, w, h, 4, 4);
        
        // Trophy handles
        g2d.drawArc(centerX - w, centerY - h/4, w/2, h/2, 0, 90);
        g2d.drawArc(centerX + w/2, centerY - h/4, w/2, h/2, 90, 90);
    }
    
    private void drawPlusIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        g2d.drawLine(centerX - w, centerY, centerX + w, centerY);
        g2d.drawLine(centerX, centerY - w, centerX, centerY + w);
    }
    
    private void drawMinusIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        g2d.drawLine(centerX - w, centerY, centerX + w, centerY);
    }
    
    private void drawHeartIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        int h = scale / 3;
        
        // Heart shape (simplified)
        g2d.drawArc(centerX - w, centerY - h/2, w, h, 0, 180);
        g2d.drawArc(centerX, centerY - h/2, w, h, 0, 180);
        g2d.drawLine(centerX - w, centerY, centerX + w, centerY);
    }
    
    private void drawStarIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int r = scale / 3;
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];
        
        for (int i = 0; i < 10; i++) {
            double angle = i * Math.PI / 5;
            int radius = (i % 2 == 0) ? r : r/2;
            xPoints[i] = centerX + (int)(radius * Math.cos(angle - Math.PI/2));
            yPoints[i] = centerY + (int)(radius * Math.sin(angle - Math.PI/2));
        }
        g2d.drawPolygon(xPoints, yPoints, 10);
    }
    
    private void drawShoppingBagIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        int h = scale / 2;
        
        // Bag body
        g2d.drawRoundRect(centerX - w/2, centerY - h/2, w, h, 4, 4);
        
        // Bag handles
        g2d.drawLine(centerX - w/2, centerY - h/2, centerX - w/2 - 2, centerY - h/2 - 4);
        g2d.drawLine(centerX + w/2, centerY - h/2, centerX + w/2 + 2, centerY - h/2 - 4);
    }
    
    private void drawGiftIcon(Graphics2D g2d, int centerX, int centerY, int scale) {
        int w = scale / 3;
        int h = scale / 2;
        
        // Gift box
        g2d.drawRoundRect(centerX - w/2, centerY - h/2, w, h, 2, 2);
        
        // Ribbon
        g2d.drawLine(centerX, centerY - h/2, centerX, centerY + h/2);
        g2d.drawLine(centerX - w/2, centerY, centerX + w/2, centerY);
    }
    
    // Getters and setters
    public void setIconType(IconType iconType) {
        this.iconType = iconType;
        repaint();
    }
    
    public void setIconColor(Color color) {
        this.iconColor = color;
        repaint();
    }
    
    public void setIconSize(int size) {
        this.iconSize = size;
        setPreferredSize(new Dimension(size, size));
        repaint();
    }
    
    public void setStrokeWidth(float width) {
        this.strokeWidth = width;
        repaint();
    }
    
    // Static factory methods for common icons
    public static LineIcon createCartIcon() {
        return new LineIcon(IconType.CART, new Color(59, 130, 246), 20, 2.0f);
    }
    
    public static LineIcon createCoinsIcon() {
        return new LineIcon(IconType.COINS, new Color(249, 115, 22), 20, 2.0f);
    }
    
    public static LineIcon createUserIcon() {
        return new LineIcon(IconType.USER, new Color(107, 114, 128), 20, 2.0f);
    }
    
    public static LineIcon createSearchIcon() {
        return new LineIcon(IconType.SEARCH, new Color(107, 114, 128), 20, 2.0f);
    }
    
    public static LineIcon createGameIcon() {
        return new LineIcon(IconType.GAME, new Color(59, 130, 246), 20, 2.0f);
    }
    
    public static LineIcon createProductsIcon() {
        return new LineIcon(IconType.PRODUCTS, new Color(59, 130, 246), 20, 2.0f);
    }
    
    public static LineIcon createLogoIcon() {
        return new LineIcon(IconType.LOGO, new Color(59, 130, 246), 32, 3.0f);
    }
    
    public static LineIcon createTrophyIcon() {
        return new LineIcon(IconType.TROPHY, new Color(34, 197, 94), 20, 2.0f);
    }
}
