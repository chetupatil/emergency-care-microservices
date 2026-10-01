-- Database-per-service: one schema per microservice, created on first MySQL boot.
CREATE DATABASE IF NOT EXISTS user_db;
CREATE DATABASE IF NOT EXISTS contact_db;
CREATE DATABASE IF NOT EXISTS membership_db;
CREATE DATABASE IF NOT EXISTS emergency_db;
CREATE DATABASE IF NOT EXISTS facility_db;
CREATE DATABASE IF NOT EXISTS notification_db;
CREATE DATABASE IF NOT EXISTS location_db;

-- Dedicated app user (avoid every service connecting as root)
CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'app_password';
GRANT ALL PRIVILEGES ON user_db.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON contact_db.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON membership_db.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON emergency_db.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON facility_db.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON notification_db.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON location_db.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
