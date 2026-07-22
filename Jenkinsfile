pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven3'
    }
 
    environment {
        APP_NAME = 'professor-management-api'
        DOCKER_NAMESPACE = 'replace-with-your-dockerhub-username'
        DOCKER_IMAGE = "${DOCKER_NAMESPACE}/${APP_NAME}"
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        SONARQUBE_SERVER = 'SonarQube'
        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials'
        SONAR_TOKEN_ID = 'sonarqube-token'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('OWASP Dependency Check') {
            steps {
                dependencyCheck additionalArguments: '--scan ./ --format HTML --format XML', odcInstallation: 'db-check'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }

        stage('Test and Package') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv("${SONARQUBE_SERVER}") {
                    withCredentials([string(credentialsId: "${SONAR_TOKEN_ID}", variable: 'SONAR_TOKEN')]) {
                        sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN'
                    }
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} -t ${DOCKER_IMAGE}:latest -f Dockerfile.final .'
            }
        }

        stage('Trivy Image Scan') {
            steps {
                sh 'trivy image --severity HIGH,CRITICAL --exit-code 0 ${DOCKER_IMAGE}:${IMAGE_TAG}'
            }
        }

        stage('Docker Push') {
            steps {
                withDockerRegistry(credentialsId: "${DOCKER_CREDENTIALS_ID}", toolName: 'docker') {
                    sh 'docker push ${DOCKER_IMAGE}:${IMAGE_TAG}'
                    sh 'docker push ${DOCKER_IMAGE}:latest'
                }
            }
        }

        stage('Deploy to Staging') {
            steps {
                sh 'docker compose up -d --build'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
        }
        success {
            echo "Pipeline completed successfully for ${APP_NAME}:${IMAGE_TAG}"
        }
        failure {
            echo "Pipeline failed. Check Jenkins console logs for details."
        }
    }
}
