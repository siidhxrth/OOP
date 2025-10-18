package com.ecommerce.ui;

import com.ecommerce.models.User;
import com.ecommerce.models.Question;
import com.ecommerce.models.GameSession;
import com.ecommerce.service.QuizGameService;
import com.ecommerce.ui.components.NavigationBar;
import com.ecommerce.ui.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Modern Quiz Game frame matching CoinCart design exactly
 */
public class QuizGameFrame extends JFrame {
    private User currentUser;
    private QuizGameService quizGameService;
    
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int questionsAnswered = 0;
    
    private NavigationBar navigationBar;
    private JPanel contentPanel;
    private JLabel pageTitleLabel;
    private JPanel quizCard;
    private JLabel questionNumberLabel;
    private JProgressBar progressBar;
    private JLabel scoreLabel;
    private JLabel questionLabel;
    private JButton optionA;
    private JButton optionB;
    private JButton optionC;
    private JButton optionD;
    private String selectedAnswer;
    
    public QuizGameFrame(User user) {
        this.currentUser = user;
        this.quizGameService = new QuizGameService();
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        startNewGame();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Quiz Game - CoinCart");
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
        
        // Quiz card
        quizCard = createQuizCard();
    }
    
    private JPanel createQuizCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        card.setPreferredSize(new Dimension(800, 500));
        
        // Top section with question number and score
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBackground(Color.WHITE);
        
        // Question number
        questionNumberLabel = new JLabel("Question 1 of 5");
        UIConstants.applyMutedStyle(questionNumberLabel);
        questionNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Progress bar
        progressBar = new JProgressBar(0, 5);
        progressBar.setValue(1);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(200, 8));
        progressBar.setBackground(UIConstants.BORDER_GRAY);
        progressBar.setForeground(UIConstants.PRIMARY_BLUE);
        progressBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Score
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        scoreLabel.setForeground(UIConstants.SUCCESS_GREEN);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(34, 197, 94, 30));
        scoreLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.SUCCESS_GREEN, 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        
        JPanel leftTop = new JPanel(new BorderLayout());
        leftTop.setBackground(Color.WHITE);
        leftTop.add(questionNumberLabel, BorderLayout.NORTH);
        leftTop.add(progressBar, BorderLayout.SOUTH);
        
        topSection.add(leftTop, BorderLayout.WEST);
        topSection.add(scoreLabel, BorderLayout.EAST);
        
        // Question
        questionLabel = new JLabel("Which is the largest state in India by area?");
        UIConstants.applySubheadingStyle(questionLabel);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        // Options
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        optionsPanel.setBackground(Color.WHITE);
        
        optionA = createOptionButton("Rajasthan");
        optionB = createOptionButton("Madhya Pradesh");
        optionC = createOptionButton("Maharashtra");
        optionD = createOptionButton("Uttar Pradesh");
        
        optionsPanel.add(optionA);
        optionsPanel.add(optionB);
        optionsPanel.add(optionC);
        optionsPanel.add(optionD);
        
        // Add components to card
        card.add(topSection, BorderLayout.NORTH);
        card.add(questionLabel, BorderLayout.CENTER);
        card.add(optionsPanel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JButton createOptionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(UIConstants.TEXT_PRIMARY);
        button.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1));
        button.setPreferredSize(new Dimension(0, 50));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(UIConstants.PRIMARY_BLUE)) {
                    button.setBackground(new Color(248, 250, 252));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.getBackground().equals(UIConstants.PRIMARY_BLUE)) {
                    button.setBackground(Color.WHITE);
                }
            }
        });
        
        return button;
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
        
        // Quiz Card
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(quizCard, gbc);
        
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
        
        // Option button listeners
        optionA.addActionListener(e -> selectOption("A", optionA));
        optionB.addActionListener(e -> selectOption("B", optionB));
        optionC.addActionListener(e -> selectOption("C", optionC));
        optionD.addActionListener(e -> selectOption("D", optionD));
    }
    
    private void selectOption(String option, JButton button) {
        // Reset all buttons
        resetOptionButtons();
        
        // Highlight selected button
        button.setBackground(UIConstants.PRIMARY_BLUE);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(UIConstants.PRIMARY_BLUE_DARK, 2));
        
        selectedAnswer = option;
        
        // Auto-submit after selection
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(1000); // 1 second delay
                submitAnswer();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    private void resetOptionButtons() {
        JButton[] buttons = {optionA, optionB, optionC, optionD};
        for (JButton button : buttons) {
            button.setBackground(Color.WHITE);
            button.setForeground(UIConstants.TEXT_PRIMARY);
            button.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER_GRAY, 1));
        }
    }
    
    private void startNewGame() {
        try {
            questions = quizGameService.startGame(currentUser.getId(), 5);
            currentQuestionIndex = 0;
            score = 0;
            questionsAnswered = 0;
            
            displayCurrentQuestion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error starting game: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void displayCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            
            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            progressBar.setValue(currentQuestionIndex + 1);
            scoreLabel.setText("Score: " + score);
            questionLabel.setText(question.getQuestionText());
            
            optionA.setText(question.getOptionA());
            optionB.setText(question.getOptionB());
            optionC.setText(question.getOptionC());
            optionD.setText(question.getOptionD());
            
            resetOptionButtons();
            selectedAnswer = null;
        }
    }
    
    private void submitAnswer() {
        if (selectedAnswer == null) {
            return;
        }
        
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean isCorrect = quizGameService.submitAnswer(currentQuestion, selectedAnswer);
        
        questionsAnswered++;
        if (isCorrect) {
            score++;
        }
        
        // Show correct answer
        showCorrectAnswer(currentQuestion);
        
        // Move to next question after delay
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(2000); // 2 second delay
                nextQuestion();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    private void showCorrectAnswer(Question question) {
        JButton[] buttons = {optionA, optionB, optionC, optionD};
        String[] options = {"A", "B", "C", "D"};
        
        for (int i = 0; i < buttons.length; i++) {
            if (options[i].equals(question.getCorrectOption())) {
                buttons[i].setBackground(UIConstants.SUCCESS_GREEN);
                buttons[i].setForeground(Color.WHITE);
                buttons[i].setBorder(BorderFactory.createLineBorder(UIConstants.SUCCESS_GREEN_DARK, 2));
            } else if (options[i].equals(selectedAnswer) && !options[i].equals(question.getCorrectOption())) {
                buttons[i].setBackground(UIConstants.ERROR_RED);
                buttons[i].setForeground(Color.WHITE);
                buttons[i].setBorder(BorderFactory.createLineBorder(UIConstants.ERROR_RED, 2));
            }
        }
    }
    
    private void nextQuestion() {
        currentQuestionIndex++;
        
        if (currentQuestionIndex < questions.size()) {
            displayCurrentQuestion();
        } else {
            // Game finished
            endGame();
        }
    }
    
    private void endGame() {
        try {
            GameSession gameSession = quizGameService.endGame(currentUser.getId(), score, questionsAnswered);
            
            if (gameSession != null) {
                double scorePercentage = (double) score / questionsAnswered * 100;
                String message = "Quiz Complete!\n\n" +
                    "Final Score: " + score + "/" + questionsAnswered + 
                    " (" + String.format("%.1f", scorePercentage) + "%)\n" +
                    "Coins Earned: " + String.format("%.0f", gameSession.getCoinsEarned()) + " Coins\n\n" +
                    "Great job! Your coins have been added to your wallet.";
                
                JOptionPane.showMessageDialog(this, message, 
                    "Quiz Complete", JOptionPane.INFORMATION_MESSAGE);
            }
            
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error ending game: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
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