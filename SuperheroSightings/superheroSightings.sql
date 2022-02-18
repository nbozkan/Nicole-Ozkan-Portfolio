DROP DATABASE IF EXISTS superheroSightings;
CREATE DATABASE superheroSightings;

USE superheroSightings;

CREATE TABLE address(
	addressId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    address1 VARCHAR(50) NOT NULL,
    address2 VARCHAR(50) NULL,
    city VARCHAR(50) NOT NULL,
    state CHAR(2) NOT NULL,
    zipCode CHAR(5) NOT NULL,
    zipExtension CHAR(4) NULL
);

CREATE TABLE location(
	locId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    locName VARCHAR(150) NOT NULL,
    locDescription VARCHAR(150) NOT NULL,
    latitude DECIMAL(8,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    addressId INT NOT NULL,
    FOREIGN KEY (addressId) REFERENCES address(addressId)
);

CREATE TABLE contact(
	contactId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    contactName VARCHAR(50) NOT NULL,
    phoneNumber CHAR(10) NOT NULL,
    emailAddress VARCHAR(50) NOT NULL
);

CREATE TABLE organization(
	orgId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    orgName VARCHAR(150) NOT NULL,
    orgDescription VARCHAR(150) NOT NULL,
    addressId INT NOT NULL,
    contactId INT NOT NULL,
    FOREIGN KEY (addressId) REFERENCES address(addressId),
    FOREIGN KEY (contactId) REFERENCES contact(contactId)
);

CREATE TABLE power(
	powerId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    powerName VARCHAR(50) NOT NULL,
    powerDescription VARCHAR(150) NOT NULL
);

CREATE TABLE super(
	superId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    superName VARCHAR(50) NOT NULL,
    superDescription VARCHAR(150) NOT NULL,
    hero BIT NOT NULL
);

CREATE TABLE super_power(
	superId INT NOT NULL,
    powerId INT NOT NULL,
    PRIMARY KEY (superId, powerId),
    FOREIGN KEY (superId) REFERENCES super(superId),
    FOREIGN KEY (powerId) REFERENCES power(powerId)
);

CREATE TABLE super_organization(
	orgId INT NOT NULL,
    superId INT NOT NULL,
    PRIMARY KEY (orgId, superId),
    FOREIGN KEY (orgId) REFERENCES organization(orgId),
    FOREIGN KEY (superId) REFERENCES super(superId)
);

CREATE TABLE sighting(
	sightingId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    date DATE NOT NULL,
    locId INT NOT NULL,
    superId INT NOT NULL,
    FOREIGN KEY (locId) REFERENCES location(locId),
    FOREIGN KEY (superId) REFERENCES super(superId)
);