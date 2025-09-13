# SYNCORE - Healthcare Data Integration Platform (Step 1)

This repository is a minimal, runnable skeleton (Step 1) of the SYNCORE backend described on the resume.
It focuses on:
- Spring Boot REST API (Patients)
- Redis caching (via Spring Cache)
- Data masking filter (demo)
- Docker + docker-compose for quick local setup

## Quick start (Docker)

1. Build and run:
```bash
docker compose up --build
```

2. API endpoints:
- `GET  http://localhost:8080/api/patients` - list patients
- `POST http://localhost:8080/api/patients` - create patient (JSON body)
- `GET  http://localhost:8080/api/patients/{id}` - fetch patient (cached)

## Notes
- This is a minimal starting point for an iterative project. Next steps:
  - Add EFK logging config + structured logs
  - Add FTP/email integration services
  - Add tests and Swagger docs

## License
MIT

## Step 2 - Enhancements
- Structured JSON logging (EFK-ready via logback + logstash encoder)
- Global exception handler with proper JSON error responses
- Simulated microservice call (`GET /api/patients/simulate/{input}`) with random failures


## Step 3 - FTP & Email Automation (ready-to-run)
This step adds:
- FTP poller that connects to the `ftp` service, reads CSV files, imports patients.
- Email reporter using SMTP (MailHog) that receives summary reports.
- Background scheduler (runs every 30s by default).

### Run everything (no edits required)
```bash
docker compose up --build
```
- FTP server will be available on port 21 (container name: `syncore-ftp`). A sample CSV is pre-mounted at `./ftp-data/patients_import.csv`.
- MailHog web UI: http://localhost:8025 (view sent emails)
- Application API: http://localhost:8080/api/patients
