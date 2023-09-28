CREATE TABLE Empleado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    sexo CHAR(1) NOT NULL,
    dni VARCHAR(15) NOT NULL UNIQUE,
    categoria INT NOT NULL,
    salario DOUBLE NOT NULL
);