-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2023 at 12:02 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestion_abs`
--

-- --------------------------------------------------------

--
-- Table structure for table `absence`
--

CREATE TABLE `absence` (
  `id_abs` int(8) NOT NULL,
  `date_abs` date NOT NULL,
  `apogee` int(8) NOT NULL,
  `id_module` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `absence`
--

INSERT INTO `absence` (`id_abs`, `date_abs`, `apogee`, `id_module`) VALUES
(1, '2023-06-20', 19008961, 5),
(3, '2023-06-14', 19008962, 7),
(7, '2023-06-08', 19008962, 7),
(8, '2023-05-30', 19008962, 7),
(12, '2023-06-20', 19008961, 5),
(56, '2023-06-21', 4567, 5),
(58, '2023-06-21', 19008961, 5),
(85, '2023-06-21', 1345678, 5),
(87, '2023-06-21', 19008962, 8),
(88, '2023-06-23', 4567, 5),
(89, '2023-06-22', 1345678, 6);

-- --------------------------------------------------------

--
-- Table structure for table `etudiant`
--

CREATE TABLE `etudiant` (
  `apogee` int(8) NOT NULL,
  `name` varchar(80) NOT NULL,
  `id_filiere` int(8) NOT NULL,
  `id_semestre` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `etudiant`
--

INSERT INTO `etudiant` (`apogee`, `name`, `id_filiere`, `id_semestre`) VALUES
(4567, 'reda', 58, 2),
(1345678, 'alae', 58, 2),
(19008961, 'mehdi kaalat', 58, 2),
(19008962, 'reda eljidi', 59, 2);

-- --------------------------------------------------------

--
-- Table structure for table `filiere`
--

CREATE TABLE `filiere` (
  `id_filiere` int(8) NOT NULL,
  `nom_filiere` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `filiere`
--

INSERT INTO `filiere` (`id_filiere`, `nom_filiere`) VALUES
(58, 'Géoinformation'),
(59, 'Genie Industriel');

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE `module` (
  `id_module` int(8) NOT NULL,
  `nom_module` varchar(100) NOT NULL,
  `id_prof` int(8) NOT NULL,
  `id_filiere` int(8) NOT NULL,
  `id_semestre` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`id_module`, `nom_module`, `id_prof`, `id_filiere`, `id_semestre`) VALUES
(5, 'Geometrie', 12345, 58, 2),
(6, 'Programmation Java', 12345, 58, 2),
(7, 'Math', 123456, 59, 1),
(8, 'Tec', 123456, 59, 2),
(10, 'Analyse', 123456, 58, 2);

-- --------------------------------------------------------

--
-- Table structure for table `professeur`
--

CREATE TABLE `professeur` (
  `id_prof` int(8) NOT NULL,
  `name_prof` varchar(80) NOT NULL,
  `email_prof` varchar(80) NOT NULL,
  `password_prof` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `professeur`
--

INSERT INTO `professeur` (`id_prof`, `name_prof`, `email_prof`, `password_prof`) VALUES
(12345, 'mehdi kaalat', 'mehdi@gmail.com', '12345'),
(123456, 'bernoussi', 'bernoussi@gmail.com', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `semestre`
--

CREATE TABLE `semestre` (
  `id_semestre` int(8) NOT NULL,
  `name_semestre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `semestre`
--

INSERT INTO `semestre` (`id_semestre`, `name_semestre`) VALUES
(1, 'S1'),
(2, 'S2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absence`
--
ALTER TABLE `absence`
  ADD PRIMARY KEY (`id_abs`),
  ADD KEY `apogee_fk` (`apogee`),
  ADD KEY `module_fk` (`id_module`);

--
-- Indexes for table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`apogee`),
  ADD KEY `filiere_fk1` (`id_filiere`),
  ADD KEY `semestre_fk` (`id_semestre`);

--
-- Indexes for table `filiere`
--
ALTER TABLE `filiere`
  ADD PRIMARY KEY (`id_filiere`);

--
-- Indexes for table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`id_module`),
  ADD KEY `id_prof` (`id_prof`),
  ADD KEY `filiere_fk` (`id_filiere`),
  ADD KEY `semestre_fk2` (`id_semestre`);

--
-- Indexes for table `professeur`
--
ALTER TABLE `professeur`
  ADD PRIMARY KEY (`id_prof`);

--
-- Indexes for table `semestre`
--
ALTER TABLE `semestre`
  ADD PRIMARY KEY (`id_semestre`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `absence`
--
ALTER TABLE `absence`
  MODIFY `id_abs` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT for table `etudiant`
--
ALTER TABLE `etudiant`
  MODIFY `apogee` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19008968;

--
-- AUTO_INCREMENT for table `filiere`
--
ALTER TABLE `filiere`
  MODIFY `id_filiere` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT for table `module`
--
ALTER TABLE `module`
  MODIFY `id_module` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `semestre`
--
ALTER TABLE `semestre`
  MODIFY `id_semestre` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `absence`
--
ALTER TABLE `absence`
  ADD CONSTRAINT `apogee_fk` FOREIGN KEY (`apogee`) REFERENCES `etudiant` (`apogee`),
  ADD CONSTRAINT `module_fk` FOREIGN KEY (`id_module`) REFERENCES `module` (`id_module`);

--
-- Constraints for table `etudiant`
--
ALTER TABLE `etudiant`
  ADD CONSTRAINT `filiere_fk1` FOREIGN KEY (`id_filiere`) REFERENCES `filiere` (`id_filiere`),
  ADD CONSTRAINT `semestre_fk` FOREIGN KEY (`id_semestre`) REFERENCES `semestre` (`id_semestre`);

--
-- Constraints for table `module`
--
ALTER TABLE `module`
  ADD CONSTRAINT `filiere_fk` FOREIGN KEY (`id_filiere`) REFERENCES `filiere` (`id_filiere`),
  ADD CONSTRAINT `module_ibfk_2` FOREIGN KEY (`id_prof`) REFERENCES `professeur` (`id_prof`),
  ADD CONSTRAINT `semestre_fk2` FOREIGN KEY (`id_semestre`) REFERENCES `semestre` (`id_semestre`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
