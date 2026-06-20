# GitHub Upload Steps

## 1. Create a new GitHub repository

Recommended repository name:

```text
spring-boot-jenkins-cicd-pipeline
```

Recommended GitHub description:

```text
Spring Boot REST API with Jenkins CI/CD, Maven, Docker, SonarQube, OWASP Dependency Check, Trivy, and Docker Compose deployment.
```

## 2. Initialize Git locally

```bash
git init
git add .
git commit -m "Initial commit - Spring Boot Jenkins CI/CD project"
```

## 3. Connect to GitHub

Replace `<your-github-username>` and `<repo-name>` with your details.

```bash
git branch -M main
git remote add origin https://github.com/<your-github-username>/<repo-name>.git
git push -u origin main
```

## 4. Update Jenkinsfile before running pipeline

Open `Jenkinsfile` and replace:

```groovy
DOCKER_NAMESPACE = 'replace-with-your-dockerhub-username'
```

with your Docker Hub username.

## 5. Add Jenkins credentials

Create these credentials in Jenkins:

- `dockerhub-credentials`
- `sonarqube-token`

## 6. Run locally first

```bash
docker compose up -d --build
```

Then open:

```text
http://localhost:8080/swagger-ui
```
