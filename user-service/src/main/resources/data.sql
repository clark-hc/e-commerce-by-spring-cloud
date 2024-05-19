Drop TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

insert into users (username, password) values ('test', '$2a$10$Fsk8gWig8AMnqrAki/jlceZjwdY2NE0b2ZY5.agFnqtlvYulV6XiG');