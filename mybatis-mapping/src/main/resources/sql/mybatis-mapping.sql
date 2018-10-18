/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.11 : Database - mybatis
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mybatis` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mybatis`;

/*Table structure for table `author` */

DROP TABLE IF EXISTS `author`;

CREATE TABLE `author` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '作者id',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='作者表';

/*Data for the table `author` */

insert  into `author`(`id`,`username`,`password`,`email`) values (1,'小明','xiaoming123','xiaoming@163.com'),(2,'小王','xiaowang123','xiaowang@163.com'),(3,'大牛','daniu123','daniu@163.com'),(4,'黑子','heizi123','heizi@163.com');

/*Table structure for table `blog` */

DROP TABLE IF EXISTS `blog`;

CREATE TABLE `blog` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `title` varchar(20) DEFAULT NULL COMMENT '博客标题',
  `author_id` int(10) unsigned NOT NULL COMMENT '作者id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `author_fk` (`author_id`),
  CONSTRAINT `author_fk` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='博客表';

/*Data for the table `blog` */

insert  into `blog`(`id`,`title`,`author_id`) values (1,'小明的博客',1),(2,'小王的博客',2),(3,'大牛的博客',3),(4,'黑子的博客',4);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `post_id` int(10) unsigned NOT NULL COMMENT '博客标题',
  `content` text COMMENT '博客内容',
  PRIMARY KEY (`id`),
  KEY `post_fk` (`post_id`),
  CONSTRAINT `post_fk` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

/*Data for the table `comment` */

/*Table structure for table `post` */

DROP TABLE IF EXISTS `post`;

CREATE TABLE `post` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '博客id',
  `blog_id` int(10) unsigned NOT NULL COMMENT '博客标题',
  `content` text COMMENT '博客内容',
  `draft` int(11) NOT NULL COMMENT '草稿',
  PRIMARY KEY (`id`),
  KEY `blog_fk` (`blog_id`),
  CONSTRAINT `blog_fk` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='发布表';

/*Data for the table `post` */

insert  into `post`(`id`,`blog_id`,`content`,`draft`) values (1,1,'小明-第1篇测试啦啦啦',0),(2,1,'小明-第2篇测试啦啦啦',0),(3,1,'第3篇测试啦啦啦',0),(4,2,'小王-我是大王， 大家好',0),(5,2,'大牛-我是一只牛',0);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `student_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `locked` tinyint(4) DEFAULT NULL COMMENT '状态(0:正常,1:锁定)',
  `gmt_created` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '存入数据库的时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改的时间',
  `delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生表';

/*Data for the table `student` */

insert  into `student`(`student_id`,`name`,`phone`,`email`,`sex`,`locked`,`gmt_created`,`gmt_modified`,`delete`) values (1,'小明','13821378270','xiaoming@mybatis.cn',1,0,'2018-08-29 18:27:42','2018-10-08 20:54:25',NULL),(2,'小丽','13821378271','xiaoli@mybatis.cn',0,0,'2018-08-30 18:27:42','2018-10-08 20:54:29',NULL),(3,'小刚','13821378272','xiaogang@mybatis.cn',1,0,'2018-08-31 18:27:42','2018-10-08 20:55:08',NULL),(4,'小花','13821378273','xiaohua@mybatis.cn',0,0,'2018-09-01 18:27:42','2018-10-08 20:55:12',NULL),(5,'小强','13821378274','xiaoqiang@mybatis.cn',1,0,'2018-09-02 18:27:42','2018-10-08 20:55:18',NULL),(6,'小红','13821378275','xiaohong@mybatis.cn',0,0,'2018-09-03 18:27:42','2018-10-08 20:55:27',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
