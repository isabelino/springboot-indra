INSERT INTO regiones(nombre) VALUES ('Europa');
INSERT INTO regiones(nombre) VALUES ('Centroamerica');
INSERT INTO regiones(nombre) VALUES ('Norteamerica');
INSERT INTO regiones(nombre) VALUES ('Sudamerica');
INSERT INTO regiones(nombre) VALUES ('Asia');
INSERT INTO regiones(nombre) VALUES ('Oceania');
INSERT INTO regiones(nombre) VALUES ('Africa');
INSERT INTO regiones(nombre) VALUES ('Antartida');

INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(1,"Jose","Perez","jp@email.com",454241,"2022-02-11");
INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(2,"Maria","Walker","mw@email.com",232323,"2022-02-11");
INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(3,"Juana","Diaz","jd@email.com",3454545,"2022-02-11");
INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(4,"Juan","Sanchez","js@email.com",676767,"2022-02-11");
INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(5,"Ramon","Valdez","rv@email.com",787878,"2022-02-11");
INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(6,"Luis","Colina","lc@email.com",78787878,"2022-02-11");
INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(7,"Felix","Cardozo","fc@email.com",787878,"2022-02-11");
INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(8,"Martin","Araujo","ma@email.com",899889,"2022-02-11");
INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(8,"David","Ojeda","do@email.com",7886767,"2022-02-11");
INSERT INTO clientes(region_id,nombre,apellido,email,telefono,create_at) VALUES(1,"Daniel","Gues","dg@email.com",4545445,"2022-02-11");


INSERT INTO usuarios (username,password,enabled) VALUES ('rolando','$2a$10$VTMecMi.QwIynlpyuBHAAenhz9Wg2fEk4VbaOlXn.2xKkCJTMf75u',1);
INSERT INTO usuarios (username,password,enabled) VALUES ('admin','$2a$10$nSgo1TPH4IQRro7HkVqrBO.cNC1cXrW5Xyhs5u/NwkjeEUh9Bo65G',1);

INSERT INTO roles (nombre) VALUES('ROLE_USER');
INSERT INTO roles (nombre) VALUES('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id,role_id) VALUES(1,1);
INSERT INTO usuarios_roles (usuario_id,role_id) VALUES(2,2);
INSERT INTO usuarios_roles (usuario_id,role_id) VALUES(2,1);
