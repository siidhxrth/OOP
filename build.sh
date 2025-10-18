#!/bin/bash

echo "Building E-Commerce Application..."

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files
echo "Compiling Java files..."
javac -cp "lib/*" -d bin src/main/java/com/ecommerce/*.java src/main/java/com/ecommerce/**/*.java

if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo ""
    echo "To run the application:"
    echo "java -cp \"bin:lib/*\" com.ecommerce.ECommerceApplication"
else
    echo "Build failed! Please check the error messages above."
fi
