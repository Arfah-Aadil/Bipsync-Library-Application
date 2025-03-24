
CREATE TABLE IF NOT EXISTS users (
                                     UserID BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) UNIQUE,
                                     role VARCHAR(50) NOT NULL,
                                     status VARCHAR(50) DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS Inventory (
                                         ItemID INT AUTO_INCREMENT PRIMARY KEY,
                                         Type VARCHAR(255) NOT NULL,
                                         Model VARCHAR(255) NOT NULL,
                                         Status VARCHAR(50) NOT NULL DEFAULT 'Available',
                                         Location VARCHAR(255) NOT NULL,
                                         Stock INT,
                                         OnLoan INT DEFAULT 0,
                                         Description VARCHAR(255),
                                         Company VARCHAR(50) NOT NULL,
                                         AcquisitionDate DATE NOT NULL,
                                         CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP);

CREATE TABLE IF NOT EXISTS Transactions (
                                            TransactionID INT AUTO_INCREMENT PRIMARY KEY,
                                            ItemID INT NOT NULL,
                                            UserID INT NOT NULL,
                                            CheckoutDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            DueDate DATE,
                                            CheckinDate DATE,
                                            Status VARCHAR(50) NOT NULL

);

