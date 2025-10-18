package com.ecommerce.ui;

import com.ecommerce.models.User;
import com.ecommerce.models.CartItem;
import com.ecommerce.service.CartService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.WalletService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Shopping cart management frame
 */
public class CartFrame extends JFrame {
    private User currentUser;
    private CartService cartService;
    private OrderService orderService;
    private WalletService walletService;
    
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JLabel walletLabel;
    private JSpinner coinsSpinner;
    private JButton updateQuantityButton;
    private JButton removeItemButton;
    private JButton checkoutButton;
    private JButton clearCartButton;
    
    public CartFrame(User user) {
        this.currentUser = user;
        this.cartService = new CartService();
        this.orderService = new OrderService();
        this.walletService = new WalletService();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadCartItems();
        updateWalletDisplay();
    }
    
    private void initializeComponents() {
        // Cart table
        String[] columnNames = {"Product", "Price", "Quantity", "Total", "Stock"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        cartTable = new JTable(tableModel);
        cartTable.setFont(new Font("Arial", Font.PLAIN, 12));
        cartTable.setRowHeight(25);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Labels
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(51, 102, 153));
        
        walletLabel = new JLabel("Wallet Balance: $0.00");
        walletLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        walletLabel.setForeground(new Color(34, 139, 34));
        
        // Coins spinner
        coinsSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 1.0));
        coinsSpinner.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Buttons
        updateQuantityButton = new JButton("Update Quantity");
        updateQuantityButton.setBackground(new Color(51, 102, 153));
        updateQuantityButton.setForeground(Color.WHITE);
        updateQuantityButton.setFont(new Font("Arial", Font.BOLD, 12));
        updateQuantityButton.setEnabled(false);
        
        removeItemButton = new JButton("Remove Item");
        removeItemButton.setBackground(new Color(220, 20, 60));
        removeItemButton.setForeground(Color.WHITE);
        removeItemButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeItemButton.setEnabled(false);
        
        checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(new Color(34, 139, 34));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkoutButton.setPreferredSize(new Dimension(120, 40));
        
        clearCartButton = new JButton("Clear Cart");
        clearCartButton.setBackground(new Color(255, 69, 0));
        clearCartButton.setForeground(Color.WHITE);
        clearCartButton.setFont(new Font("Arial", Font.BOLD, 12));
    }
    
    private void setupLayout() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Shopping Cart - " + currentUser.getName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        setLayout(new BorderLayout());
        
        // Center panel - Cart table
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Cart Items"));
        
        // Right panel - Actions
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setPreferredSize(new Dimension(200, 0));
        
        // Quantity update section
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityPanel.add(new JLabel("New Qty:"));
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantityPanel.add(quantitySpinner);
        quantityPanel.add(updateQuantityButton);
        
        // Remove item section
        JPanel removePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        removePanel.add(removeItemButton);
        
        // Wallet section
        JPanel walletPanel = new JPanel();
        walletPanel.setLayout(new BoxLayout(walletPanel, BoxLayout.Y_AXIS));
        walletPanel.add(walletLabel);
        walletPanel.add(new JLabel("Use Coins:"));
        walletPanel.add(coinsSpinner);
        
        // Total and checkout section
        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setLayout(new BoxLayout(checkoutPanel, BoxLayout.Y_AXIS));
        checkoutPanel.add(totalLabel);
        checkoutPanel.add(Box.createVerticalStrut(10));
        checkoutPanel.add(checkoutButton);
        checkoutPanel.add(Box.createVerticalStrut(10));
        checkoutPanel.add(clearCartButton);
        
        rightPanel.add(quantityPanel);
        rightPanel.add(removePanel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(walletPanel);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(checkoutPanel);
        
        add(scrollPane, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }
    
    private void setupEventHandlers() {
        cartTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = cartTable.getSelectedRow() != -1;
                updateQuantityButton.setEnabled(hasSelection);
                removeItemButton.setEnabled(hasSelection);
            }
        });
        
        updateQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuantity();
            }
        });
        
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItem();
            }
        });
        
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
        
        clearCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearCart();
            }
        });
    }
    
    private void loadCartItems() {
        try {
            List<CartItem> cartItems = cartService.getCartItems(currentUser.getId());
            updateTable(cartItems);
            updateTotal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading cart items: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTable(List<CartItem> cartItems) {
        tableModel.setRowCount(0);
        for (CartItem item : cartItems) {
            Object[] row = {
                item.getProduct().getName(),
                String.format("$%.2f", item.getProduct().getPrice()),
                item.getQuantity(),
                String.format("$%.2f", item.getTotalPrice()),
                item.getProduct().getStock()
            };
            tableModel.addRow(row);
        }
    }
    
    private void updateTotal() {
        try {
            double total = cartService.calculateTotal(currentUser.getId());
            totalLabel.setText("Total: $" + String.format("%.2f", total));
            
            // Update max value for coins spinner
            double walletBalance = walletService.getBalance(currentUser.getId());
            double maxCoins = Math.min(walletBalance, total);
            coinsSpinner.setModel(new SpinnerNumberModel(0.0, 0.0, maxCoins, 1.0));
        } catch (Exception e) {
            totalLabel.setText("Total: Error");
        }
    }
    
    private void updateWalletDisplay() {
        try {
            double balance = walletService.getBalance(currentUser.getId());
            walletLabel.setText("Wallet Balance: $" + String.format("%.2f", balance));
        } catch (Exception e) {
            walletLabel.setText("Wallet Balance: Error");
        }
    }
    
    private void updateQuantity() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Get the cart item from the selected row
            List<CartItem> cartItems = cartService.getCartItems(currentUser.getId());
            if (selectedRow >= cartItems.size()) {
                JOptionPane.showMessageDialog(this, "Invalid selection!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CartItem selectedItem = cartItems.get(selectedRow);
            String input = JOptionPane.showInputDialog(this, 
                "Enter new quantity for " + selectedItem.getProduct().getName() + ":", 
                selectedItem.getQuantity());
            
            if (input != null && !input.trim().isEmpty()) {
                try {
                    int newQuantity = Integer.parseInt(input.trim());
                    boolean success = cartService.updateQuantity(currentUser.getId(), 
                        selectedItem.getProductId(), newQuantity);
                    
                    if (success) {
                        JOptionPane.showMessageDialog(this, 
                            "Quantity updated successfully!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                        loadCartItems();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Failed to update quantity!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, 
                        "Please enter a valid number!", 
                        "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error updating quantity: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removeItem() {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            List<CartItem> cartItems = cartService.getCartItems(currentUser.getId());
            if (selectedRow >= cartItems.size()) {
                JOptionPane.showMessageDialog(this, "Invalid selection!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CartItem selectedItem = cartItems.get(selectedRow);
            int choice = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to remove " + selectedItem.getProduct().getName() + " from cart?", 
                "Confirm Removal", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                boolean success = cartService.removeProduct(currentUser.getId(), selectedItem.getProductId());
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Item removed from cart successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadCartItems();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to remove item from cart!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error removing item: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void checkout() {
        try {
            if (cartService.isCartEmpty(currentUser.getId())) {
                JOptionPane.showMessageDialog(this, 
                    "Your cart is empty!", 
                    "Empty Cart", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double coinsToUse = (Double) coinsSpinner.getValue();
            double total = cartService.calculateTotal(currentUser.getId());
            double finalAmount = Math.max(0, total - coinsToUse);
            
            // Show order summary
            String summary = "Order Summary:\n" +
                "Total Amount: $" + String.format("%.2f", total) + "\n" +
                "Coins Used: $" + String.format("%.2f", coinsToUse) + "\n" +
                "Final Amount: $" + String.format("%.2f", finalAmount) + "\n\n" +
                "Proceed with checkout?";
            
            int choice = JOptionPane.showConfirmDialog(this, summary, 
                "Confirm Checkout", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                var order = orderService.placeOrder(currentUser.getId(), coinsToUse);
                
                if (order != null) {
                    JOptionPane.showMessageDialog(this, 
                        "Order placed successfully!\nOrder ID: " + order.getId() + 
                        "\nFinal Amount: $" + String.format("%.2f", order.getFinalAmount()), 
                        "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Close cart window
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to place order!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error during checkout: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearCart() {
        int choice = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to clear all items from your cart?", 
            "Confirm Clear Cart", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            try {
                boolean success = cartService.clearCart(currentUser.getId());
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Cart cleared successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadCartItems();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to clear cart!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error clearing cart: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
