# HR Workforce Management API

Backend-only HR management REST API built with Java 17, Spring Boot 3, Spring Data JPA/Hibernate, PostgreSQL, Spring Security, JWT, Bean Validation and Maven. No frontend is included.

## Features
- JWT registration/login and role-based access (`ADMIN`, `HR`, `EMPLOYEE`)
- Department CRUD with duplicate-name validation
- Employee CRUD, unique email/code checks, department association, pagination/sorting and soft deactivation
- Leave request submission, pending-overlap validation, HR/Admin approval or rejection
- Attendance check-in/check-out, one record per employee per day, date-range report and worked minutes
- Bean Validation and centralized JSON error responses
- Docker Compose PostgreSQL setup

## Requirements
- JDK 17+
- Maven 3.9+
- Docker (optional) or local PostgreSQL

## Run
1. Start PostgreSQL: `docker compose up -d`
2. Set environment variables if needed. Defaults are for local development only:
   - `DB_URL=jdbc:postgresql://localhost:5432/hr_workforce`
   - `DB_USERNAME=postgres`
   - `DB_PASSWORD=postgres`
   - `JWT_SECRET` must be Base64 encoding of at least 32 random bytes in real use.
3. Build and run:
   ```bash
   mvn clean test
   mvn spring-boot:run
   ```
4. API base URL: `http://localhost:8080/api/v1`

## JWT secret
Generate a suitable secret, for example:
```bash
openssl rand -base64 32
```
Set `JWT_SECRET` to the generated output before running. Do not commit real secrets to GitHub. The checked-in fallback is development-only and must be replaced for any shared deployment.

## Authentication
`POST /auth/register`
```json
{"email":"admin@example.com","password":"ChangeMe123!","role":"ADMIN"}
```
`POST /auth/login`
```json
{"email":"admin@example.com","password":"ChangeMe123!"}
```
Use the returned token in Postman:
`Authorization: Bearer <token>`

**Important:** For a portfolio deployment, public self-registration should not allow users to assign themselves `ADMIN`/`HR`. Restrict registration to `EMPLOYEE` or implement an administrator-provisioned user workflow before deploying. This demo accepts the role in the request for local testing.

## Main endpoints

| Method | Endpoint | Access / purpose |
|---|---|---|
| POST | `/auth/register` | Create login user |
| POST | `/auth/login` | Login and receive JWT |
| GET | `/employees?page=0&size=10&sortBy=id&direction=asc` | List employees (authenticated) |
| GET | `/employees/{id}` | Employee detail |
| POST | `/employees` | ADMIN/HR create employee |
| PUT | `/employees/{id}` | ADMIN/HR update employee |
| DELETE | `/employees/{id}` | ADMIN soft-deactivate employee |
| GET | `/departments` | List departments |
| POST | `/departments` | ADMIN/HR create department |
| PUT | `/departments/{id}` | ADMIN/HR update department |
| DELETE | `/departments/{id}` | ADMIN delete department |
| POST | `/leaves` | Submit leave request |
| GET | `/leaves?employeeId=1` | List leave requests |
| PATCH | `/leaves/{id}/review?status=APPROVED` | ADMIN/HR review |
| POST | `/attendance/check-in?employeeId=1` | Check in |
| PATCH | `/attendance/check-out?employeeId=1` | Check out |
| GET | `/attendance?employeeId=1&start=2026-10-01&end=2026-10-31` | Attendance report |

All paths above are relative to `/api/v1`. For create/update employee, example:
```json
{
  "employeeCode": "EMP-1001",
  "firstName": "Asha",
  "lastName": "Patil",
  "email": "asha@example.com",
  "phone": "9876543210",
  "jobTitle": "Java Developer",
  "joiningDate": "2026-10-01",
  "departmentId": 1
}
```

## Postman quick-start
1. Register an account with a role for local testing.
2. Login and copy `token` from the response.
3. Add `Authorization: Bearer <token>` to protected requests.
4. Create a department using `POST /departments`.
5. Create an employee using `POST /employees` with that department ID.
6. Test leave and attendance endpoints.

## GitHub
```bash
git init
git add .
git commit -m "Initial HR workforce management backend"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/hr-workforce-management.git
git push -u origin main
```

## Notes and limitations
- This is a portfolio starter, not a production HR system. Review authorization ownership rules, registration privileges, audit logging, migrations, rate limiting, CORS, secrets and concurrency handling before deployment.
- Employee/attendance endpoints currently accept employee IDs; production systems should derive the employee identity from the authenticated principal and enforce record ownership.
- `ddl-auto: update` is convenient for local development; use Flyway/Liquibase migrations for production.
- Kafka, Redis, payroll and frontend are intentionally not included.
