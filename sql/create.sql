-- Create a new database (optional if you already have one):
CREATE DATABASE IF NOT EXISTS piano_tuning;
USE piano_tuning;

-- Table for Customers
CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT ,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    contact_number VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255)
);

-- Table for Appointments
CREATE TABLE appointments (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    start_time DATETIME,
    duration INT,
    status VARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- Table for Invoices
CREATE TABLE invoices (
    invoice_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    total_amount DECIMAL(10, 2),
    due_date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- 1) Tuners table
CREATE TABLE IF NOT EXISTS tuners (
    tuner_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100)
);

-- 2) Payments table
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_id INT NOT NULL,
    payment_date DATETIME NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50),
    status VARCHAR(50),
    CONSTRAINT fk_payments_invoice_id
        FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id)
        ON DELETE CASCADE ON UPDATE CASCADE
);
