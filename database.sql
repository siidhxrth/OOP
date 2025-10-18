-- E-Commerce Application with Quiz Game Database Schema
-- MySQL Database Setup

CREATE DATABASE IF NOT EXISTS ecommerce_quiz;
USE ecommerce_quiz;

-- Users table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    wallet_balance DECIMAL(10,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    description TEXT,
    category VARCHAR(50),
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Cart table
CREATE TABLE cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_product (user_id, product_id)
);

-- Orders table
CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    coins_used DECIMAL(10,2) DEFAULT 0.00,
    final_amount DECIMAL(10,2) NOT NULL,
    status ENUM('pending', 'confirmed', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Order items table
CREATE TABLE order_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Wallet table (for transaction history)
CREATE TABLE wallet (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    balance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Transactions table
CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    type ENUM('earned', 'spent', 'refund') NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description VARCHAR(255),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Questions table for quiz game
CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option ENUM('A', 'B', 'C', 'D') NOT NULL,
    difficulty ENUM('easy', 'medium', 'hard') DEFAULT 'medium',
    category VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Game sessions table
CREATE TABLE game_sessions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    score INT NOT NULL DEFAULT 0,
    coins_earned DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    questions_answered INT NOT NULL DEFAULT 0,
    session_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample data

-- Sample users
INSERT INTO users (name, email, password, wallet_balance) VALUES
('John Doe', 'john@example.com', 'password123', 50.00),
('Jane Smith', 'jane@example.com', 'password123', 25.00),
('Admin User', 'admin@example.com', 'admin123', 100.00);

-- Sample products with modern dropshipping theme
INSERT INTO products (name, price, stock, description, category, image_url) VALUES
('MacBook Pro M1 Pro 14" 512GB', 1800.00, 10, 'Powerful laptop with M1 Pro chip, 512GB SSD, and stunning Liquid Retina XDR display', 'Electronics', 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400&h=300&fit=crop'),
('Monitor MSI 27" Modern MD271UL 4K', 350.00, 15, '27-inch 4K UHD monitor with IPS panel, USB-C connectivity, and ultra-slim bezels', 'Electronics', 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400&h=300&fit=crop'),
('MacBook Pro M1 2020 13" 512GB', 1200.00, 8, 'Compact 13-inch MacBook Pro with M1 chip, 512GB SSD, and all-day battery life', 'Electronics', 'https://images.unsplash.com/photo-1541807084-5c52b6b3adef?w=400&h=300&fit=crop'),
('MacBook Air M1 2020 13" 256GB', 999.00, 12, 'Lightweight MacBook Air with M1 chip, 256GB SSD, and silent fanless design', 'Electronics', 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400&h=300&fit=crop'),
('Apple 32" Pro Display XDR Retina 6K', 4999.00, 5, 'Professional 32-inch 6K Retina display with extreme dynamic range and reference modes', 'Electronics', 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400&h=300&fit=crop'),
('Wireless Bluetooth Earbuds Pro', 79.99, 50, 'Premium wireless earbuds with active noise cancellation and 30-hour battery life', 'Electronics', 'https://images.unsplash.com/photo-1606220945770-b5b6c2c55bf1?w=400&h=300&fit=crop'),
('Mechanical Gaming Keyboard RGB', 120.00, 20, 'RGB backlit mechanical keyboard with tactile switches and customizable lighting', 'Electronics', 'https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=400&h=300&fit=crop'),
('Ergonomic Office Chair Premium', 250.00, 7, 'Comfortable ergonomic office chair with lumbar support and adjustable height', 'Furniture', 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400&h=300&fit=crop'),
('Organic Whole Wheat Bread 500g', 3.50, 100, 'Freshly baked organic whole wheat bread, perfect for healthy breakfast', 'Food', 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop'),
('Fresh Organic Apples 1kg', 4.99, 75, 'Crisp and juicy organic apples, rich in vitamins and perfect for snacking', 'Grocery', 'https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=400&h=300&fit=crop'),
('Premium Coffee Beans 250g', 12.00, 40, 'Rich and aromatic Arabica coffee beans, medium roast for perfect morning brew', 'Food', 'https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=400&h=300&fit=crop'),
('Fresh Milk 1 Liter', 2.00, 60, 'Fresh full-fat milk, pasteurized and homogenized for daily consumption', 'Grocery', 'https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop');

-- Sample questions for quiz game
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option, difficulty, category) VALUES
('What is the capital of France?', 'London', 'Berlin', 'Paris', 'Madrid', 'C', 'easy', 'Geography'),
('Which planet is known as the Red Planet?', 'Venus', 'Mars', 'Jupiter', 'Saturn', 'B', 'easy', 'Science'),
('What is 15 + 27?', '40', '41', '42', '43', 'C', 'easy', 'Math'),
('Who painted the Mona Lisa?', 'Vincent van Gogh', 'Pablo Picasso', 'Leonardo da Vinci', 'Michelangelo', 'C', 'medium', 'Art'),
('What is the largest ocean on Earth?', 'Atlantic', 'Indian', 'Pacific', 'Arctic', 'C', 'medium', 'Geography'),
('Which programming language is known for its use in web development?', 'C++', 'Java', 'JavaScript', 'Python', 'C', 'medium', 'Technology'),
('What is the chemical symbol for gold?', 'Go', 'Gd', 'Au', 'Ag', 'C', 'medium', 'Science'),
('In which year did World War II end?', '1944', '1945', '1946', '1947', 'B', 'medium', 'History'),
('What is the square root of 144?', '11', '12', '13', '14', 'B', 'easy', 'Math'),
('Which company developed the iPhone?', 'Samsung', 'Google', 'Apple', 'Microsoft', 'C', 'easy', 'Technology'),
('What is the hardest natural substance on Earth?', 'Gold', 'Iron', 'Diamond', 'Platinum', 'C', 'medium', 'Science'),
('Who wrote "Romeo and Juliet"?', 'Charles Dickens', 'William Shakespeare', 'Mark Twain', 'Jane Austen', 'B', 'medium', 'Literature'),
('What is the speed of light in vacuum?', '299,792,458 m/s', '300,000,000 m/s', '299,000,000 m/s', '301,000,000 m/s', 'A', 'hard', 'Science'),
('Which element has the atomic number 1?', 'Helium', 'Hydrogen', 'Lithium', 'Carbon', 'B', 'medium', 'Science'),
('What is the largest mammal in the world?', 'African Elephant', 'Blue Whale', 'Giraffe', 'Polar Bear', 'B', 'easy', 'Biology');

-- Sample transactions
INSERT INTO transactions (user_id, type, amount, description) VALUES
(1, 'earned', 10.00, 'Quiz game reward'),
(1, 'earned', 5.00, 'Welcome bonus'),
(2, 'earned', 15.00, 'Quiz game reward'),
(3, 'earned', 20.00, 'Quiz game reward'),
(3, 'earned', 10.00, 'Welcome bonus');

-- Sample game sessions
INSERT INTO game_sessions (user_id, score, coins_earned, questions_answered) VALUES
(1, 8, 10.00, 10),
(2, 6, 15.00, 8),
(3, 9, 20.00, 10);
