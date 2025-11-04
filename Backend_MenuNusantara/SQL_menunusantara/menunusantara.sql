-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 04, 2025 at 03:26 PM
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
(8, 'Lapis Legit', 'Kue', 120000.00, 15, 'Jumbo', 'Lapis legit adalah kue tradisional dengan lapisan-lapisan tipis dari adonan telur, mentega, dan gula. Rasa manis dan tekstur lembutnya membuatnya populer di acara-acara khusus.', 'https://od.lk/s/NThfNDc5MjU3MjJf/Lapis%20Legit.png'),
(9, 'Es Teler Segar', 'Minuman', 15000.00, 10, 'Jumbo', 'Es teler adalah minuman segar dari campuran buah-buahan seperti alpukat, nangka, dan kelapa muda, ditambah es serut, susu kental manis, dan sirup. Rasa manis dan dingin membuatnya sangat menyegarkan, terutama di hari panas.', 'https://od.lk/s/NThfNDc5MjU3MjRf/Es%20Teler.png'),
(10, 'Ayam Taliwang', 'Makanan', 30000.00, 10, 'Normal', 'Ayam Taliwang merupakan hidangan khas Lombok, Indonesia yang terkenal dengan rasa pedas dan gurih. Ayam ini dibakar atau dipanggang dengan bumbu kaya rempah seperti cabai, bawang putih, terasi, dan jeruk nipis, menghasilkan rasa pedas, manis, dan sedikit asam yang lezat.', 'https://od.lk/s/NThfNDc5MjU3MTVf/Ayam%20Taliwang.png');

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
  MODIFY `ID_Menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
