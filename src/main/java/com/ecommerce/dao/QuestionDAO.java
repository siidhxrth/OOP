package com.ecommerce.dao;

import com.ecommerce.DBConnection;
import com.ecommerce.models.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Data Access Object for Question operations
 */
public class QuestionDAO {
    
    /**
     * Get all questions
     * @return List of all questions
     */
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                questions.add(new Question(
                    rs.getInt("id"),
                    rs.getString("question_text"),
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d"),
                    rs.getString("correct_option"),
                    rs.getString("difficulty"),
                    rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all questions: " + e.getMessage());
        }
        return questions;
    }
    
    /**
     * Get random questions for quiz game
     * @param count number of questions to get
     * @return List of random questions
     */
    public List<Question> getRandomQuestions(int count) {
        List<Question> allQuestions = getAllQuestions();
        if (allQuestions.size() <= count) {
            return allQuestions;
        }
        
        Collections.shuffle(allQuestions);
        return allQuestions.subList(0, count);
    }
    
    /**
     * Get questions by difficulty
     * @param difficulty difficulty level (easy, medium, hard)
     * @return List of questions with specified difficulty
     */
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE difficulty = ? ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, difficulty);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option"),
                        rs.getString("difficulty"),
                        rs.getString("category")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting questions by difficulty: " + e.getMessage());
        }
        return questions;
    }
    
    /**
     * Get questions by category
     * @param category question category
     * @return List of questions in the category
     */
    public List<Question> getQuestionsByCategory(String category) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE category = ? ORDER BY id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option"),
                        rs.getString("difficulty"),
                        rs.getString("category")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting questions by category: " + e.getMessage());
        }
        return questions;
    }
    
    /**
     * Get question by ID
     * @param questionId question ID
     * @return Question object if found, null otherwise
     */
    public Question getQuestionById(int questionId) {
        String sql = "SELECT * FROM questions WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, questionId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Question(
                        rs.getInt("id"),
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option"),
                        rs.getString("difficulty"),
                        rs.getString("category")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting question by ID: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get all categories
     * @return List of unique categories
     */
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM questions WHERE category IS NOT NULL ORDER BY category";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting categories: " + e.getMessage());
        }
        return categories;
    }
    
    /**
     * Get question count
     * @return total number of questions
     */
    public int getQuestionCount() {
        String sql = "SELECT COUNT(*) FROM questions";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting question count: " + e.getMessage());
        }
        return 0;
    }
}
