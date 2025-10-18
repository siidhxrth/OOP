package com.ecommerce.dao;

import com.ecommerce.DBConnection;
import com.ecommerce.models.GameSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for GameSession operations
 */
public class GameSessionDAO {
    
    /**
     * Create a new game session
     * @param gameSession GameSession object to create
     * @return GameSession object with generated ID if successful, null otherwise
     */
    public GameSession createGameSession(GameSession gameSession) {
        String sql = "INSERT INTO game_sessions (user_id, score, coins_earned, questions_answered) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, gameSession.getUserId());
            stmt.setInt(2, gameSession.getScore());
            stmt.setDouble(3, gameSession.getCoinsEarned());
            stmt.setInt(4, gameSession.getQuestionsAnswered());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        gameSession.setId(generatedKeys.getInt(1));
                        return gameSession;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating game session: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Get game sessions for a user
     * @param userId user ID
     * @return List of game sessions
     */
    public List<GameSession> getGameSessionsByUserId(int userId) {
        List<GameSession> gameSessions = new ArrayList<>();
        String sql = "SELECT * FROM game_sessions WHERE user_id = ? ORDER BY session_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    gameSessions.add(new GameSession(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getDouble("coins_earned"),
                        rs.getInt("questions_answered"),
                        rs.getTimestamp("session_date")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting game sessions by user ID: " + e.getMessage());
        }
        return gameSessions;
    }
    
    /**
     * Get recent game sessions for a user
     * @param userId user ID
     * @param limit maximum number of sessions to return
     * @return List of recent game sessions
     */
    public List<GameSession> getRecentGameSessions(int userId, int limit) {
        List<GameSession> gameSessions = new ArrayList<>();
        String sql = "SELECT * FROM game_sessions WHERE user_id = ? ORDER BY session_date DESC LIMIT ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    gameSessions.add(new GameSession(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getDouble("coins_earned"),
                        rs.getInt("questions_answered"),
                        rs.getTimestamp("session_date")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting recent game sessions: " + e.getMessage());
        }
        return gameSessions;
    }
    
    /**
     * Get best score for a user
     * @param userId user ID
     * @return best score
     */
    public int getBestScore(int userId) {
        String sql = "SELECT MAX(score) FROM game_sessions WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting best score: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get total coins earned by a user
     * @param userId user ID
     * @return total coins earned
     */
    public double getTotalCoinsEarned(int userId) {
        String sql = "SELECT SUM(coins_earned) FROM game_sessions WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting total coins earned: " + e.getMessage());
        }
        return 0.0;
    }
    
    /**
     * Get total games played by a user
     * @param userId user ID
     * @return total games played
     */
    public int getTotalGamesPlayed(int userId) {
        String sql = "SELECT COUNT(*) FROM game_sessions WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting total games played: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Get average score for a user
     * @param userId user ID
     * @return average score
     */
    public double getAverageScore(int userId) {
        String sql = "SELECT AVG(score) FROM game_sessions WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting average score: " + e.getMessage());
        }
        return 0.0;
    }
    
    /**
     * Get all game sessions (for admin purposes)
     * @return List of all game sessions
     */
    public List<GameSession> getAllGameSessions() {
        List<GameSession> gameSessions = new ArrayList<>();
        String sql = "SELECT * FROM game_sessions ORDER BY session_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                gameSessions.add(new GameSession(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getDouble("coins_earned"),
                    rs.getInt("questions_answered"),
                    rs.getTimestamp("session_date")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all game sessions: " + e.getMessage());
        }
        return gameSessions;
    }
}
