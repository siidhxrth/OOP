package com.ecommerce.ui.components;

import com.ecommerce.models.Product;
import com.ecommerce.ui.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Modern Product Card Component matching dropshipping theme
 */
public class ProductCard extends JPanel {
    private Product product;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel priceLabel;
    private JButton addButton;
    private JLabel categoryLabel;
    private JPanel buttonContainer;
    private ActionListener addToCartListener;
    
    public ProductCard(Product product) {
        this.product = product;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(280, 380));
        setMaximumSize(new Dimension(280, 380));
        UIConstants.applyCardStyle(this);
        
        // Product image
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(280, 200));
        imageLabel.setBackground(UIConstants.BACKGROUND_GRAY);
        imageLabel.setOpaque(true);
        imageLabel.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1));
        loadProductImage();
        
        // Category badge
        categoryLabel = new JLabel(product.getCategory());
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setOpaque(true);
        categoryLabel.setBackground(UIConstants.SUCCESS_GREEN);
        categoryLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Product name
        nameLabel = new JLabel(product.getName());
        UIConstants.applySubheadingStyle(nameLabel);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING_SMALL, 0, UIConstants.PADDING_SMALL, 0));
        
        // Product description
        String description = product.getDescription();
        if (description != null && description.length() > 80) {
            description = description.substring(0, 77) + "...";
        }
        descriptionLabel = new JLabel(description != null ? description : "");
        UIConstants.applySecondaryStyle(descriptionLabel);
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, UIConstants.PADDING_SMALL, 0));
        
        // Price
        priceLabel = new JLabel("$" + String.format("%.2f", product.getPrice()));
        UIConstants.applyLabelStyle(priceLabel, UIConstants.FONT_PRICE, UIConstants.PRIMARY_BLUE);
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        priceLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, UIConstants.PADDING_MEDIUM, 0));
        
        // Add button with icon
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        buttonContainer.setBackground(UIConstants.BACKGROUND_WHITE);
        
        LineIcon arrowIcon = new LineIcon(LineIcon.IconType.ARROW_RIGHT, UIConstants.PRIMARY_BLUE, 16, 2.0f);
        buttonContainer.add(arrowIcon);
        
        addButton = new JButton("Import Product");
        UIConstants.applyPrimaryButtonStyle(addButton);
        addButton.setPreferredSize(new Dimension(200, UIConstants.BUTTON_HEIGHT_SMALL));
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        buttonContainer.add(addButton);
    }
    
    private void setupLayout() {
        // Image panel with category badge overlay
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(UIConstants.BACKGROUND_WHITE);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        // Category badge positioned at top-right
        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        badgePanel.setOpaque(false);
        badgePanel.add(categoryLabel);
        imagePanel.add(badgePanel, BorderLayout.NORTH);
        
        add(imagePanel, BorderLayout.NORTH);
        
        // Center panel for product info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING_MEDIUM, UIConstants.PADDING_MEDIUM, 0, UIConstants.PADDING_MEDIUM));
        
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        infoPanel.add(descriptionLabel);
        infoPanel.add(Box.createVerticalStrut(UIConstants.PADDING_SMALL));
        infoPanel.add(priceLabel);
        infoPanel.add(Box.createVerticalGlue());
        
        add(infoPanel, BorderLayout.CENTER);
        
        // Bottom panel for button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(UIConstants.BACKGROUND_WHITE);
        buttonPanel.add(buttonContainer);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING_SMALL, UIConstants.PADDING_MEDIUM, UIConstants.PADDING_MEDIUM, UIConstants.PADDING_MEDIUM));
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        addButton.addActionListener(e -> {
            if (addToCartListener != null) {
                addToCartListener.actionPerformed(e);
            }
        });
    }
    
    public void setAddToCartListener(ActionListener listener) {
        this.addToCartListener = listener;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setStockStatus(boolean inStock) {
        if (inStock) {
            addButton.setEnabled(true);
            addButton.setText("Import Product →");
            UIConstants.applyPrimaryButtonStyle(addButton);
        } else {
            addButton.setEnabled(false);
            addButton.setText("Out of Stock");
            addButton.setBackground(UIConstants.TEXT_MUTED);
            addButton.setForeground(Color.WHITE);
        }
    }
    
    private void loadProductImage() {
        if (product.getImageUrl() == null || product.getImageUrl().isEmpty()) {
            imageLabel.setText("No Image");
            imageLabel.setIcon(null);
            return;
        }
        
        // Use SwingWorker to load image in background
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                try {
                    URL url = new URL(product.getImageUrl());
                    BufferedImage originalImage = ImageIO.read(url);
                    if (originalImage != null) {
                        // Scale image to fit the label size
                        Image scaledImage = originalImage.getScaledInstance(
                            imageLabel.getPreferredSize().width,
                            imageLabel.getPreferredSize().height,
                            Image.SCALE_SMOOTH
                        );
                        return new ImageIcon(scaledImage);
                    }
                } catch (Exception e) {
                    System.err.println("Error loading image for product " + product.getName() + ": " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    if (icon != null) {
                        imageLabel.setIcon(icon);
                        imageLabel.setText(""); // Clear "No Image" text
                    } else {
                        imageLabel.setText("Image Error");
                        imageLabel.setIcon(null);
                    }
                } catch (Exception e) {
                    imageLabel.setText("Image Error");
                    imageLabel.setIcon(null);
                    System.err.println("Error setting image for product " + product.getName() + ": " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
}
