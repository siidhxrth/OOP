package com.ecommerce.models;

import java.sql.Timestamp;

/**
 * GameSession model class representing a quiz game session
 */
public class GameSession {
    private int id;
    private int userId;
    private int score;
    private double coinsEarned;
    private int questionsAnswered;
    private Timestamp sessionDate;
    
    // Constructors
    public GameSession() {}
    
    public GameSession(int userId, int score, double coinsEarned, int questionsAnswered) {
        this.userId = userId;
        this.score = score;
        this.coinsEarned = coinsEarned;
        this.questionsAnswered = questionsAnswered;
    }
    
    public GameSession(int id, int userId, int score, double coinsEarned, int questionsAnswered, Timestamp sessionDate) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.coinsEarned = coinsEarned;
        this.questionsAnswered = questionsAnswered;
        this.sessionDate = sessionDate;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public double getCoinsEarned() {
        return coinsEarned;
    }
    
    public void setCoinsEarned(double coinsEarned) {
        this.coinsEarned = coinsEarned;
    }
    
    public int getQuestionsAnswered() {
        return questionsAnswered;
    }
    
    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }
    
    public Timestamp getSessionDate() {
        return sessionDate;
    }
    
    public void setSessionDate(Timestamp sessionDate) {
        this.sessionDate = sessionDate;
    }
    
    /**
     * Calculate the percentage score
     * @return percentage score
     */
    public double getPercentageScore() {
        if (questionsAnswered == 0) return 0.0;
        return (double) score / questionsAnswered * 100;
    }
    
    @Override
    public String toString() {
        return "GameSession{" +
                "id=" + id +
                ", userId=" + userId +
                ", score=" + score +
                ", coinsEarned=" + coinsEarned +
                ", questionsAnswered=" + questionsAnswered +
                ", sessionDate=" + sessionDate +
                '}';
    }
}
