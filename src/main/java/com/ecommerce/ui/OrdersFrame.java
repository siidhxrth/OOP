package com.ecommerce.ui;

import com.ecommerce.models.User;
import com.ecommerce.models.Order;
import com.ecommerce.models.OrderItem;
import com.ecommerce.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Orders management frame
 */
public class OrdersFrame extends JFrame {
    private User currentUser;
    private OrderService orderService;
    
    private JTable ordersTable;
    private DefaultTableModel tableModel;
    private JButton viewDetailsButton;
    private JButton cancelOrderButton;
    private JButton refreshButton;
    
    public OrdersFrame(User user) {
        this.currentUser = user;
        this.orderService = new OrderService();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadOrders();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Orders - " + user.getName());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initializeComponents() {
        // Orders table
        String[] columnNames = {"Order ID", "Date", "Total", "Coins Used", "Final Amount", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        ordersTable = new JTable(tableModel);
        ordersTable.setFont(new Font("Arial", Font.PLAIN, 12));
        ordersTable.setRowHeight(25);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Buttons
        viewDetailsButton = new JButton("View Details");
        viewDetailsButton.setBackground(new Color(51, 102, 153));
        viewDetailsButton.setForeground(Color.WHITE);
        viewDetailsButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewDetailsButton.setEnabled(false);
        
        cancelOrderButton = new JButton("Cancel Order");
        cancelOrderButton.setBackground(new Color(220, 20, 60));
        cancelOrderButton.setForeground(Color.WHITE);
        cancelOrderButton.setFont(new Font("Arial", Font.BOLD, 12));
        cancelOrderButton.setEnabled(false);
        
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(34, 139, 34));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel - Buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        topPanel.add(viewDetailsButton);
        topPanel.add(cancelOrderButton);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(refreshButton);
        
        // Center panel - Orders table
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Order History"));
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        ordersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean hasSelection = ordersTable.getSelectedRow() != -1;
                viewDetailsButton.setEnabled(hasSelection);
                cancelOrderButton.setEnabled(hasSelection);
            }
        });
        
        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrderDetails();
            }
        });
        
        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelOrder();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadOrders();
            }
        });
    }
    
    private void loadOrders() {
        try {
            List<Order> orders = orderService.getOrdersByUserId(currentUser.getId());
            updateTable(orders);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTable(List<Order> orders) {
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        for (Order order : orders) {
            Object[] row = {
                order.getId(),
                dateFormat.format(order.getOrderDate()),
                String.format("$%.2f", order.getTotalAmount()),
                String.format("$%.2f", order.getCoinsUsed()),
                String.format("$%.2f", order.getFinalAmount()),
                order.getStatus().toUpperCase()
            };
            tableModel.addRow(row);
        }
    }
    
    private void viewOrderDetails() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int orderId = (Integer) tableModel.getValueAt(selectedRow, 0);
            Order order = orderService.getOrderById(orderId);
            
            if (order != null) {
                List<OrderItem> orderItems = orderService.getOrderItems(orderId);
                showOrderDetailsDialog(order, orderItems);
            } else {
                JOptionPane.showMessageDialog(this, "Order not found!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading order details: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showOrderDetailsDialog(Order order, List<OrderItem> orderItems) {
        JDialog dialog = new JDialog(this, "Order Details - #" + order.getId(), true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Order summary
        JPanel summaryPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        summaryPanel.add(new JLabel("Order ID:"));
        summaryPanel.add(new JLabel(String.valueOf(order.getId())));
        summaryPanel.add(new JLabel("Date:"));
        summaryPanel.add(new JLabel(dateFormat.format(order.getOrderDate())));
        summaryPanel.add(new JLabel("Status:"));
        summaryPanel.add(new JLabel(order.getStatus().toUpperCase()));
        summaryPanel.add(new JLabel("Total Amount:"));
        summaryPanel.add(new JLabel("$" + String.format("%.2f", order.getTotalAmount())));
        summaryPanel.add(new JLabel("Coins Used:"));
        summaryPanel.add(new JLabel("$" + String.format("%.2f", order.getCoinsUsed())));
        summaryPanel.add(new JLabel("Final Amount:"));
        summaryPanel.add(new JLabel("$" + String.format("%.2f", order.getFinalAmount())));
        
        // Order items table
        String[] columnNames = {"Product", "Quantity", "Price", "Total"};
        DefaultTableModel itemsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (OrderItem item : orderItems) {
            Object[] row = {
                item.getProduct().getName(),
                item.getQuantity(),
                String.format("$%.2f", item.getPrice()),
                String.format("$%.2f", item.getTotalPrice())
            };
            itemsTableModel.addRow(row);
        }
        
        JTable itemsTable = new JTable(itemsTableModel);
        itemsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        itemsTable.setRowHeight(25);
        itemsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane itemsScrollPane = new JScrollPane(itemsTable);
        itemsScrollPane.setBorder(BorderFactory.createTitledBorder("Order Items"));
        
        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(51, 102, 153));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        
        mainPanel.add(summaryPanel, BorderLayout.NORTH);
        mainPanel.add(itemsScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
    
    private void cancelOrder() {
        int selectedRow = ordersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order!", 
                "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int orderId = (Integer) tableModel.getValueAt(selectedRow, 0);
            String status = (String) tableModel.getValueAt(selectedRow, 5);
            
            if (!"PENDING".equals(status) && !"CONFIRMED".equals(status)) {
                JOptionPane.showMessageDialog(this, 
                    "Cannot cancel order with status: " + status, 
                    "Cannot Cancel", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int choice = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to cancel Order #" + orderId + "?", 
                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                boolean success = orderService.cancelOrder(orderId, currentUser.getId());
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Order cancelled successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadOrders();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to cancel order!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error cancelling order: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
