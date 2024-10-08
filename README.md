# Blog Server

A simple HTTP server for managing blog posts, built with Java.

> [!NOTE]  
> File Structure: [here](./src/Structure)

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Database Schema](#database-schema)

## Features

- User authentication using JWT
- Create new blog posts
- Retrieve all blog posts
- Retrieve a specific blog post by ID

## Prerequisites

- Java JDK 11 or higher
- MySQL database
- Install required Dependencies

## Installation

1. Clone the repository:

   ```
   git clone https://github.com/pantharshit007/BloggerJava
   ```

2. Navigate to the project directory:

   ```
   cd BloggerJava
   ```

3. Add all the required libraries either using Mavern or JAR files.

   > (I used SQL lib as a JAR file)

4. Set up the MySQL database:

   - Create a new database named `blog`
   - Run the SQL scripts in `db/Table.java` to set up the tables

5. Configure the database connection:
   - Update the database URL, username, and password

## Usage

1. Start the server:

   ```
   javac ./Main.java
   ```

2. The server will start running on `http://localhost:8000`

## API Endpoints

- `POST /signup`: Create a new user account
- `POST /login`: Authenticate and receive a JWT token
- `POST /newContent`: Create a new blog post (requires authentication)
- `GET /allBlogs`: Retrieve all blog posts
- `GET /blog?id={postId}`: Retrieve a specific blog post by ID

## Authentication

This server uses JWT (JSON Web Tokens) for authentication. To access protected endpoints:

1. Obtain a token by logging in via the `/login` endpoint
2. Include the token in the `Authorization` header of your requests:
   ```
   Authorization: Bearer your_jwt_token_here
   ```

## Database Schema

The project uses the following database schema:

```sql
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE posts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  published BOOLEAN DEFAULT false,
  authorId INT NOT NULL,
  FOREIGN KEY (authorId) REFERENCES users(id)
);
```
