CREATE DATABASE IF NOT EXISTS ecommerce_db;
USE ecommerce_db;

CREATE TABLE IF NOT EXISTS Orders (
    order_id INT PRIMARY KEY,
    customer_name VARCHAR(100),
    product_name VARCHAR(100),
    quantity INT,
    unit_price DOUBLE,
    status VARCHAR(30)
);

INSERT IGNORE INTO Orders (order_id, customer_name, product_name, quantity, unit_price, status) VALUES
(101, 'Aarav Sharma', 'Wireless Mouse', 4, 750.00, 'Pending'),
(102, 'Priya Verma', 'Mechanical Keyboard', 3, 2500.00, 'Pending'),
(103, 'Rohan Gupta', 'USB-C Hub', 1, 1200.00, 'Pending'),
(104, 'Sneha Singh', 'Laptop Stand', 5, 1100.00, 'Shipped'),
(105, 'Vikram Yadav', 'Bluetooth Speaker', 3, 3200.00, 'Shipped'),
(106, 'Ananya Mishra', 'Gaming Headset', 2, 2800.00, 'Shipped'),
(107, 'Karan Malhotra', '27-inch Monitor', 4, 15500.00, 'Delivered'),
(108, 'Neha Joshi', 'Webcam HD', 3, 1800.00, 'Delivered'),
(109, 'Aditya Tiwari', 'Phone Charger', 2, 650.00, 'Cancelled'),
(110, 'Pooja Agarwal', 'Power Bank', 1, 1450.00, 'Cancelled');
