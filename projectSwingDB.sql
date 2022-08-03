CREATE SCHEMA ejemploProjectSwing;
USE ejemploProjectSwing;
CREATE TABLE persona(
	id int not null auto_increment,
    nombre varchar(50),
    apellido varchar(50),
    edad int,
    primary key(id)
    );
CREATE TABLE producto(
	id int not null auto_increment,
    nombre varchar(20),
    marca varchar(20),
    creado_por int not null,
    primary key(id),
    foreign key(creado_por) references persona(id)
);

INSERT INTO persona (nombre, apellido, edad)
VALUES  ("Diego","Sanchez",23),
		("Sandra","Ramirez",13),
		("Laura","Perez",45),
		("Pedro","Trujillo",32);

INSERT INTO producto (nombre, marca, creado_por)
VALUES  ("iphon","apple",1),
		("ipad","apple",2),
		("macbook","apple",2),
		("imac","apple",3);


