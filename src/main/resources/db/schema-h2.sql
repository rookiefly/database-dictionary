SET MODE MySQL; -- for h2 test

CREATE TABLE clipboard
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    hash  BIGINT   NOT NULL ,
    content CLOB NOT NULL,
    UNIQUE KEY hash_unique (hash)
);