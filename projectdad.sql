-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 26, 2024 at 05:40 PM
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
-- Database: `projectdad`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `BookID` int(11) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `ISBN` varchar(13) NOT NULL,
  `PublishedYear` year(4) NOT NULL,
  `Category` varchar(100) NOT NULL,
  `copies` int(11) DEFAULT 5
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`BookID`, `Title`, `ISBN`, `PublishedYear`, `Category`, `copies`) VALUES
(450, 'To Kill a Mockingbird', '9780061120084', '1960', 'Fiction', 1),
(451, '1984', '9780451524935', '1949', 'Dystopian', 4),
(452, 'The Great Gatsby', '9780743273565', '1925', 'Classic', 6),
(453, 'The Catcher in the Rye', '9780316269488', '1951', 'Classic', 5),
(454, 'The Hobbit', '9780547928227', '1937', 'Fantasy', 5),
(455, 'Fahrenheit 451', '9781451673319', '1953', 'Dystopian', 5),
(457, 'The Diary of a Young Girl', '9780553296983', '1947', 'Biography', 4),
(458, 'Harry Potter and the Sorcerer\'s Stone', '9780439708180', '1997', 'Fantasy', 5),
(459, 'The Lord of the Rings', '9780544003415', '1954', 'Fantasy', 5),
(460, 'The Alchemist', '9780061122415', '1988', 'Adventure', 5),
(461, 'The Book Thief', '9780375842207', '2005', 'Historical Fiction', 5),
(462, 'Animal Farm', '9780451526342', '1945', 'Political Satire', 5),
(463, 'Gone with the Wind', '9781451635621', '1936', 'Historical Fiction', 4),
(464, 'The Little Prince', '9780156012195', '1943', 'Children', 5),
(472, 'Brave New World', '9780060850524', '1932', 'Dystopian', 5),
(473, 'The Road', '9780307387899', '2006', 'Post-Apocalyptic', 5),
(475, 'The Grapes of Wrath', '9780143039433', '1939', 'Realist Novel', 5),
(478, 'The Handmaid\'s Tale', '9780385490818', '1985', 'Dystopian', 5),
(480, 'Catch-22', '9781451626650', '1961', 'Satire', 5),
(481, 'Slaughterhouse-Five', '9780440180296', '1969', 'Science Fiction', 5),
(482, 'The Chronicles of Narnia', '9780066238500', '1950', 'Fantasy', 5),
(485, 'A Clockwork Orange', '9780393312836', '1962', 'Dystopian', 5),
(486, 'One Hundred Years of Solitude', '9780060883287', '1967', 'Magical Realism', 5),
(487, 'The Old Man and the Sea', '9780684801223', '1952', 'Classic', 5),
(488, 'The Stranger', '9780679720201', '1942', 'Philosophical Fiction', 5),
(489, 'Beloved', '9781400033416', '1987', 'Historical Fiction', 5),
(490, 'The Shining', '9780307743657', '1977', 'Horror', 5),
(491, 'A Farewell to Arms', '9780684801469', '1929', 'War Fiction', 5),
(493, 'The Metamorphosis', '9780553213690', '1915', 'Absurdist Fiction', 5),
(495, 'Ulysses', '9780199535675', '1922', 'Modernist Novel', 5),
(505, 'Lolita', '9780679723165', '1955', 'Classic', 5),
(506, 'The Kite Runner', '9781594631931', '2003', 'Drama', 5),
(507, 'The Da Vinci Code', '9780307474278', '2003', 'Thriller', 5),
(508, 'Life of Pi', '9780156027328', '2001', 'Adventure', 5),
(509, 'Memoirs of a Geisha', '9780679781585', '1997', 'Historical Fiction', 5),
(510, 'The Bell Jar', '9780060837020', '1963', 'Autobiographical Fiction', 5),
(511, 'Invisible Man', '9780679732761', '1952', 'Classic', 5),
(512, 'Gone Girl', '9780307588371', '2012', 'Thriller', 5),
(513, 'The Fault in Our Stars', '9780525478812', '2012', 'Young Adult', 5),
(514, 'To the Lighthouse', '9780156907392', '1927', 'Modernist Novel', 5),
(515, 'The Secret Life of Bees', '9780142001745', '2001', 'Coming-of-Age', 5),
(516, 'Water for Elephants', '9781565125605', '2006', 'Historical Fiction', 5),
(517, 'Eat, Pray, Love', '9780143038412', '2006', 'Memoir', 5),
(518, 'The Girl on the Train', '9781594634024', '2015', 'Thriller', 5),
(519, 'Wild', '9780307476074', '2012', 'Memoir', 5),
(520, 'The Maze Runner', '9780385737951', '2009', 'Young Adult', 5),
(521, 'The Lovely Bones', '9780316044936', '2002', 'Drama', 5),
(522, 'The Perks of Being a Wallflower', '9781451696196', '1999', 'Young Adult', 5),
(523, 'Divergent', '9780062024039', '2011', 'Young Adult', 5),
(524, 'The Hunger Games', '9780439023481', '2008', 'Dystopian', 5),
(525, 'Twilight', '9780316015844', '2005', 'Young Adult', 5),
(526, 'The Help', '9780399155345', '2009', 'Historical Fiction', 5),
(527, 'Fifty Shades of Grey', '9780345803481', '2011', 'Romance', 5),
(528, 'Me Before You', '9780143124542', '2012', 'Romance', 5),
(529, 'The Notebook', '9780446676090', '1996', 'Romance', 5),
(530, 'The Girl with the Dragon Tattoo', '9780307949486', '2005', 'Thriller', 5),
(531, 'The Giver', '9780544336261', '1993', 'Dystopian', 5),
(532, 'Ender\'s Game', '9780812550702', '1985', 'Science Fiction', 5),
(533, 'The Time Traveler\'s Wife', '9781939126016', '2003', 'Science Fiction', 5),
(534, 'Outlander', '9780440212560', '1991', 'Historical Fiction', 5),
(535, 'The Catcher in the Rye', '9780316769488', '1951', 'Classic', 5),
(536, 'Harry Potter and the Chamber of Secrets', '9780439064873', '1998', 'Fantasy', 5),
(538, 'The Hobbit', '9780547928241', '1937', 'Fantasy', 5);

