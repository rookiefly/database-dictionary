SET MODE MySQL; -- for h2 test

CREATE TABLE clipboard
(
    id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    hash  VARCHAR(60)   NOT NULL ,
    content CLOB NOT NULL,
    UNIQUE KEY hash_unique (hash)
);