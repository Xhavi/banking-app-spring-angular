DROP TABLE IF EXISTS movimiento CASCADE;
DROP TABLE IF EXISTS cuenta CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS persona CASCADE;

CREATE TABLE persona (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  genero VARCHAR(20) NOT NULL,
  edad INTEGER NOT NULL,
  identificacion VARCHAR(50) NOT NULL UNIQUE,
  direccion VARCHAR(200),
  telefono VARCHAR(20)
);

CREATE TABLE cliente (
  id SERIAL PRIMARY KEY,
  cliente_id VARCHAR(50) NOT NULL UNIQUE,
  persona_id INTEGER NOT NULL REFERENCES persona(id),
  contrasena VARCHAR(100) NOT NULL,
  estado BOOLEAN DEFAULT TRUE
);

CREATE TABLE cuenta (
  id SERIAL PRIMARY KEY,
  numero_cuenta VARCHAR(30) NOT NULL UNIQUE,
  tipo VARCHAR(20) NOT NULL,
  saldo NUMERIC(19, 2) NOT NULL,
  estado BOOLEAN DEFAULT TRUE,
  cliente_id INTEGER NOT NULL REFERENCES cliente(id)
);

CREATE TABLE movimiento (
  id SERIAL PRIMARY KEY,
  cuenta_id INTEGER NOT NULL REFERENCES cuenta(id),
  fecha TIMESTAMP NOT NULL,
  tipo VARCHAR(20) NOT NULL,
  valor NUMERIC(19, 2) NOT NULL,
  saldo NUMERIC(19, 2) NOT NULL
);

INSERT INTO persona (nombre, genero, edad, identificacion, direccion, telefono)
VALUES
  ('Jose Lema', 'M', 30, '1101112223', 'Av. Siempre Viva 123', '0999991111'),
  ('Marianela Montalvo', 'F', 26, '1102223334', 'Calle Norte 456', '0999992222'),
  ('Juan Osorio', 'M', 35, '1103334445', 'Av. Central 789', '0999993333');

INSERT INTO cliente (cliente_id, persona_id, contrasena, estado)
VALUES
  ('CLI-100', 1, 'Clave123', true),
  ('CLI-200', 2, 'Clave456', true),
  ('CLI-300', 3, 'Clave789', true);

INSERT INTO cuenta (numero_cuenta, tipo, saldo, estado, cliente_id)
VALUES
  ('478758', 'Ahorros', 2000.00, true, 1),
  ('225487', 'Corriente', 1000.00, true, 2),
  ('495878', 'Ahorros', 1500.00, true, 3);

INSERT INTO movimiento (cuenta_id, fecha, tipo, valor, saldo)
VALUES
  (1, NOW() - INTERVAL '3 days', 'Deposito', 500.00, 2000.00),
  (1, NOW() - INTERVAL '2 days', 'Retiro', -200.00, 1800.00),
  (2, NOW() - INTERVAL '1 day', 'Deposito', 300.00, 1000.00),
  (3, NOW(), 'Retiro', -100.00, 1400.00);
