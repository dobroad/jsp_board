USE `add`;

CREATE TABLE article(
    idx INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(idx),   
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL,
    nickname CHAR(100) NOT NULL,
    regDate DATETIME NOT NULL
);

DESC article;

SELECT * FROM article;