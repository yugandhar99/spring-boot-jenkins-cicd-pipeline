# Spring Boot CI/CD Pipeline with Jenkins

## Project Overview

This project is a **Spring Boot REST API** for managing professors and specialities. It also includes a complete CI/CD setup using **Jenkins, Maven, SonarQube, OWASP Dependency Check, Docker, Trivy, and Docker Compose**.

This repository is useful for demonstrating backend development, CI/CD automation, containerization, code quality checks, dependency scanning, image vulnerability scanning, and staging deployment.

## Features

- Create, update, delete, and retrieve professors
- Create, update, delete, and retrieve specialities
- Filter professors by speciality
- Filter professors by hiring date range
- MySQL database integration using Spring Data JPA
- Request validation using Jakarta Validation
- Swagger/OpenAPI documentation
- Spring Boot Actuator health endpoint
- Dockerized application runtime
- Docker Compose setup for application and MySQL
- Jenkins pipeline for build, test, scan, Docker image creation, and deployment
- SonarQube code quality analysis
- OWASP Dependency Check for dependency vulnerability scanning
- Trivy scan for Docker image vulnerabilities

## Technology Stack

| Category | Tools |
|---|---|
| Backend | Java 17, Spring Boot, Spring Web, Spring Data JPA |
| Database | MySQL, H2 for tests |
| Build | Maven |
| API Docs | Swagger / OpenAPI |
| CI/CD | Jenkins |
| Code Quality | SonarQube, JaCoCo |
| Security Scanning | OWASP Dependency Check, Trivy |
| Containers | Docker, Docker Compose |

## Project Structure

```text
.
├── src/main/java/ma/projet/gestionprofesseurs
│   ├── controllers
│   ├── dao
│   ├── entities
│   ├── exceptions
│   ├── repository
│   └── services
├── src/main/resources/application.properties
├── src/test/resources/application-test.properties
├── Dockerfile
├── Dockerfile.final
├── docker-compose.yml
├── Jenkinsfile
├── pom.xml
└── README.md
```

## API Endpoints

### Professor APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/professeur` | Get all professors |
| GET | `/api/professeur/{id}` | Get professor by ID |
| POST | `/api/professeur` | Create professor |
| PUT | `/api/professeur/{id}` | Update professor |
| DELETE | `/api/professeur/{id}` | Delete professor |
| GET | `/api/professeur/specialite/{id}` | Get professors by speciality |
| GET | `/api/professeur/filterByDate?dateDebut=2024-01-01&dateFin=2024-12-31` | Filter professors by hiring date |

### Speciality APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/specialite` | Get all specialities |
| GET | `/api/specialite/{id}` | Get speciality by ID |
| POST | `/api/specialite` | Create speciality |
| PUT | `/api/specialite/{id}` | Update speciality |
| DELETE | `/api/specialite/{id}` | Delete speciality |

## Sample Requests

### Create a Speciality

```bash
curl -X POST http://localhost:8080/api/specialite \
  -H "Content-Type: application/json" \
  -d '{
    "code": "CS",
    "libelle": "Computer Science"
  }'
```

### Create a Professor

```bash
curl -X POST http://localhost:8080/api/professeur \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Smith",
    "prenom": "John",
    "telephone": "+1-555-1000",
    "email": "john.smith@example.com",
    "dateEmbauche": "2024-01-15",
    "specialite": {
      "id": 1
    }
  }'
```

## Run Locally

### Prerequisites

- Java 17
- Maven 3.x
- MySQL 8.x

### Configure Database

The application uses environment variables with default values.

```properties
SERVER_PORT=8080
DB_HOST=localhost
DB_PORT=3307
DB_NAME=springboot3
DB_USERNAME=root
DB_PASSWORD=root
```

### Start the Application

```bash
mvn clean package
java -jar target/professor-management-api.jar
```

## Run with Docker Compose

Docker Compose starts both MySQL and the Spring Boot application.

```bash
docker compose up -d --build
```

Stop the containers:

```bash
docker compose down
```

Remove containers and database volume:

```bash
docker compose down -v
```

## Useful URLs

| Service | URL |
|---|---|
| Application | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/swagger-ui` |
| OpenAPI Docs | `http://localhost:8080/api-docs` |
| Health Check | `http://localhost:8080/actuator/health` |
| MySQL | `localhost:3307` |

## Jenkins CI/CD Pipeline

The Jenkins pipeline contains these stages:

1. **Checkout** - Pulls source code from GitHub
2. **OWASP Dependency Check** - Scans Maven dependencies for vulnerabilities
3. **Test and Package** - Runs tests and builds the Spring Boot JAR
4. **SonarQube Analysis** - Runs static code quality analysis
5. **Docker Build** - Builds and tags the Docker image
6. **Trivy Image Scan** - Scans the Docker image for vulnerabilities
7. **Docker Push** - Pushes the image to Docker Hub
8. **Deploy to Staging** - Deploys the app using Docker Compose

