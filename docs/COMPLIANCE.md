# Compliance Checklist

| Requirement | Status | Where implemented | Notes |
| --- | --- | --- | --- |
| Layered architecture (Controller → Service → Repository) | OK | `backend/src/main/java/com/bankingapp/backend/{controller,service,repository}` | DTOs used for requests/responses. |
| CRUD for Clientes/Cuentas/Movimientos | OK | `backend/src/main/java/com/bankingapp/backend/controller/*Controller.java` | Includes GET/POST/PUT/DELETE. |
| Search capability for list endpoints | OK | `ClienteServiceImpl`, `CuentaServiceImpl`, `MovimientoServiceImpl` | Query params `search`, `cuentaId`, `tipo`. |
| Business rules: saldo disponible, cupo diario | OK | `MovimientoServiceImpl` | Daily limit configurable via `MOVIMIENTOS_DAILY_LIMIT`. |
| Movement strategy pattern | OK | `DebitoMovimientoStrategy`, `CreditoMovimientoStrategy` | Strategy selection in `MovimientoServiceImpl`. |
| Report JSON + PDF base64 | OK | `ReporteServiceImpl`, `ReporteResponse` | Includes client, account summaries, movements, and PDF. |
| Global exception handler | OK | `GlobalExceptionHandler` | Consistent JSON response. |
| Minimum backend tests (2+) | OK | `HealthControllerTest`, `MovimientoControllerTest` | Uses H2 test profile. |
| Angular layout with sidebar + header BANCO | OK | `frontend/src/app/app.component.html` | Custom CSS only. |
| Frontend pages with CRUD forms | OK | `frontend/src/app/pages/*` | Clientes/Cuentas/Movimientos forms and lists. |
| Report page JSON + PDF download | OK | `frontend/src/app/pages/reportes` | Base64 decoded to PDF download. |
| Jest unit tests (2+) | OK | `frontend/src/app/app.component.spec.ts`, `frontend/src/app/services/api.service.spec.ts` | `npm test` uses Jest. |
| Postgres + backend docker-compose | OK | `docker-compose.yml` | DB init mounts `database/BaseDatos.sql`. |
| BaseDatos.sql seed data | OK | `database/BaseDatos.sql` | Includes Jose Lema, Marianela Montalvo, Juan Osorio. |
| Postman collection | OK | `postman/BankingApp.postman_collection.json` | Uses `{{baseUrl}}` variable. |
| README run commands + ports | OK | `README.md` | Includes docker/local/test commands. |
