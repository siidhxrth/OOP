package com.ecommerce.ui;

import com.ecommerce.models.User;
import com.ecommerce.ui.components.NavigationBar;
import com.ecommerce.ui.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Games page with Quiz Challenge card and instructions
 */
public class GamesFrame extends JFrame {
    private User currentUser;
    private NavigationBar navigationBar;
    private JPanel contentPanel;
    private JLabel pageTitleLabel;
    private JPanel quizChallengeCard;
    private JPanel howToPlayPanel;
    
    public GamesFrame(User user) {
        this.currentUser = user;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Games - CoinCart");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(UIConstants.BACKGROUND_GRAY);
    }
    
    private void initializeComponents() {
        // Navigation bar
        navigationBar = new NavigationBar(currentUser);
        
        // Content panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UIConstants.BACKGROUND_GRAY);
        
        // Page title
        pageTitleLabel = new JLabel("Games");
        UIConstants.applyHeadingStyle(pageTitleLabel);
        pageTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        
        // Quiz Challenge Card
        quizChallengeCard = createQuizChallengeCard();
        
        // How to Play Panel
        howToPlayPanel = createHowToPlayPanel();
    }
    
    private JPanel createQuizChallengeCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UIConstants.SUCCESS_GREEN);
        card.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        card.setPreferredSize(new Dimension(600, 300));
        
        // Top section with icon and title
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(UIConstants.SUCCESS_GREEN);
        
        // Icon
        JLabel iconLabel = new JLabel("G", JLabel.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        iconLabel.setPreferredSize(new Dimension(80, 80));
        iconLabel.setOpaque(true);
        iconLabel.setBackground(new Color(34, 197, 94, 100)); // Light green with transparency
        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title and subtitle
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(UIConstants.SUCCESS_GREEN);
        
        JLabel titleLabel = new JLabel("Quiz Challenge");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Test your knowledge & earn coins");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.WHITE);
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        topSection.add(iconLabel, BorderLayout.WEST);
        topSection.add(titlePanel, BorderLayout.CENTER);
        
        // Details section
        JPanel detailsPanel = new JPanel(new GridLayout(2, 2, 20, 10));
        detailsPanel.setBackground(UIConstants.SUCCESS_GREEN);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel questionsLabel = new JLabel("Questions");
        questionsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        questionsLabel.setForeground(Color.WHITE);
        
        JLabel questionsValue = new JLabel("5");
        questionsValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        questionsValue.setForeground(Color.WHITE);
        
        JLabel rewardLabel = new JLabel("Reward per correct answer");
        rewardLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rewardLabel.setForeground(Color.WHITE);
        
        JLabel rewardValue = new JLabel("20 Coins");
        rewardValue.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rewardValue.setForeground(Color.WHITE);
        
        detailsPanel.add(questionsLabel);
        detailsPanel.add(questionsValue);
        detailsPanel.add(rewardLabel);
        detailsPanel.add(rewardValue);
        
        // Start Playing button
        JButton startButton = new JButton("▶ Start Playing");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(UIConstants.TEXT_PRIMARY);
        startButton.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1));
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(UIConstants.SUCCESS_GREEN);
        buttonPanel.add(startButton);
        
        // Add components to card
        card.add(topSection, BorderLayout.NORTH);
        card.add(detailsPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listener
        startButton.addActionListener(e -> openQuizGame());
        
        return card;
    }
    
    private JPanel createHowToPlayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setPreferredSize(new Dimension(600, 200));
        
        JLabel titleLabel = new JLabel("How to Play");
        UIConstants.applySubheadingStyle(titleLabel);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        // Instructions list
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));
        instructionsPanel.setBackground(Color.WHITE);
        
        String[] instructions = {
            "Answer 5 multiple-choice questions",
            "Each correct answer earns you 20 coins",
            "Coins are automatically added to your wallet",
            "Use coins to get discounts on your orders!"
        };
        
        for (int i = 0; i < instructions.length; i++) {
            JPanel instructionItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            instructionItem.setBackground(Color.WHITE);
            
            // Number badge
            JLabel numberLabel = new JLabel(String.valueOf(i + 1));
            numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            numberLabel.setForeground(Color.WHITE);
            numberLabel.setOpaque(true);
            numberLabel.setBackground(UIConstants.PRIMARY_BLUE);
            numberLabel.setPreferredSize(new Dimension(24, 24));
            numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
            numberLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            
            // Instruction text
            JLabel instructionLabel = new JLabel(instructions[i]);
            UIConstants.applyBodyStyle(instructionLabel);
            instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
            
            instructionItem.add(numberLabel);
            instructionItem.add(instructionLabel);
            instructionsPanel.add(instructionItem);
            instructionsPanel.add(Box.createVerticalStrut(15));
        }
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(instructionsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Add navigation bar at top
        add(navigationBar, BorderLayout.NORTH);
        
        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(UIConstants.BACKGROUND_GRAY);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(UIConstants.PADDING_LARGE, UIConstants.PADDING_LARGE, UIConstants.PADDING_MEDIUM, 0));
        titlePanel.add(pageTitleLabel);
        
        // Center content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(UIConstants.BACKGROUND_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        
        // Quiz Challenge Card
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(quizChallengeCard, gbc);
        
        // How to Play Panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(howToPlayPanel, gbc);
        
        contentPanel.add(titlePanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Navigation bar listeners
        navigationBar.setProductsListener(e -> openProductsWindow());
        navigationBar.setGamesListener(e -> {}); // Already in games
        navigationBar.setCartListener(e -> openCartWindow());
        navigationBar.setUserListener(e -> openProfileWindow());
    }
    
    private void openQuizGame() {
        SwingUtilities.invokeLater(() -> {
            new QuizGameFrame(currentUser).setVisible(true);
        });
    }
    
    private void openProductsWindow() {
        SwingUtilities.invokeLater(() -> {
            new ProductsFrame(currentUser).setVisible(true);
        });
    }
    
    private void openCartWindow() {
        SwingUtilities.invokeLater(() -> {
            new CartFrame(currentUser).setVisible(true);
        });
    }
    
    private void openProfileWindow() {
        SwingUtilities.invokeLater(() -> {
            new ProfileFrame(currentUser).setVisible(true);
        });
    }
}