## Jenkins Setup Notes

Before running the pipeline, update these values in `Jenkinsfile`:

```groovy
DOCKER_NAMESPACE = 'replace-with-your-dockerhub-username'
```

Create these Jenkins credentials:

| Credential ID | Type | Purpose |
|---|---|---|
| `dockerhub-credentials` | Username with password/token | Docker Hub login |
| `sonarqube-token` | Secret text | SonarQube authentication token |

Configure these Jenkins tools:

| Tool Name | Purpose |
|---|---|
| `jdk17` | Java 17 |
| `maven3` | Maven build |
| `docker` | Docker CLI |
| `db-check` | OWASP Dependency Check installation |

Configure SonarQube server in Jenkins with this name:

```text
SonarQube
```

## Docker Commands

Build the image using the multi-stage Dockerfile:

```bash
docker build -t professor-management-api:latest .
```

Run the application container only:

```bash
docker run -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e DB_PORT=3307 \
  -e DB_NAME=springboot3 \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=root \
  professor-management-api:latest
```

## Security Improvements Added

- Removed hardcoded SonarQube token from Jenkinsfile
- Removed hardcoded GitHub repository checkout URL and changed checkout to `checkout scm`
- Removed hardcoded personal DockerHub username and replaced it with a configurable namespace
- Added environment-variable based database configuration
- Added validation for API request bodies
- Added `404 NOT_FOUND` responses when records are missing
- Added Docker Compose file for local/staging deployment
- Added Trivy image scan stage
- Added JaCoCo test coverage reporting

## Future Improvements

- Add JWT authentication and role-based authorization
- Add controller and service layer unit tests
- Add Kubernetes manifests or Helm chart
- Add Prometheus and Grafana monitoring
- Add centralized logging using ELK/OpenSearch
- Add GitHub Actions pipeline as an alternative to Jenkins

## Resume / LinkedIn Project Summary

Built a Spring Boot REST API for Professor and Speciality Management and implemented a Jenkins-based CI/CD pipeline to automate code checkout, Maven build, testing, OWASP dependency scanning, SonarQube code analysis, Docker image creation, Trivy image scanning, DockerHub publishing, and staging deployment using Docker Compose.

---

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=0:0F2027,50:2C5364,100:00C9FF&height=120&section=footer&text=Let's%20Connect&fontColor=ffffff&fontSize=32&fontAlignY=70" />
</p>

<h2 align="center">🤝 Connect With Me</h2>

<p align="center">
  <em>
    Thanks for visiting this project! I’m continuously building hands-on DevOps, Cloud, Automation, and AI-enabled engineering projects to improve real-world deployment, monitoring, and infrastructure skills.
  </em>
</p>

<p align="center">
  <img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&size=22&duration=2500&pause=800&color=00C9FF&center=true&vCenter=true&width=650&lines=DevOps+%7C+Cloud+%7C+Automation;CI%2FCD+%7C+Docker+%7C+Kubernetes+%7C+Terraform;Building+real-world+projects+one+commit+at+a+time" alt="Typing SVG" />
</p>

<p align="center">
  <a href="https://github.com/yugandhar99" target="_blank" rel="noopener noreferrer">
    <img src="https://img.shields.io/badge/GitHub-Follow-181717?style=flat&logo=github&logoColor=white" alt="GitHub" />
  </a>
  <a href="https://www.linkedin.com/in/yugandhar-devops" target="_blank" rel="noopener noreferrer">
    <img src="https://img.shields.io/badge/LinkedIn-Connect-0A66C2?style=flat&logo=linkedin&logoColor=white" alt="LinkedIn" />
  </a>
  <a href="https://yugandhar-portfolio-psi.vercel.app/" target="_blank" rel="noopener noreferrer">
    <img src="https://img.shields.io/badge/Portfolio-View%20My%20Work-FF5722?style=flat&logo=vercel&logoColor=white" alt="Portfolio" />
  </a>
  <a href="mailto:yugandharethamukkala1999@gmail.com">
    <img src="https://img.shields.io/badge/Email-Contact%20Me-D14836?style=flat&logo=gmail&logoColor=white" alt="Email" />
  </a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Focus-DevOps%20Engineering-blue?style=flat-square" />
  <img src="https://img.shields.io/badge/Cloud-AWS%20%7C%20Azure%20%7C%20GCP-orange?style=flat-square" />
  <img src="https://img.shields.io/badge/IaC-Terraform-purple?style=flat-square" />
  <img src="https://img.shields.io/badge/Containers-Docker%20%7C%20Kubernetes-2496ED?style=flat-square" />
</p>

---

<p align="center">
  ⭐ If this project added value, feel free to star the repository and connect with me!
</p>

<p align="center">
  <strong>Built with ❤️ using modern DevOps practices</strong>
</p>
