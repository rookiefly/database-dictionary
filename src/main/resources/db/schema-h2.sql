SET MODE MySQL; -- for h2 test

CREATE TABLE clipboard
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    hash  VARCHAR(65)   NOT NULL,
    content CLOB NOT NULL
);