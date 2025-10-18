# E-Commerce Application with Integrated Quiz Game

A comprehensive desktop-based E-Commerce Application built with Java Swing, JDBC, and MySQL. Users can browse products, manage their cart, place orders, and play a quiz game to earn coins that can be used as discounts during checkout.

## 🚀 Features

### Core E-Commerce Features
- **User Management**: Registration, login, profile management
- **Product Catalog**: Browse, search, and filter products by category
- **Shopping Cart**: Add, remove, and update quantities
- **Order Management**: Place orders, view order history, cancel orders
- **Wallet System**: Earn and spend coins, view transaction history

### Quiz Game Integration
- **Interactive Quiz**: Answer multiple-choice questions to earn coins
- **Dynamic Scoring**: Coins earned based on performance
- **Game Statistics**: Track best scores, total games played, coins earned
- **Wallet Integration**: Coins automatically added to user wallet

## 🛠 Technology Stack

- **Frontend**: Java Swing (UI components)
- **Backend**: JDBC (Database connectivity)
- **Database**: MySQL
- **Architecture**: MVC (Model-View-Controller)

## 📋 Prerequisites

1. **Java Development Kit (JDK) 8 or higher**
2. **MySQL Server 5.7 or higher**
3. **MySQL JDBC Driver** (mysql-connector-java-8.0.33.jar or similar)

## 🗄 Database Setup

1. **Start MySQL Server**
2. **Create Database**:
   ```sql
   CREATE DATABASE ecommerce_quiz;
   ```
3. **Run the SQL Script**:
   ```bash
   mysql -u your_username -p ecommerce_quiz < database.sql
   ```

## ⚙ Configuration

1. **Update Database Connection**:
   - Open `src/main/java/com/ecommerce/DBConnection.java`
   - Update the following variables:
     ```java
     private static final String DB_USERNAME = "your_mysql_username";
     private static final String DB_PASSWORD = "your_mysql_password";
     ```

2. **Add MySQL JDBC Driver**:
   - Download MySQL JDBC driver
   - Add it to your project's classpath
   - Or include it in your IDE's library path

## 🏃‍♂️ Running the Application

### Method 1: Using IDE
1. Import the project into your IDE (Eclipse, IntelliJ IDEA, etc.)
2. Add MySQL JDBC driver to classpath
3. Run `ECommerceApplication.java`

### Method 2: Command Line
```bash
# Compile the project
javac -cp "path/to/mysql-connector-java.jar" -d bin src/main/java/com/ecommerce/*.java src/main/java/com/ecommerce/**/*.java

# Run the application
java -cp "bin:path/to/mysql-connector-java.jar" com.ecommerce.ECommerceApplication
```

## 📱 Application Flow

### 1. User Registration/Login
- New users can register with name, email, and password
- Existing users can login with email and password
- Welcome bonus of 10 coins for new users

### 2. Main Dashboard
- View wallet balance
- Access all application features
- Navigate between different modules

### 3. Product Browsing
- Browse all available products
- Search products by name or description
- Filter by category
- Add products to cart with desired quantity

### 4. Shopping Cart
- View cart items with details
- Update quantities or remove items
- Apply wallet coins as discount
- Proceed to checkout

### 5. Order Management
- Place orders with coin discounts
- View order history and status
- Cancel pending/confirmed orders
- Track order items and amounts

### 6. Quiz Game
- Play 10-question quiz games
- Earn coins based on performance
- View game statistics and history
- Coins automatically added to wallet

### 7. Wallet Management
- View current balance
- See transaction history
- Manually add coins (for testing)
- Track earnings and spending

## 🎮 Quiz Game Rules

- **Questions**: 10 random questions per game
- **Scoring**: 1 point per correct answer
- **Coin Calculation**: 
  - Base: 1 coin per question
  - Multiplier: Based on percentage score
  - Bonus: Extra coins for completing more questions
