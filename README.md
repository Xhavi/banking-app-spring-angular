# Banking App Monorepo (Spring Boot + Angular + Postgres)

Este repositorio contiene el scaffold inicial para un sistema bancario con backend en Spring Boot, frontend en Angular y base de datos PostgreSQL.

## Estructura

```
backend/   Spring Boot (Java 17, Maven)
frontend/  Angular (routing habilitado)
database/  SQL de esquema + datos base
```

## Requisitos

- Docker + Docker Compose
- Java 17 y Maven (para desarrollo local del backend)
- Node.js 18+ y npm (para desarrollo local del frontend)

## Ejecutar con Docker

Levanta PostgreSQL y el backend:

```bash
docker compose up -d --build
```

Servicios disponibles:

- Backend: http://localhost:8080/health
- Postgres: localhost:5432
- Frontend (local dev): http://localhost:4200

Variables de entorno usadas por el backend:

- `DB_HOST` (default: `localhost`)
- `DB_PORT` (default: `5432`)
- `DB_NAME` (default: `banking`)
- `DB_USER` (default: `banking_user`)
- `DB_PASSWORD` (default: `banking_pass`)
- `MOVIMIENTOS_DAILY_LIMIT` (default: `1000`)

## Desarrollo local

### Backend

```bash
cd backend
mvn spring-boot:run
```

Nota: por defecto el backend intenta conectarse a PostgreSQL en `localhost:5432` (ver `backend/src/main/resources/application.yml`).

- Si no tienes PostgreSQL levantado, primero ejecuta `docker compose up -d postgres` (o `docker compose up -d --build`).
- Alternativa sin Postgres/Docker: usa el perfil `local` (H2 en memoria):

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Pruebas:

```bash
cd backend
mvn test
```

### Frontend

```bash
cd frontend
npm ci
npm run start
```

Pruebas:

```bash
cd frontend
npm ci
npm test
```

Compilar frontend:

```bash
cd frontend
npm ci
npm run build
```

## Modelo de datos

- `Cliente` extiende el concepto de `Persona` con una relación 1:1 (`cliente.persona`). La información personal se guarda en la tabla `persona` y se referencia desde la tabla `cliente`.
- `Cuenta` pertenece a un `Cliente` (relación muchos-a-uno).
- `Movimiento` pertenece a una `Cuenta` (relación muchos-a-uno).

## Endpoints (Postman-ready)

Todos los endpoints soportan paginación estándar de Spring (`page`, `size`, `sort`).

### Clientes

- `POST /clientes`

```json
{
  "clienteId": "CLI-001",
  "contrasena": "supersecret",
  "estado": true,
  "persona": {
    "nombre": "Juan Perez",
    "genero": "M",
    "edad": 30,
    "identificacion": "123456789",
    "direccion": "Calle Principal 123",
    "telefono": "0999999999"
  }
}
```

- `GET /clientes?search=juan&page=0&size=10`

- `PUT /clientes/{id}` (mismo payload que POST)

- `DELETE /clientes/{id}`

### Cuentas

- `POST /cuentas`

```json
{
  "numeroCuenta": "000123456",
  "tipo": "AHORROS",
  "saldo": 1000.00,
  "estado": true,
  "clienteId": 1
}
```

- `GET /cuentas?search=AHORROS&page=0&size=10`

- `PUT /cuentas/{id}` (mismo payload que POST)

- `DELETE /cuentas/{id}`

### Movimientos

- `POST /movimientos`

```json
{
  "cuentaId": 1,
  "tipo": "DEBITO",
  "valor": 100.00,
  "fecha": "2024-01-25T10:15:00"
}
```

- `GET /movimientos?cuentaId=1&tipo=DEBITO&page=0&size=10`

- `PUT /movimientos/{id}` (mismo payload que POST)

- `DELETE /movimientos/{id}`

## Base de datos

El script `database/BaseDatos.sql` crea las tablas `persona`, `cliente`, `cuenta` y `movimiento` con datos de ejemplo.

## Postman

La colección Postman se encuentra en `postman/BankingApp.postman_collection.json`.
