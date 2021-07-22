-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 04, 2020 at 06:28 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_projectbad`
--

-- --------------------------------------------------------

--
-- Table structure for table `ariplanes`
--

CREATE TABLE `ariplanes` (
  `AirplaneID` varchar(50) NOT NULL,
  `AirplaneName` varchar(70) NOT NULL,
  `AirplaneCapacity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ariplanes`
--

INSERT INTO `ariplanes` (`AirplaneID`, `AirplaneName`, `AirplaneCapacity`) VALUES
('plane1', 'plane1', 1),
('plane2', 'plane2', 2);

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `FlightID` varchar(50) NOT NULL,
  `UserID` varchar(50) NOT NULL,
  `BookingQty` int(11) NOT NULL,
  `AdditionalDescription` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`FlightID`, `UserID`, `BookingQty`, `AdditionalDescription`) VALUES
('flight2', 'user1', 2, ''),
('flight1', 'US071', 3, 'Pls work');

-- --------------------------------------------------------

--
-- Table structure for table `cities`
--

CREATE TABLE `cities` (
  `CityID` varchar(50) NOT NULL,
  `CityName` varchar(100) NOT NULL,
  `CityCountry` varchar(100) NOT NULL,
  `CityNote` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cities`
--

INSERT INTO `cities` (`CityID`, `CityName`, `CityCountry`, `CityNote`) VALUES
('Tes1', 'Tes1', 'Tes1', 'Tes1'),
('Tes2', 'Tes2', 'Tes2', 'Tes2');

-- --------------------------------------------------------

--
-- Table structure for table `flights`
--

CREATE TABLE `flights` (
  `FlightID` varchar(50) NOT NULL,
  `AirplaneID` varchar(50) NOT NULL,
  `DepartureCityID` varchar(70) NOT NULL,
  `DestinationCityID` varchar(70) NOT NULL,
  `FlightDate` date NOT NULL,
  `FlightDuration` int(11) NOT NULL,
  `FlightPrice` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `flights`
--

INSERT INTO `flights` (`FlightID`, `AirplaneID`, `DepartureCityID`, `DestinationCityID`, `FlightDate`, `FlightDuration`, `FlightPrice`) VALUES
('delete', 'plane2', 'Tes2', 'Tes2', '2020-01-02', 15, 15),
('dummy', 'plane1', 'Tes1', 'Tes2', '2020-01-01', 11, 11),
('dummy1', 'plane2', 'Tes1', 'Tes1', '2020-01-02', 21, 21),
('dummy2', 'plane1', 'Tes2', 'Tes1', '2020-01-17', 200, 200),
('flight1', 'plane1', 'Tes1', 'Tes2', '2000-01-31', 10, 10),
('flight2', 'plane2', 'Tes2', 'Tes1', '2000-02-12', 20, 20);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `UserID` varchar(70) NOT NULL,
  `UserName` varchar(70) NOT NULL,
  `UserDOB` date NOT NULL,
  `UserPhone` varchar(50) NOT NULL,
  `UserGender` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`UserID`, `UserName`, `UserDOB`, `UserPhone`, `UserGender`) VALUES
('US071', 'Tryout', '1990-01-01', '1234', 'Male'),
('user1', 'user1', '2000-12-31', 'user1', 'user1'),
('user2', 'user2', '2000-12-22', 'user2', 'user2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ariplanes`
--
ALTER TABLE `ariplanes`
  ADD PRIMARY KEY (`AirplaneID`);

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD KEY `FK_FlightID` (`FlightID`),
  ADD KEY `FK_UserID` (`UserID`);

--
-- Indexes for table `cities`
--
ALTER TABLE `cities`
  ADD PRIMARY KEY (`CityID`);

--
-- Indexes for table `flights`
--
ALTER TABLE `flights`
  ADD PRIMARY KEY (`FlightID`),
  ADD KEY `FK_AirplaneID` (`AirplaneID`),
  ADD KEY `FK_DepartureID` (`DepartureCityID`),
  ADD KEY `FK_DestinationID` (`DestinationCityID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`UserID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `FK_FlightID` FOREIGN KEY (`FlightID`) REFERENCES `flights` (`FlightID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_UserID` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `flights`
--
ALTER TABLE `flights`
  ADD CONSTRAINT `FK_AirplaneID` FOREIGN KEY (`AirplaneID`) REFERENCES `ariplanes` (`AirplaneID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_DepartureID` FOREIGN KEY (`DepartureCityID`) REFERENCES `cities` (`CityID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_DestinationID` FOREIGN KEY (`DestinationCityID`) REFERENCES `cities` (`CityID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