-- --------------------------------------------------------

--
-- Table structure for table `borrowedbook`
--

CREATE TABLE `borrowedbook` (
  `id` int(11) NOT NULL,
  `Title` varchar(50) NOT NULL,
  `ISBN` varchar(50) NOT NULL,
  `dateBorrowed` date NOT NULL,
  `memberID` int(11) NOT NULL DEFAULT 2332,
  `status` varchar(15) DEFAULT 'ONGOING'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `borrowedbook`
--

INSERT INTO `borrowedbook` (`id`, `Title`, `ISBN`, `dateBorrowed`, `memberID`, `status`) VALUES
(1, 'BookTitle', '1234567890', '2024-06-23', 1, 'RETURNED'),
(2, 'BookTitle', '9780547928227', '2024-06-22', 1, 'RETURNED'),
(30, '1984', '9780451524935', '2024-06-25', 1, 'RETURNED'),
(31, '1984', '9780451524935', '2024-06-25', 1, 'RETURNED'),
(32, '1984', '9780451524935', '2024-06-25', 1, 'RETURNED'),
(33, 'The Great Gatsby', '9780743273565', '2024-06-25', 1, 'RETURNED'),
(34, '1984', '9780451524935', '2024-06-25', 1, 'RETURNED'),
(35, 'To Kill a Mockingbird', '9780061120084', '2024-06-18', 1, 'RETURNED'),
(36, 'To Kill a Mockingbird', '9780061120084', '2024-06-25', 1, 'RETURNED'),
(37, 'The Lord of the Rings', '9780544003415', '2024-06-18', 1, 'RETURNED'),
(38, 'Gone with the Wind', '9781451635621', '2024-06-18', 1, 'ONGOING'),
(39, 'To Kill a Mockingbird', '9780061120084', '2024-06-26', 1, 'ONGOING'),
(40, 'The Great Gatsby', '9780743273565', '2024-06-26', 5, 'RETURNED'),
(41, 'The Diary of a Young Girl', '9780553296983', '2024-06-26', 5, 'ONGOING'),
(42, 'Harry Potter and the Sorcerer\'s Stone', '9780439708180', '2024-06-26', 5, 'RETURNED'),
(43, 'The Lord of the Rings', '9780544003415', '2024-06-26', 5, 'RETURNED'),
(44, 'The Alchemist', '9780061122415', '2024-06-26', 5, 'RETURNED'),
(45, 'The Catcher in the Rye', '9780316269488', '2024-06-26', 5, 'RETURNED'),
(46, 'The Book Thief', '9780375842207', '2024-06-26', 6, 'ONGOING'),
(47, 'The Hobbit', '9780547928241', '2024-06-26', 6, 'ONGOING'),
(48, 'The Book Thief', '9780375842207', '2024-06-26', 9, 'RETURNED'),
(49, 'To Kill a Mockingbird', '9780061120084', '2024-06-26', 9, 'RETURNED'),
(52, 'To Kill a Mockingbird', '9780061120084', '2024-06-26', 9, 'RETURNED'),
(53, 'To Kill a Mockingbird', '9780061120084', '2024-06-26', 9, 'RETURNED'),
(54, 'The Great Gatsby', '9780743273565', '2024-06-26', 5, 'RETURNED'),
(55, '1984', '9780451524935', '2024-06-26', 9, 'ONGOING'),
(56, '1984', '9780451524935', '2024-06-26', 9, 'ONGOING'),
(57, 'The Book Thief', '9780375842207', '2024-06-26', 9, 'ONGOING'),
(58, 'The Diary of a Young Girl', '9780553296983', '2024-06-26', 5, 'RETURNED');

-- --------------------------------------------------------

--
-- Table structure for table `member`
--

CREATE TABLE `member` (
  `memberID` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `memberStatus` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `member`
--

INSERT INTO `member` (`memberID`, `username`, `password`, `memberStatus`) VALUES
(1, 'a', 'a', 'APPROVED'),
(2, 'b', 'b', 'APPROVED'),
(3, '1', '1', 'APPROVED'),
(4, 'f', 'f', 'APPROVED'),
(5, 'khalis', '123', 'APPROVED'),
(6, '7', '7', 'APPROVED'),
(7, '123', '123', 'PENDING'),
(8, 'cad', 'cad', 'PENDING'),
(9, '9', '9', 'APPROVED');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`BookID`),
  ADD UNIQUE KEY `ISBN` (`ISBN`);

--
-- Indexes for table `borrowedbook`
--
ALTER TABLE `borrowedbook`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`memberID`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `BookID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=539;

--
-- AUTO_INCREMENT for table `borrowedbook`
--
ALTER TABLE `borrowedbook`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `member`
--
ALTER TABLE `member`
  MODIFY `memberID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
