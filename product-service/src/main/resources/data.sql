Drop TABLE IF EXISTS products;

CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL
);

insert into products (name, price, stock) values ('Product 1', 1, 100);
insert into products (name, price, stock) values ('Product 2', 2, 200);
insert into products (name, price, stock) values ('Product 3', 3, 300);