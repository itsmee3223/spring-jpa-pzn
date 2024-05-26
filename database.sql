CREATE DATABASE spring_jpa;
USE spring_jpa;

CREATE TABLE categories
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

SELECT *
FROM categories;