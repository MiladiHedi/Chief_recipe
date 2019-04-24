# create database dev

CREATE DATABASE  recipe_db_integration;
use recipe_db_integration;

CREATE USER 'dev'@'localhost' IDENTIFIED BY 'devp';
CREATE USER 'dev'@'%' IDENTIFIED BY 'devp';

GRANT ALL ON recipe_db_integration.* to 'dev'@'localhost';
GRANT ALL ON recipe_db_integration.* to 'dev'@'%';

use recipe_db_integration;

CREATE TABLE  `recipe` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `cook_time` int(11) DEFAULT NULL,
  `difficulty` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prep_time` int(11) DEFAULT NULL,
  `servings` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rm1mlratj8yf3e1yxwk156x4p` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE  `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3x7l6yk1oxdxmdh4am3yq2y38` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE  `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `com_content` longtext COLLATE utf8mb4_unicode_ci,
  `date` date DEFAULT NULL,
  `recipe_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe5i1rxybcm40jcn98fj1jmvit` (`recipe_id`),
  CONSTRAINT `FKe5i1rxybcm40jcn98fj1jmvit` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE  `ingredient` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unit_of_measure` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `recipe_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj0s4ywmqqqw4h5iommigh5yja` (`recipe_id`),
  CONSTRAINT `FKj0s4ywmqqqw4h5iommigh5yja` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE  `recipe_category` (
  `recipe_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`recipe_id`,`category_id`),
  KEY `FKqsi87i8d4qqdehlv2eiwvpwb` (`category_id`),
  CONSTRAINT `FKcqlqnvfyarhieewfeayk3v25v` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`),
  CONSTRAINT `FKqsi87i8d4qqdehlv2eiwvpwb` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

# create database prod

CREATE DATABASE recipe_db_prod;
use recipe_db_prod;
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'adminp';
CREATE USER 'admin'@'%' IDENTIFIED BY 'adminp';

GRANT ALL ON recipe_db_prod.* to 'admin'@'localhost';
GRANT ALL ON recipe_db_prod.* to 'admin'@'%';

CREATE USER 'read_only_user'@'%' IDENTIFIED BY 'userp';

GRANT SELECT ON recipe_db_prod.* to 'read_only_user'@'%';

CREATE TABLE  `recipe` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `cook_time` int(11) DEFAULT NULL,
  `difficulty` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prep_time` int(11) DEFAULT NULL,
  `servings` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rm1mlratj8yf3e1yxwk156x4p` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE  `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3x7l6yk1oxdxmdh4am3yq2y38` (`description`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE  `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `com_content` longtext COLLATE utf8mb4_unicode_ci,
  `date` date DEFAULT NULL,
  `recipe_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe5i1rxybcm40jcn98fj1jmvit` (`recipe_id`),
  CONSTRAINT `FKe5i1rxybcm40jcn98fj1jmvit` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE  `ingredient` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `unit_of_measure` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `recipe_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj0s4ywmqqqw4h5iommigh5yja` (`recipe_id`),
  CONSTRAINT `FKj0s4ywmqqqw4h5iommigh5yja` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE  `recipe_category` (
  `recipe_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`recipe_id`,`category_id`),
  KEY `FKqsi87i8d4qqdehlv2eiwvpwb` (`category_id`),
  CONSTRAINT `FKcqlqnvfyarhieewfeayk3v25v` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`),
  CONSTRAINT `FKqsi87i8d4qqdehlv2eiwvpwb` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
