-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jan 17, 2019 at 11:37 PM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `db_sticker_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `category_table`
--

CREATE TABLE `category_table` (
  `id` int(11) NOT NULL,
  `media_id` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `position` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `device_table`
--

CREATE TABLE `device_table` (
  `id` int(11) NOT NULL,
  `token` longtext COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `fos_user_table`
--

CREATE TABLE `fos_user_table` (
  `id` int(11) NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `username_canonical` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email_canonical` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `locked` tinyint(1) NOT NULL,
  `expired` tinyint(1) NOT NULL,
  `expires_at` datetime DEFAULT NULL,
  `confirmation_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_requested_at` datetime DEFAULT NULL,
  `roles` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT '(DC2Type:array)',
  `credentials_expired` tinyint(1) NOT NULL,
  `credentials_expire_at` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `facebook` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `instagram` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `twitter` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `emailo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `token` longtext COLLATE utf8_unicode_ci,
  `image` longtext COLLATE utf8_unicode_ci NOT NULL,
  `trusted` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `fos_user_table`
--

INSERT INTO `fos_user_table` (`id`, `username`, `username_canonical`, `email`, `email_canonical`, `enabled`, `salt`, `password`, `last_login`, `locked`, `expired`, `expires_at`, `confirmation_token`, `password_requested_at`, `roles`, `credentials_expired`, `credentials_expire_at`, `name`, `facebook`, `instagram`, `twitter`, `emailo`, `type`, `token`, `image`, `trusted`) VALUES
(1, 'ADMIN', 'admin', 'ADMIN', 'admin', 1, 'djtfgbufxr4gwk4k0gss4sgs4k48wc4', '$2y$13$djtfgbufxr4gwk4k0gss4ekodAwfJ3IP01OyKvMD.stoxgr6MMa2S', '2019-01-17 23:34:16', 0, 0, NULL, NULL, NULL, 'a:1:{i:0;s:10:\"ROLE_ADMIN\";}', 0, NULL, 'Ngoma Lodin', NULL, NULL, NULL, NULL, NULL, NULL, 'https://review.gbtcdn.com/upload/gearbest/avatar/20170603/40B058FF5D6FF8D7A18F6A937B88B65E.jpg', 1);

-- --------------------------------------------------------

--
-- Table structure for table `gallery_table`
--

CREATE TABLE `gallery_table` (
  `id` int(11) NOT NULL,
  `titre` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `medias_gallerys_table`
--

CREATE TABLE `medias_gallerys_table` (
  `gallery_id` int(11) NOT NULL,
  `media_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `media_table`
--

