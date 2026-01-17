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
  ('Juan Perez', 'M', 35, '110112233', 'Av. Central 123', '555-0101'),
  ('Maria Lopez', 'F', 28, '110223344', 'Calle Norte 456', '555-0202'),
  ('Carlos Diaz', 'M', 42, '110334455', 'Av. Sur 789', '555-0303');

INSERT INTO cliente (cliente_id, persona_id, contrasena, estado)
VALUES
  ('CLI-001', 1, 'secreto1', true),
  ('CLI-002', 2, 'secreto2', true),
  ('CLI-003', 3, 'secreto3', true);

INSERT INTO cuenta (numero_cuenta, tipo, saldo, estado, cliente_id)
VALUES
  ('CTA-1001', 'Ahorros', 1500.00, true, 1),
  ('CTA-1002', 'Corriente', 2500.00, true, 2),
  ('CTA-1003', 'Ahorros', 500.00, true, 3);

INSERT INTO movimiento (cuenta_id, fecha, tipo, valor, saldo)
VALUES
  (1, NOW() - INTERVAL '2 days', 'Deposito', 500.00, 1500.00),
  (2, NOW() - INTERVAL '1 day', 'Retiro', -200.00, 2500.00),
  (3, NOW(), 'Deposito', 300.00, 500.00);
