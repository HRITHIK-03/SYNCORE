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
