-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主机： db
-- 生成日期： 2022-10-23 14:26:44
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

DELIMITER $$
--
-- 存储过程
--
CREATE
    DEFINER = `root`@`%` PROCEDURE `test`()
BEGIN
    DECLARE i_build, i_floor, i_room INT; # 申明楼号, 楼层. 房间号
    SET i_build = 5; # 5号楼
    SET i_floor = 1; # 最小层数
    WHILE i_floor < 5
        DO
            SET i_room = 1;
            WHILE i_room < 50
                DO
                    INSERT INTO room
                    (room_id, room_sex, room_available, bed_cnt_all, bet_cnt_available, bed_cnt_free, floor_id)
                    VALUES (i_build * 1000 + i_floor * 100 + i_room, i_floor mod 2, 1, (rand() mod 6) + 2, bed_cnt_all,
                            bed_cnt_all, i_floor); # 往test表添加数据
                    SET i_room = i_room + 1; # 循环一次,i加1
                end while;
            SET i_floor = i_floor + 1;
        END WHILE; # 结束while循环
END$$

DELIMITER ;

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
VALUES (1, 9211, 0, 1, 3, 3, 3, 9),
       (2, 9212, 0, 1, 4, 4, 4, 9);

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
       (9, 2, 3, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `submission`
--

CREATE TABLE `submission`
(
    `id`           int(10) UNSIGNED NOT NULL,
    `floor_id`     tinyint(4)            DEFAULT NULL,
    `user_id`      int(10) UNSIGNED NOT NULL,
    `user_cnt`     tinyint(4)       NOT NULL,
    `status`       tinyint(4)       NOT NULL,
    `submit_time`  timestamp        NULL DEFAULT NULL,
    `room_room_id` int(10) UNSIGNED      DEFAULT NULL
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
    `user_user_id`  int(10) UNSIGNED NOT NULL,
    `check_code`    varchar(10) DEFAULT NULL
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
       (2, 2201210002, '二号', 1, 0, '2000-01-02', '96e79218965eb72c92a549dd5a330112', '13800000001', '3454353464'),
       (4, 2201210003, '三号', 0, 0, '2000-10-06', 'e10adc3949ba59abbe56e057f20f883e', NULL, '4832948092'),
       (5, 2201210004, '四号', 1, 0, '1999-10-01', 'e10adc3949ba59abbe56e057f20f883e', '13800000002', '1543534432'),
       (6, 2201210005, '五号', 0, 0, '2001-01-21', 'e10adc3949ba59abbe56e057f20f883e', '13800000004', '6723642387'),
       (7, 2201210006, '六号', 0, 0, '2001-05-04', 'e10adc3949ba59abbe56e057f20f883e', '13800000005', '7389127489'),
       (8, 2201210007, '七号', 0, 0, '2022-10-23', 'e10adc3949ba59abbe56e057f20f883e', '13800000006', '5347849578');

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
    ADD KEY `user_id` (`user_id`),
    ADD KEY `room_room_id` (`room_room_id`);

--
-- 表的索引 `submission_user`
--
ALTER TABLE `submission_user`
    ADD PRIMARY KEY (`id`),
    ADD KEY `submission_id` (`submission_id`);

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
    AUTO_INCREMENT = 67;

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
    MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 21;

--
-- 使用表AUTO_INCREMENT `submission_user`
--
ALTER TABLE `submission_user`
    MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 19;

--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
    MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 9;

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
    ADD CONSTRAINT `room_room_id` FOREIGN KEY (`room_room_id`) REFERENCES `room` (`room_id`),
    ADD CONSTRAINT `submission_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- 限制表 `submission_user`
--
ALTER TABLE `submission_user`
    ADD CONSTRAINT `submission_user_ibfk_1` FOREIGN KEY (`submission_id`) REFERENCES `submission` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
