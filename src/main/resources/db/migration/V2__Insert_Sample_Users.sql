-- Insert sample users for application testing
-- Admin user
INSERT INTO users (username, email, first_name, last_name, active, created_at, updated_at)
VALUES ('admin', 'admin@example.com', 'Admin', 'User', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Regular user
INSERT INTO users (username, email, first_name, last_name, active, created_at, updated_at)
VALUES ('johndoe', 'john.doe@example.com', 'John', 'Doe', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Test user
INSERT INTO users (username, email, first_name, last_name, active, created_at, updated_at)
VALUES ('testuser', 'test@example.com', 'Test', 'User', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);