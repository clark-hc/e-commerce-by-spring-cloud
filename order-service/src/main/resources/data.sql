Drop TABLE IF EXISTS orders;

CREATE TABLE IF NOT EXISTS orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  quantity INTEGER NOT NULL,
  status VARCHAR(20) NOT NULL
);

insert into orders (user_id, product_id, quantity, status) values (1, 1, 1, 'CREATED');
insert into orders (user_id, product_id, quantity, status) values (1, 2, 2, 'CREATED');
insert into orders (user_id, product_id, quantity, status) values (1, 3, 3, 'CREATED');