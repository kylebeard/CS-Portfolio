.open waterdata.db
ATTACH DATABASE 'waterdata.db' as 'WATER';
CREATE TABLE Account (
accountNum id INTEGER UNSIGNED PRIMARY KEY,
accountName VARCHAR(96),
street TEXT,
state CHAR(2),
zip CHAR(5),
current NCHAR(2) NOT NULL
);
CREATE TABLE Invoice (
invoiceNum id INTEGER UNSIGNED,
accountNum id INTEGER UNSIGNED,
waterCharge DECIMAL(6, 2),
sewerCharge DECIMAL(6, 2),
sanitationCharge DECIMAL(6, 2),
stormCharge DECIMAL(6, 2),
streetLight DECIMAL(6, 2),
franchFee DECIMAL(6, 2),
adjustments DECIMAL(6, 2),
totalCharges DECIMAL(6, 2),
dueDate DATE NOT NULL,
serviceFrom DATE NOT NULL,
serviceTo DATE NOT NULL,
billingUnits INTEGER UNSIGNED,
PRIMARY KEY (invoiceNum, accountNum),
FOREIGN KEY (accountNum) REFERENCES Account(accountNum)
);
INSERT INTO Account (accountNum, accountName, street, state, zip, current)
VALUES
('1570793', 'BUSINESS', '1840 S 1300 East', 'UT', '84105', 'Y'),
('1570801', 'BUSINESS', '1840 S 1300 East', 'UT', '84105', 'Y'),
('972139','GIOVALE LIBRARY', '', 'UT', '84105', 'Y'),
('802322', 'Pottery', '1852 S 1300 E', 'UT', '84015', 'Y');
INSERT INTO Invoice(invoiceNum, accountNum, watercharge, sewerCharge,
sanitationCharge, stormCharge, streetLight, franchFee, adjustments,
totalCharges, dueDate, serviceFrom, serviceTo, billingUnits) VALUES
('10555055611','1570793','5.72','0.0','0.0','0.0','0.0','0.34','0.0','6.06','2012-09-13','2012-07-13','2012-08-10', '0'),
('10555055611','1570801','720.79','38.42','0.0','0.0','0.0','45.55','0.0','804.76','2012-09-13','2012-07-13','2012-08-10', '368'),
('10555055611', '972139', '1295.11', '157.66', '0.0', '0.0', '0.0','87.17', '0.0', '1539.94', '2017-09-07', '2017-07-15', '2017-08-14', '557'),
('10555055611', '802322', '106.61', '0.0', '22.08', '0.0', '0.0', '6.40','0.0', '135.09', '2017-08-15', '2017-06-16', '2017-07-17', '55');

