UPDATE users SET password = '{noop}admin' WHERE username = 'admin';
UPDATE users SET password = '{noop}user' WHERE username = 'user';

-- ALTER TABLE Transactions MODIFY COLUMN CheckoutDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
-- ALTER TABLE Transactions MODIFY COLUMN DueDate DATE DEFAULT '2024-12-31';
-- ALTER TABLE Transactions MODIFY COLUMN id INT NOT NULL DEFAULT 1;

DELETE FROM users;
INSERT INTO users (USERID, USERNAME, PASSWORD, ROLE)
VALUES(21,'admin2', '{noop}admin2', 'ADMIN');
INSERT INTO Inventory (Type, Model, Status, Stock, Location, Company, AcquisitionDate)
VALUES
    ('Laptop', 'Dell XPS 13', 'Available', 10, 'Warehouse 1', 'Dell', '2023-01-15'),
    ('Laptop', 'MacBook Pro 14', 'Available', 5, 'Warehouse 2', 'Apple', '2023-02-20'),
    ('Smartphone', 'iPhone 14', 'Available', 20, 'Warehouse 1', 'Apple', '2023-03-10'),
    ('Smartphone', 'Samsung Galaxy S23', 'Available', 15, 'Warehouse 3', 'Samsung', '2023-03-15'),
    ('Tablet', 'iPad Pro', 'Available', 12, 'Warehouse 2', 'Apple', '2023-04-05'),
    ('Laptop', 'HP Spectre x360', 'Available', 8, 'Warehouse 1', 'HP', '2023-05-12'),
    ('Desktop', 'Dell Optiplex 7090', 'Available', 7, 'Warehouse 3', 'Dell', '2023-06-18'),
    ('Desktop', 'iMac 24-inch', 'Available', 4, 'Warehouse 2', 'Apple', '2023-07-25');

INSERT INTO transactions (ItemID, UserID, CheckoutDate, DueDate, CheckinDate, Status)
VALUES
    (1, 1, '2023-08-01 10:00:00', '2023-08-15', '2023-08-14', 'Pending'),
    (2, 2, '2023-08-05 14:30:00', '2023-08-20', NULL, 'Pending'),
    (3, 1, '2023-08-10 09:15:00', '2023-08-25', '2023-08-24', 'Returned'),
    (4, 3, '2023-08-12 11:45:00', '2023-08-27', NULL, 'Checked Out'),
    (5, 2, '2023-08-15 16:00:00', '2024-08-22', NULL, 'Checked Out'),
    (6, 4, '2023-08-18 08:00:00', '2024-08-22', '2023-08-31', 'Returned'),
    (7, 3, '2023-08-20 13:30:00', '2024-08-22', NULL, 'Checked Out'),
    (8, 4, '2023-08-22 15:20:00', '2023-09-07', NULL, 'Checked Out');
