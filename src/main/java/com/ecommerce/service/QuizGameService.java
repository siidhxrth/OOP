package com.ecommerce.service;

import com.ecommerce.dao.QuestionDAO;
import com.ecommerce.dao.GameSessionDAO;
import com.ecommerce.models.Question;
import com.ecommerce.models.GameSession;

import java.util.List;

/**
 * Service class for Quiz Game operations
 */
public class QuizGameService {
    private QuestionDAO questionDAO;
    private GameSessionDAO gameSessionDAO;
    private WalletService walletService;
    
    public QuizGameService() {
        this.questionDAO = new QuestionDAO();
        this.gameSessionDAO = new GameSessionDAO();
        this.walletService = new WalletService();
    }
    
    /**
     * Start a new quiz game
     * @param userId user ID
     * @param questionCount number of questions for the game
     * @return List of random questions
     */
    public List<Question> startGame(int userId, int questionCount) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (questionCount <= 0) {
            throw new IllegalArgumentException("Question count must be positive");
        }
        
        List<Question> questions = questionDAO.getRandomQuestions(questionCount);
        if (questions.isEmpty()) {
            throw new RuntimeException("No questions available for the quiz");
        }
        
        return questions;
    }
    
    /**
     * Submit an answer for a question
     * @param question Question object
     * @param answer user's answer (A, B, C, or D)
     * @return true if correct, false otherwise
     */
    public boolean submitAnswer(Question question, String answer) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        if (answer == null || answer.trim().isEmpty()) {
            throw new IllegalArgumentException("Answer cannot be empty");
        }
        
        return question.isCorrectAnswer(answer.trim().toUpperCase());
    }
    
    /**
     * Calculate score based on correct answers
     * @param correctAnswers number of correct answers
     * @param totalQuestions total number of questions
     * @return score percentage
     */
    public double calculateScore(int correctAnswers, int totalQuestions) {
        if (totalQuestions <= 0) {
            throw new IllegalArgumentException("Total questions must be positive");
        }
        if (correctAnswers < 0) {
            throw new IllegalArgumentException("Correct answers cannot be negative");
        }
        if (correctAnswers > totalQuestions) {
            throw new IllegalArgumentException("Correct answers cannot exceed total questions");
        }
        
        return (double) correctAnswers / totalQuestions * 100;
    }
    
    /**
     * Calculate coins reward based on score
     * @param score score percentage
     * @param totalQuestions total number of questions
     * @return coins to reward
     */
    public double calculateReward(double score, int totalQuestions) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        if (totalQuestions <= 0) {
            throw new IllegalArgumentException("Total questions must be positive");
        }
        
        // Base reward calculation
        double baseReward = 1.0; // Base 1 coin per question
        double scoreMultiplier = score / 100.0; // Score as multiplier
        double difficultyBonus = Math.min(totalQuestions * 0.1, 2.0); // Bonus for more questions
        
        return Math.round((baseReward * totalQuestions * scoreMultiplier + difficultyBonus) * 100.0) / 100.0;
    }
    
    /**
     * End a quiz game and record the session
     * @param userId user ID
     * @param score final score
     * @param questionsAnswered number of questions answered
     * @return GameSession object if successful, null otherwise
     */
    public GameSession endGame(int userId, int score, int questionsAnswered) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        if (questionsAnswered <= 0) {
            throw new IllegalArgumentException("Questions answered must be positive");
        }
        
        // Calculate coins earned
        double scorePercentage = calculateScore(score, questionsAnswered);
        double coinsEarned = calculateReward(scorePercentage, questionsAnswered);
        
        // Create game session
        GameSession gameSession = new GameSession(userId, score, coinsEarned, questionsAnswered);
        GameSession savedSession = gameSessionDAO.createGameSession(gameSession);
        
        if (savedSession != null && coinsEarned > 0) {
            // Add coins to wallet
            walletService.addCoins(userId, coinsEarned, 
                "Quiz game reward - Score: " + score + "/" + questionsAnswered + 
                " (" + String.format("%.1f", scorePercentage) + "%)");
        }
        
        return savedSession;
    }
    
    /**
     * Get game statistics for a user
     * @param userId user ID
     * @return formatted game statistics
     */
    public String getGameStatistics(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        int totalGames = gameSessionDAO.getTotalGamesPlayed(userId);
        int bestScore = gameSessionDAO.getBestScore(userId);
        double averageScore = gameSessionDAO.getAverageScore(userId);
        double totalCoinsEarned = gameSessionDAO.getTotalCoinsEarned(userId);
        
        StringBuilder stats = new StringBuilder();
        stats.append("Game Statistics:\n");
        stats.append("Total Games Played: ").append(totalGames).append("\n");
        stats.append("Best Score: ").append(bestScore).append("\n");
        stats.append("Average Score: ").append(String.format("%.1f", averageScore)).append("\n");
        stats.append("Total Coins Earned: $").append(String.format("%.2f", totalCoinsEarned));
        
        return stats.toString();
    }
    
    /**
     * Get recent game sessions for a user
     * @param userId user ID
     * @param limit maximum number of sessions to return
     * @return List of recent game sessions
     */
    public List<GameSession> getRecentGameSessions(int userId, int limit) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be positive");
        }
        
        return gameSessionDAO.getRecentGameSessions(userId, limit);
    }
    
    /**
     * Get all game sessions for a user
     * @param userId user ID
     * @return List of all game sessions
     */
    public List<GameSession> getGameSessionsByUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        
        return gameSessionDAO.getGameSessionsByUserId(userId);
    }
    
    /**
     * Get questions by difficulty
     * @param difficulty difficulty level
     * @return List of questions with specified difficulty
     */
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        if (difficulty == null || difficulty.trim().isEmpty()) {
            throw new IllegalArgumentException("Difficulty cannot be empty");
        }
        
        String[] validDifficulties = {"easy", "medium", "hard"};
        boolean validDifficulty = false;
        for (String valid : validDifficulties) {
            if (valid.equals(difficulty.toLowerCase())) {
                validDifficulty = true;
                break;
            }
        }
        
        if (!validDifficulty) {
            throw new IllegalArgumentException("Invalid difficulty level");
        }
        
        return questionDAO.getQuestionsByDifficulty(difficulty.toLowerCase());
    }
    
    /**
     * Get questions by category
     * @param category question category
     * @return List of questions in the category
     */
    public List<Question> getQuestionsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        
        return questionDAO.getQuestionsByCategory(category.trim());
    }
    
    /**
     * Get all available categories
     * @return List of all categories
     */
    public List<String> getAllCategories() {
        return questionDAO.getAllCategories();
    }
    
    /**
     * Get total number of questions available
     * @return total question count
     */
    public int getTotalQuestionCount() {
        return questionDAO.getQuestionCount();
    }
}
