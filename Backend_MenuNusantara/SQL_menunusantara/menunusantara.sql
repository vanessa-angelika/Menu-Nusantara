-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 26, 2025 at 02:57 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `menunusantara`
--

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `ID_Menu` int(11) NOT NULL,
  `Nama_Makanan` varchar(100) NOT NULL,
  `Kategori` varchar(50) NOT NULL,
  `Harga_Awal` decimal(10,2) NOT NULL,
  `Persen_Diskon` int(5) NOT NULL,
  `Porsi` text NOT NULL,
  `Deskripsi` text DEFAULT NULL,
  `Link_Gambar` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`ID_Menu`, `Nama_Makanan`, `Kategori`, `Harga_Awal`, `Persen_Diskon`, `Porsi`, `Deskripsi`, `Link_Gambar`) VALUES
(1, 'Ayam Taliwang', 'Makanan', 30000.00, 10, '10', 'Ayam Taliwang merupakan hidangan khas Lombok, Indonesia yang terkenal dengan rasa pedas dan gurih. Ayam ini dibakar atau dipanggang dengan bumbu kaya rempah seperti cabai, bawang putih, terasi, dan jeruk nipis, menghasilkan rasa pedas, manis, dan sedikit asam yang lezat.', 'https://od.lk/s/NThfNDc5MjU3MTVf/Ayam%20Taliwang.png'),
(2, 'ahihihi', 'Makanan', 99999.00, 10, 'Jumbo', 'orak tau West nyoba bae ayam taliwang wangan coba aja testing kalimat', 'https://od.lk/s/NThfNDc5MjU3MTVf/Ayam%20Taliwang.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`ID_Menu`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `ID_Menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