CREATE TABLE `media_table` (
  `id` int(11) NOT NULL,
  `titre` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `extension` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `date` datetime NOT NULL,
  `enabled` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

INSERT INTO `media_table` (`id`, `titre`, `url`, `type`, `extension`, `date`, `enabled`) VALUES
(0, 'slide_11.png', '524e3a80d0acc2ffcb0acd699ec9efa7.png', 'image/png', 'png', '2019-01-01 00:00:00', 1);

--
-- Table structure for table `packs_categories_table`
--

CREATE TABLE `packs_categories_table` (
  `pack_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `packs_table`
--

CREATE TABLE `packs_table` (
  `id` int(11) NOT NULL,
  `media_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `publisher` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `publisheremail` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `publisherwebsite` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `privacypolicywebsite` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `licenseagreementwebsite` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tags` longtext COLLATE utf8_unicode_ci,
  `size` int(11) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `review` tinyint(1) NOT NULL,
  `premium` tinyint(1) NOT NULL,
  `downloads` int(11) NOT NULL,
  `created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `packs_tags_table`
--

CREATE TABLE `packs_tags_table` (
  `pack_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rate_table`
--

CREATE TABLE `rate_table` (
  `id` int(11) NOT NULL,
  `pack_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `review` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `settings_table`
--

CREATE TABLE `settings_table` (
  `id` int(11) NOT NULL,
  `media_id` int(11) DEFAULT NULL,
  `appname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appdescription` longtext COLLATE utf8_unicode_ci,
  `googleplay` longtext COLLATE utf8_unicode_ci,
  `privacypolicy` longtext COLLATE utf8_unicode_ci,
  `firebasekey` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rewardedadmobid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `banneradmobid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bannerfacebookid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nativebannerfacebookid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bannertype` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nativeadmobid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nativefacebookid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nativeitem` int(11) DEFAULT NULL,
  `nativetype` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `interstitialadmobid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `interstitialfacebookid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `interstitialtype` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `interstitialclick` int(11) DEFAULT NULL,
  `publisherid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `appid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `settings_table`
--

INSERT INTO `settings_table` (`id`, `media_id`, `appname`, `appdescription`, `googleplay`, `privacypolicy`, `firebasekey`, `rewardedadmobid`, `banneradmobid`, `bannerfacebookid`, `nativebannerfacebookid`, `bannertype`, `nativeadmobid`, `nativefacebookid`, `nativeitem`, `nativetype`, `interstitialadmobid`, `interstitialfacebookid`, `interstitialtype`, `interstitialclick`, `publisherid`, `appid`) VALUES
(1, 0, 'Ultimate Stickers - WAStickerApps', 'The best stickers app on google play with 300+ sticker and more then 30 Pack', 'https://play.google.com/store/apps/details?id=be.irisnet.fixmystreet', '<h1>Privacy Policy Content</h1>', 'AIzaSyCg77N96veclCZBruelCXqKY5MVJc1nUds', 'ca-app-pub-3447169368463700/6873864498', 'ca-app-pub-3447169368463700/7588357978', '2169750233343295_2481411135510535', '2169750233343295_2169756580009327', 'BOTH', 'ca-app-pub-3447169368463700/5334796315', '2703176636427041_2705680412843330', 3, 'BOTH', 'ca-app-pub-3447169368463700/3509334552', '2169750233343295_2481425015509147', 'BOTH', 3, 'pub-3447169368463700', 'ca-app-pub-3447169368463700~4004336187');

-- --------------------------------------------------------

--
-- Table structure for table `slide_table`
--

CREATE TABLE `slide_table` (
  `id` int(11) NOT NULL,
  `pack_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `media_id` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `position` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stickers_table`
--

CREATE TABLE `stickers_table` (
  `id` int(11) NOT NULL,
  `media_id` int(11) DEFAULT NULL,
  `pack_id` int(11) DEFAULT NULL,
  `emojis` longtext COLLATE utf8_unicode_ci NOT NULL,
  `size` int(11) NOT NULL,
  `position` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `support_table`
--

CREATE TABLE `support_table` (
  `id` int(11) NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `subject` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `message` longtext COLLATE utf8_unicode_ci NOT NULL,
  `created` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tags_table`
--

CREATE TABLE `tags_table` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `search` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_followers`
--

CREATE TABLE `user_followers` (
  `user_id` int(11) NOT NULL,
  `follower_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `version_table`
--

CREATE TABLE `version_table` (
  `id` int(11) NOT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `features` longtext COLLATE utf8_unicode_ci NOT NULL,
  `code` int(11) NOT NULL,
  `enabled` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category_table`
--
ALTER TABLE `category_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_1E1AC00FEA9FDD75` (`media_id`);

--
-- Indexes for table `device_table`
--
ALTER TABLE `device_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `fos_user_table`
--
ALTER TABLE `fos_user_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_C3D4D4BD92FC23A8` (`username_canonical`),
  ADD UNIQUE KEY `UNIQ_C3D4D4BDA0D96FBF` (`email_canonical`);

--
-- Indexes for table `gallery_table`
--
ALTER TABLE `gallery_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `medias_gallerys_table`
--
ALTER TABLE `medias_gallerys_table`
  ADD PRIMARY KEY (`gallery_id`,`media_id`),
  ADD KEY `IDX_CC965DCE4E7AF8F` (`gallery_id`),
  ADD KEY `IDX_CC965DCEEA9FDD75` (`media_id`);

--
-- Indexes for table `media_table`
--
ALTER TABLE `media_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `packs_categories_table`
--
ALTER TABLE `packs_categories_table`
  ADD PRIMARY KEY (`pack_id`,`category_id`),
  ADD KEY `IDX_4FC04AFD1919B217` (`pack_id`),
  ADD KEY `IDX_4FC04AFD12469DE2` (`category_id`);

--
-- Indexes for table `packs_table`
--
ALTER TABLE `packs_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_53BC8512EA9FDD75` (`media_id`),
  ADD KEY `IDX_53BC8512A76ED395` (`user_id`);

--
-- Indexes for table `packs_tags_table`
--
ALTER TABLE `packs_tags_table`
  ADD PRIMARY KEY (`pack_id`,`tag_id`),
  ADD KEY `IDX_17CBC2C61919B217` (`pack_id`),
  ADD KEY `IDX_17CBC2C6BAD26311` (`tag_id`);

--
-- Indexes for table `rate_table`
--
ALTER TABLE `rate_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_666996651919B217` (`pack_id`),
  ADD KEY `IDX_66699665A76ED395` (`user_id`);

--
-- Indexes for table `settings_table`
--
ALTER TABLE `settings_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_4EF0C90FEA9FDD75` (`media_id`);

--
-- Indexes for table `slide_table`
--
ALTER TABLE `slide_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_77A059652B36786B` (`title`),
  ADD KEY `IDX_77A059651919B217` (`pack_id`),
  ADD KEY `IDX_77A0596512469DE2` (`category_id`),
  ADD KEY `IDX_77A05965EA9FDD75` (`media_id`);

--
-- Indexes for table `stickers_table`
--
ALTER TABLE `stickers_table`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_80E0FAA5EA9FDD75` (`media_id`),
  ADD KEY `IDX_80E0FAA51919B217` (`pack_id`);

--
-- Indexes for table `support_table`
--
ALTER TABLE `support_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tags_table`
--
ALTER TABLE `tags_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_followers`
--
ALTER TABLE `user_followers`
  ADD PRIMARY KEY (`user_id`,`follower_id`),
  ADD KEY `IDX_84E87043A76ED395` (`user_id`),
  ADD KEY `IDX_84E87043AC24F853` (`follower_id`);

--
-- Indexes for table `version_table`
--
ALTER TABLE `version_table`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category_table`
--
ALTER TABLE `category_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `device_table`
--
ALTER TABLE `device_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `fos_user_table`
--
ALTER TABLE `fos_user_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `gallery_table`
--
ALTER TABLE `gallery_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `media_table`
--
ALTER TABLE `media_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `packs_table`
--
ALTER TABLE `packs_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rate_table`
--
ALTER TABLE `rate_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `settings_table`
--
ALTER TABLE `settings_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `slide_table`
--
ALTER TABLE `slide_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `stickers_table`
--
ALTER TABLE `stickers_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `support_table`
--
ALTER TABLE `support_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tags_table`
--
ALTER TABLE `tags_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `version_table`
--
ALTER TABLE `version_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `category_table`
--
ALTER TABLE `category_table`
  ADD CONSTRAINT `FK_1E1AC00FEA9FDD75` FOREIGN KEY (`media_id`) REFERENCES `media_table` (`id`);

--
-- Constraints for table `medias_gallerys_table`
--
ALTER TABLE `medias_gallerys_table`
  ADD CONSTRAINT `FK_CC965DCE4E7AF8F` FOREIGN KEY (`gallery_id`) REFERENCES `gallery_table` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_CC965DCEEA9FDD75` FOREIGN KEY (`media_id`) REFERENCES `media_table` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `packs_categories_table`
--
ALTER TABLE `packs_categories_table`
  ADD CONSTRAINT `FK_4FC04AFD12469DE2` FOREIGN KEY (`category_id`) REFERENCES `category_table` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_4FC04AFD1919B217` FOREIGN KEY (`pack_id`) REFERENCES `packs_table` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `packs_table`
--
ALTER TABLE `packs_table`
  ADD CONSTRAINT `FK_53BC8512A76ED395` FOREIGN KEY (`user_id`) REFERENCES `fos_user_table` (`id`),
  ADD CONSTRAINT `FK_53BC8512EA9FDD75` FOREIGN KEY (`media_id`) REFERENCES `media_table` (`id`);

--
-- Constraints for table `packs_tags_table`
--
ALTER TABLE `packs_tags_table`
  ADD CONSTRAINT `FK_17CBC2C61919B217` FOREIGN KEY (`pack_id`) REFERENCES `packs_table` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_17CBC2C6BAD26311` FOREIGN KEY (`tag_id`) REFERENCES `tags_table` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `rate_table`
--
ALTER TABLE `rate_table`
  ADD CONSTRAINT `FK_666996651919B217` FOREIGN KEY (`pack_id`) REFERENCES `packs_table` (`id`),
  ADD CONSTRAINT `FK_66699665A76ED395` FOREIGN KEY (`user_id`) REFERENCES `fos_user_table` (`id`);

--
-- Constraints for table `settings_table`
--
ALTER TABLE `settings_table`
  ADD CONSTRAINT `FK_4EF0C90FEA9FDD75` FOREIGN KEY (`media_id`) REFERENCES `media_table` (`id`);

--
-- Constraints for table `slide_table`
--
ALTER TABLE `slide_table`
  ADD CONSTRAINT `FK_77A0596512469DE2` FOREIGN KEY (`category_id`) REFERENCES `category_table` (`id`),
  ADD CONSTRAINT `FK_77A059651919B217` FOREIGN KEY (`pack_id`) REFERENCES `packs_table` (`id`),
  ADD CONSTRAINT `FK_77A05965EA9FDD75` FOREIGN KEY (`media_id`) REFERENCES `media_table` (`id`);

--
-- Constraints for table `stickers_table`
--
ALTER TABLE `stickers_table`
  ADD CONSTRAINT `FK_80E0FAA51919B217` FOREIGN KEY (`pack_id`) REFERENCES `packs_table` (`id`),
  ADD CONSTRAINT `FK_80E0FAA5EA9FDD75` FOREIGN KEY (`media_id`) REFERENCES `media_table` (`id`);

--
-- Constraints for table `user_followers`
--
ALTER TABLE `user_followers`
  ADD CONSTRAINT `FK_84E87043A76ED395` FOREIGN KEY (`user_id`) REFERENCES `fos_user_table` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK_84E87043AC24F853` FOREIGN KEY (`follower_id`) REFERENCES `fos_user_table` (`id`) ON DELETE CASCADE;
