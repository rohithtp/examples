#!/bin/bash

# Function to display the main menu
show_menu() {
    clear
    echo "=========================="
    echo "   Maven Commands Menu    "
    echo "=========================="
    echo "1. Compile Project"
    echo "2. Run Tests"
    echo "3. Package Project"
    echo "4. Install Project"
    echo "5. Dependency Check"
    echo "6. Clean Project"
    echo "7. Exit"
    echo "=========================="
    read -p "Enter your choice [1-7]: " choice
}

# Function to handle menu choices
handle_choice() {
    case $choice in
        1)
            mvn compile
            ;;
        2)
            mvn test
            ;;
        3)
            mvn package
            ;;
        4)
            mvn install
            ;;
        5)
            mvn dependency-check:check
            ;;
        6)
            mvn clean
            ;;
        7)
            echo "Exiting..."
            exit 0
            ;;
        *)
            echo "Invalid choice. Please enter a number between 1 and 7."
            read -p "Press [Enter] to continue..."
            ;;
    esac
}

# Main loop
while true; do
    show_menu
    handle_choice
done
