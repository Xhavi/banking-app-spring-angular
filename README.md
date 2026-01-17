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

Variables de entorno usadas por el backend:

- `DB_HOST` (default: `localhost`)
- `DB_PORT` (default: `5432`)
- `DB_NAME` (default: `banking`)
- `DB_USER` (default: `banking_user`)
- `DB_PASSWORD` (default: `banking_pass`)

## Desarrollo local

### Backend

```bash
cd backend
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm ci
npm run start
```

Compilar frontend:

```bash
cd frontend
npm ci
npm run build
```

## Base de datos

El script `database/BaseDatos.sql` crea las tablas `persona`, `cliente`, `cuenta` y `movimiento` con datos de ejemplo.
