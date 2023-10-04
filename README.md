# Online Shopping Application

# Description:
This Java-based Online Shopping Application provides a simple command-line interface for users to register, log in, and perform various shopping-related actions. The application utilizes a PostgreSQL database to store user information, shopping carts, and payment details.

Complie:
javac OnlineShopping.java
Run:
java -cp "path/to/postgresql-42.6.0.jar;." OnlineShopping

# Database Setup:
Create a PostgreSQL database with the following tables:
"shopping_app" for user information
"cart_data" for shopping carts and payment details

# User Management:
Register a new user with name, username, and phone number.
Login with username and phone number, and an OTP verification process.

# Shopping:
Browse and add items to the shopping cart.
View and process the shopping cart.

# Account Management:
View account details.
Delete the account permanently.

# Payment:
View payment details.

Database Connection(PostgreSQL):
Connection URL: jdbc:postgresql://localhost:5432/postgres
Username: your_username
Password: your_postgresql_password

# Note:
Make sure to have the PostgreSQL JDBC driver (postgresql-42.6.0.jar) in the classpath.
Database tables should be created as specified in the comments in the code.
