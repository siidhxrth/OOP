package com.ecommerce.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * JTextField with placeholder text support
 */
public class PlaceholderTextField extends JTextField {
    private String placeholder;
    
    public PlaceholderTextField() {
        super();
    }
    
    public PlaceholderTextField(String placeholder) {
        super();
        this.placeholder = placeholder;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (getText().isEmpty() && placeholder != null && !placeholder.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            FontMetrics fm = g2.getFontMetrics();
            int x = getInsets().left;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }
    
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }
    
    public String getPlaceholder() {
        return placeholder;
    }
}
