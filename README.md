# SYNCORE - Healthcare Data Integration Platform (Steps 1-4)

This repository is a runnable skeleton of the SYNCORE backend described on the resume.
It includes:
- Spring Boot REST API (Patients)
- Redis caching (via Spring Cache)
- Data masking filter (demo)
- Structured JSON logs (EFK-ready)
- FTP poller + Mail reporter + Scheduler
- Swagger/OpenAPI UI
- Unit tests (MockMvc) and GitHub Actions CI pipeline
- Docker + docker-compose for quick local setup

## Quick start (Docker)

1. Build and run everything:
```bash
docker compose up --build
```

2. API endpoints:
- `GET  http://localhost:8080/api/patients` - list patients
- `POST http://localhost:8080/api/patients` - create patient (JSON body)
- `GET  http://localhost:8080/api/patients/{id}` - fetch patient (cached)
- `GET  http://localhost:8080/api/patients/simulate/{input}` - simulate internal call

3. MailHog UI (view reports): http://localhost:8025
4. Swagger UI: http://localhost:8080/swagger-ui.html

## Run tests locally
```bash
mvn test
```

## CI
A GitHub Actions workflow `.github/workflows/ci.yml` is included to run `mvn -B verify` on push/pull requests.

## License
MIT