- **Example**: 8/10 correct = 80% = 8 coins + bonus

## 🗂 Project Structure

```
src/main/java/com/ecommerce/
├── ECommerceApplication.java          # Main application launcher
├── DBConnection.java                  # Database connection utility
├── models/                           # Data models
│   ├── User.java
│   ├── Product.java
│   ├── CartItem.java
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Transaction.java
│   ├── Question.java
│   └── GameSession.java
├── dao/                              # Data Access Objects
│   ├── UserDAO.java
│   ├── ProductDAO.java
│   ├── CartDAO.java
│   ├── OrderDAO.java
│   ├── TransactionDAO.java
│   ├── QuestionDAO.java
│   └── GameSessionDAO.java
├── service/                          # Business logic services
│   ├── UserService.java
│   ├── ProductService.java
│   ├── CartService.java
│   ├── OrderService.java
│   ├── WalletService.java
│   ├── TransactionService.java
│   └── QuizGameService.java
└── ui/                               # User interface components
    ├── LoginFrame.java
    ├── MainFrame.java
    ├── ProductsFrame.java
    ├── CartFrame.java
    ├── WalletFrame.java
    ├── OrdersFrame.java
    ├── QuizGameFrame.java
    └── ProfileFrame.java
```

## 🎯 Key Features Implemented

### ✅ User Module
- [x] User registration and login
- [x] Profile management
- [x] Wallet balance tracking

### ✅ Product Module
- [x] Product catalog display
- [x] Search and filter functionality
- [x] Stock management

### ✅ Cart Module
- [x] Add/remove products
- [x] Quantity management
- [x] Total calculation

### ✅ Order Module
- [x] Order placement
- [x] Status tracking
- [x] Order history
- [x] Coin discount application

### ✅ Wallet Module
- [x] Balance management
- [x] Transaction recording
- [x] Coin earning and spending

### ✅ Quiz Game Module
- [x] Question display
- [x] Answer submission
- [x] Score calculation
- [x] Coin reward system
- [x] Game statistics

### ✅ Transaction Module
- [x] Transaction history
- [x] Earning/spending tracking
- [x] Transaction summaries

## 🔧 Troubleshooting

### Database Connection Issues
1. Ensure MySQL server is running
2. Verify database credentials in `DBConnection.java`
3. Check if `ecommerce_quiz` database exists
4. Confirm MySQL JDBC driver is in classpath

### Application Startup Issues
1. Check Java version (JDK 8+ required)
2. Verify all dependencies are available
3. Check console for error messages
4. Ensure proper file permissions

### UI Issues
1. Try different look and feel settings
2. Check screen resolution compatibility
3. Verify Java Swing components are properly initialized

## 📝 Sample Data

The application comes with sample data including:
- 3 sample users (including admin)
- 10 sample products across different categories
- 15 quiz questions with varying difficulty levels
- Sample transactions and game sessions

## 🎨 UI Features

- **Modern Design**: Clean and intuitive interface
- **Responsive Layout**: Proper component sizing and positioning
- **User Feedback**: Success/error messages for all operations
- **Navigation**: Easy switching between different modules
- **Data Validation**: Input validation and error handling

## 🔒 Security Features

- **Password Validation**: Minimum length requirements
- **Input Sanitization**: Protection against SQL injection
- **Session Management**: User session tracking
- **Data Validation**: Server-side validation for all inputs

## 🚀 Future Enhancements

- [ ] Admin panel for product management
- [ ] Email notifications for orders
- [ ] Product reviews and ratings
- [ ] Advanced search filters
- [ ] Order tracking with shipping updates
- [ ] Multiple payment methods
- [ ] Wishlist functionality
- [ ] Social login integration

## 📞 Support

For issues or questions:
1. Check the troubleshooting section
2. Verify database setup
3. Review error messages in console
4. Ensure all prerequisites are met

---

**Happy Shopping and Gaming! 🛍️🎮**
