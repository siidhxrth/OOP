package com.ecommerce.ui;

import com.ecommerce.models.User;
import com.ecommerce.models.Transaction;
import com.ecommerce.service.WalletService;
import com.ecommerce.service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Wallet management frame
 */
public class WalletFrame extends JFrame {
    private User currentUser;
    private WalletService walletService;
    private TransactionService transactionService;
    
    private JLabel balanceLabel;
    private JTable transactionsTable;
    private DefaultTableModel tableModel;
    private JSpinner addCoinsSpinner;
    private JButton addCoinsButton;
    private JButton refreshButton;
    
    public WalletFrame(User user) {
        this.currentUser = user;
        this.walletService = new WalletService();
        this.transactionService = new TransactionService();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadData();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Wallet - " + user.getName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initializeComponents() {
        // Balance label
        balanceLabel = new JLabel("Balance: $0.00");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        balanceLabel.setForeground(new Color(34, 139, 34));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Transactions table
        String[] columnNames = {"Date", "Type", "Amount", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        transactionsTable = new JTable(tableModel);
        transactionsTable.setFont(new Font("Arial", Font.PLAIN, 12));
        transactionsTable.setRowHeight(25);
        transactionsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Add coins components
        addCoinsSpinner = new JSpinner(new SpinnerNumberModel(10.0, 1.0, 1000.0, 1.0));
        addCoinsSpinner.setFont(new Font("Arial", Font.PLAIN, 12));
        
        addCoinsButton = new JButton("Add Coins");
        addCoinsButton.setBackground(new Color(34, 139, 34));
        addCoinsButton.setForeground(Color.WHITE);
        addCoinsButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(51, 102, 153));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel - Balance and Add Coins
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        topPanel.add(balanceLabel, BorderLayout.CENTER);
        
        JPanel addCoinsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addCoinsPanel.setBackground(Color.WHITE);
        addCoinsPanel.add(new JLabel("Add Coins: $"));
        addCoinsPanel.add(addCoinsSpinner);
        addCoinsPanel.add(addCoinsButton);
        addCoinsPanel.add(Box.createHorizontalStrut(20));
        addCoinsPanel.add(refreshButton);
        
        topPanel.add(addCoinsPanel, BorderLayout.SOUTH);
        
        // Center panel - Transactions table
        JScrollPane scrollPane = new JScrollPane(transactionsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transaction History"));
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        addCoinsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCoins();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });
    }
    
    private void loadData() {
        loadBalance();
        loadTransactions();
    }
    
    private void loadBalance() {
        try {
            double balance = walletService.getBalance(currentUser.getId());
            balanceLabel.setText("Balance: $" + String.format("%.2f", balance));
        } catch (Exception e) {
            balanceLabel.setText("Balance: Error loading");
            JOptionPane.showMessageDialog(this, "Error loading balance: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadTransactions() {
        try {
            List<Transaction> transactions = transactionService.getTransactionHistory(currentUser.getId());
            updateTable(transactions);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading transactions: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTable(List<Transaction> transactions) {
        tableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (Transaction transaction : transactions) {
            String typeDisplay = transaction.getType().toUpperCase();
            String amountDisplay = (transaction.getType().equals("earned") ? "+" : "-") + 
                "$" + String.format("%.2f", transaction.getAmount());
            
            Object[] row = {
                dateFormat.format(transaction.getTransactionDate()),
                typeDisplay,
                amountDisplay,
                transaction.getDescription()
            };
            tableModel.addRow(row);
        }
    }
    
    private void addCoins() {
        try {
            double amount = (Double) addCoinsSpinner.getValue();
            String description = JOptionPane.showInputDialog(this, 
                "Enter description for adding coins:", 
                "Manual coin addition");
            
            if (description != null && !description.trim().isEmpty()) {
                boolean success = walletService.addCoins(currentUser.getId(), amount, description.trim());
                
                if (success) {
                    JOptionPane.showMessageDialog(this, 
                        "Coins added successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to add coins!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (description != null) {
                JOptionPane.showMessageDialog(this, 
                    "Description cannot be empty!", 
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error adding coins: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
