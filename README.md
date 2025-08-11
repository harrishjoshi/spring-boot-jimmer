# Spring Boot Jimmer Demo

A demo Spring Boot application showcasing Jimmer ORM with user management functionality.

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.4**
- **Jimmer ORM 0.9.105** - Modern ORM framework
- **PostgreSQL** (production) / **H2** (testing)
- **Flyway** - Database migrations
- **MapStruct** - Object mapping

## Features

- User CRUD operations
- RESTful API endpoints
- Database migrations with Flyway
- Package-by-feature architecture (Spring Modulith)

## Quick Start

1. **Prerequisites**: Java 21, Docker (for PostgreSQL)

2. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

3. **API Endpoints**:
    - `GET /api/users` - List all users
    - `POST /api/users` - Create user
    - `GET /api/users/{id}` - Get user by ID
    - `PUT /api/users/{id}` - Update user
    - `DELETE /api/users/{id}` - Delete user