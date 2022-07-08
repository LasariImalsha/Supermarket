DROP DATABASE IF EXISTS CompanySystem;
CREATE DATABASE IF NOT EXISTS CompanySystem;
SHOW DATABASES;
USE CompanySystem;

#------------------------------------------------------------

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
	custId VARCHAR(10),
	custTitle VARCHAR(10),
	custName VARCHAR(30) NOT NULL DEFAULT 'Unknown',
	custAddress VARCHAR(30),
	city Varchar(20),
	province VARCHAR(20),
	postalCode VARCHAR(10),
	CONSTRAINT PRIMARY KEY (custId)
	);
SHOW TABLES ;
DESCRIBE Customer;

#------------------------------------------------------------

DROP TABLE IF EXISTS Orders;
CREATE TABLE IF NOT EXISTS Orders(
	orderId VARCHAR(10),
	orderDate DATE,
	custId VARCHAR(10),
    time VARCHAR(20),
    cost DOUBLE,
    CONSTRAINT PRIMARY KEY (orderId),
	CONSTRAINT FOREIGN KEY (custId) REFERENCES Customer(custId) ON DELETE CASCADE ON UPDATE CASCADE
);

#-----------------------------------------------------------

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
	itemCode VARCHAR(10),
	description VARCHAR(50) NOT NULL DEFAULT 'Empty',
	packSize VARCHAR(20),
	unitPrice DOUBLE NOT NULL DEFAULT 0.00,
	qtyOnHand INT NOT NULL DEFAULT 0,
	discountPercentage DOUBLE DEFAULT 0.00,
	CONSTRAINT PRIMARY KEY (itemCode)
	);
SHOW TABLES ;
DESCRIBE Item;

#---------------------------------------------------------

DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
	orderId VARCHAR(10),
	itemCode VARCHAR(10),
	unitPrice DOUBLE,
	orderQTY INT NOT NULL DEFAULT 0,
	discount DOUBLE DEFAULT 0.00,
	price DOUBLE,
	CONSTRAINT PRIMARY KEY (itemCode,orderId),
	CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(itemCode) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES Orders(orderId) ON DELETE CASCADE ON UPDATE CASCADE
	);
SHOW TABLES ;
DESCRIBE orderDetail;