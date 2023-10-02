CREATE DATABASE IF NOT EXISTS order_db;

CREATE TABLE IF NOT EXISTS tb_customer (
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_product (
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_order (
  id_order BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  id_customer BIGINT NOT NULL,
  FOREIGN KEY (id_customer) REFERENCES tb_customer(id)
);

CREATE TABLE IF NOT EXISTS tb_item (
  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  id_order BIGINT NOT NULL,
  id_product BIGINT NOT NULL,
  quantity INT NOT NULL,
  FOREIGN KEY (id_order) REFERENCES tb_order(id_order),
  FOREIGN KEY (id_product) REFERENCES tb_product(id)
);

INSERT INTO tb_customer(id, name) VALUES (1, 'BTG Pactual');
INSERT INTO tb_customer(id, name) VALUES (2, 'BTG Teste');

INSERT INTO tb_product(id, name, price) VALUES (1, 'Lapis', 2.50);
INSERT INTO tb_product(id, name, price) VALUES (2, 'Estojo', 3.99);
INSERT INTO tb_product(id, name, price) VALUES (3, 'Caderno', 7.99);
INSERT INTO tb_product(id, name, price) VALUES (4, 'Caneta', 3.00);

INSERT INTO tb_order(id_order, id_customer) VALUES (1, 1);
INSERT INTO tb_order(id_order, id_customer) VALUES (2, 1);
INSERT INTO tb_order(id_order, id_customer) VALUES (3, 1);
INSERT INTO tb_order(id_order, id_customer) VALUES (4, 2);

INSERT INTO tb_item(id, id_order, id_product, quantity) VALUES (1, 1, 1, 7);
INSERT INTO tb_item(id, id_order, id_product, quantity) VALUES (2, 1, 3, 1);
INSERT INTO tb_item(id, id_order, id_product, quantity) VALUES (3, 2, 2, 4);
INSERT INTO tb_item(id, id_order, id_product, quantity) VALUES (4, 2, 4, 3);
INSERT INTO tb_item(id, id_order, id_product, quantity) VALUES (5, 4, 2, 2);
INSERT INTO tb_item(id, id_order, id_product, quantity) VALUES (6, 4, 1, 2);
INSERT INTO tb_item(id, id_order, id_product, quantity) VALUES (7, 3, 1, 2);
