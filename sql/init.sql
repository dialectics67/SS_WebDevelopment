-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主机： db
-- 生成日期： 2022-10-16 16:10:51
-- 服务器版本： 5.7.39
-- PHP 版本： 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `ss_web`
--

-- --------------------------------------------------------

--
-- 表的结构 `room`
--

CREATE TABLE `room`
(
    `id`                int(10) UNSIGNED NOT NULL,
    `room_id`           int(10) UNSIGNED NOT NULL,
    `room_sex`          tinyint(4)       NOT NULL,
    `room_available`    tinyint(4)       NOT NULL,
    `bed_cnt_all`       tinyint(4)       NOT NULL,
    `bet_cnt_available` tinyint(4)       NOT NULL,
    `bed_cnt_free`      tinyint(4)       NOT NULL,
    `floor_id`          tinyint(4)       NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- 转存表中的数据 `room`
--

INSERT INTO `room` (`id`, `room_id`, `room_sex`, `room_available`, `bed_cnt_all`, `bet_cnt_available`, `bed_cnt_free`,
                    `floor_id`)
VALUES (1, 9211, 0, 1, 3, 2, 2, 9),
       (2, 9212, 0, 1, 4, 4, 4, 9),
       (3, 5211, 1, 1, 4, 4, 4, 5),
       (4, 5212, 1, 0, 4, 4, 4, 5),
       (5, 5213, 1, 1, 4, 3, 3, 5),
       (6, 8211, 0, 1, 7, 7, 7, 8);

-- --------------------------------------------------------

--
-- 表的结构 `room_bed_user`
--

CREATE TABLE `room_bed_user`
(
    `id`      int(10) UNSIGNED NOT NULL,
    `room_id` int(10) UNSIGNED NOT NULL,
    `bed_id`  int(10) UNSIGNED NOT NULL,
    `user_id` int(10) UNSIGNED DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- 转存表中的数据 `room_bed_user`
--

INSERT INTO `room_bed_user` (`id`, `room_id`, `bed_id`, `user_id`)
VALUES (3, 1, 0, NULL),
       (4, 1, 1, NULL),
       (5, 1, 2, NULL),
       (6, 2, 0, NULL),
       (7, 2, 1, NULL),
       (8, 2, 2, NULL),
       (9, 2, 3, NULL),
       (10, 3, 0, NULL),
       (11, 3, 1, NULL),
       (12, 3, 2, NULL),
       (13, 3, 3, NULL),
       (14, 4, 0, NULL),
       (15, 4, 1, NULL),
       (16, 4, 2, NULL),
       (17, 4, 3, NULL),
       (18, 5, 0, NULL),
       (19, 5, 1, NULL),
       (20, 5, 2, NULL),
       (21, 5, 3, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `submission`
--

CREATE TABLE `submission`
(
    `id`       int(10) UNSIGNED NOT NULL,
    `floor_id` tinyint(4)       NOT NULL,
    `user_id`  int(10) UNSIGNED NOT NULL,
    `user_cnt` tinyint(4)       NOT NULL,
    `status`   tinyint(4)       NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 表的结构 `submission_user`
--

CREATE TABLE `submission_user`
(
    `id`            int(10) UNSIGNED NOT NULL,
    `submission_id` int(10) UNSIGNED NOT NULL,
    `user_id`       int(10) UNSIGNED NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user`
(
    `id`         int(10) UNSIGNED NOT NULL,
    `user_id`    int(10) UNSIGNED NOT NULL,
    `user_name`  varchar(30)      NOT NULL,
    `user_sex`   tinyint(4)       NOT NULL,
    `user_type`  tinyint(4)       NOT NULL,
    `birthday`   date        DEFAULT NULL,
    `password`   varchar(40)      NOT NULL,
    `phone`      varchar(20) DEFAULT NULL,
    `check_code` varchar(10) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='user basic information';

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `user_id`, `user_name`, `user_sex`, `user_type`, `birthday`, `password`, `phone`,
                    `check_code`)
VALUES (1, 2201210001, '一号', 0, 0, '2000-01-01', 'e10adc3949ba59abbe56e057f20f883e', '13800000000', '7897809319'),
       (2, 2201210002, '二号', 1, 0, '2000-01-02', '96e79218965eb72c92a549dd5a330112', '13800000001', '3454353464');

--
-- 转储表的索引
--

--
-- 表的索引 `room`
--
ALTER TABLE `room`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `room_id` (`room_id`);

--
-- 表的索引 `room_bed_user`
--
ALTER TABLE `room_bed_user`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `user_id` (`user_id`),
    ADD KEY `room_id` (`room_id`);

--
-- 表的索引 `submission`
--
ALTER TABLE `submission`
    ADD PRIMARY KEY (`id`),
    ADD KEY `user_id` (`user_id`);

--
-- 表的索引 `submission_user`
--
ALTER TABLE `submission_user`
    ADD PRIMARY KEY (`id`),
    ADD KEY `submission_id` (`submission_id`),
    ADD KEY `user_id` (`user_id`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
    ADD PRIMARY KEY (`id`),
    ADD UNIQUE KEY `user_id` (`user_id`),
    ADD UNIQUE KEY `phone` (`phone`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `room`
--
ALTER TABLE `room`
    MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 7;

--
-- 使用表AUTO_INCREMENT `room_bed_user`
--
ALTER TABLE `room_bed_user`
    MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 22;

--
-- 使用表AUTO_INCREMENT `submission`
--
ALTER TABLE `submission`
    MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `submission_user`
--
ALTER TABLE `submission_user`
    MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
    MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 3;

--
-- 限制导出的表
--

--
-- 限制表 `room_bed_user`
--
ALTER TABLE `room_bed_user`
    ADD CONSTRAINT `room_bed_user_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`),
    ADD CONSTRAINT `room_bed_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- 限制表 `submission`
--
ALTER TABLE `submission`
    ADD CONSTRAINT `submission_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- 限制表 `submission_user`
--
ALTER TABLE `submission_user`
    ADD CONSTRAINT `submission_user_ibfk_1` FOREIGN KEY (`submission_id`) REFERENCES `submission` (`id`),
    ADD CONSTRAINT `submission_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
