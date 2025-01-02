pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Démonstration du contrôle de version
                git branch: 'workshop',
                    url: 'https://github.com/abdelghani-hub/Citronix_Backend.git'
            }
        }

        stage('Build and SonarQube Analysis') {
            steps {
                echo "Running Maven build and SonarQube analysis..."
                withSonarQubeEnv('MySonarQubeServer') {
                    sh """
                    mvn clean package sonar:sonar
                    """
                }
            }
        }
        stage('Quality Gate Check') {
            steps {
                script {
                    echo "Waiting for SonarQube Quality Gate..."
                    def qualityGate = waitForQualityGate()
                    if (qualityGate.status != 'OK') {
                        error "Quality Gate failed ❌❌❌ Stopping the build."
                    }
                    echo "Quality Gate passed ✔️✔️✔️ Proceeding..."
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
    }

    post {
        success {
            mail to: 'aaittamghart8@gmail.com',
                 subject: "Success: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                 body: "The job passed. Check it here: ${env.BUILD_URL}"
        }
        failure {
            mail to: 'aaittamghart8@gmail.com',
                 subject: "Failed: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                 body: "The job failed. Check it here: ${env.BUILD_URL}"
        }
    }
}

/* pipeline {
    agent any
    environment {
        SONAR_PROJECT_KEY = "Hunters_League"
        SONAR_TOKEN = "sqa_4bd153196a6890aaebaa8e23d1e59842a5563a0b"
        SONAR_HOST_URL = "http://host.docker.internal:9001"
        DOCKER_IMAGE_NAME = "hunters_league"
        DOCKER_CONTAINER_NAME = "hunters_league_container"
        PG_DATA_PATH = "${env.POSTGRES_DATA_PATH}"
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build and SonarQube Analysis') {
            steps {
                echo "Running Maven build and SonarQube analysis..."
                withSonarQubeEnv('MySonarQubeServer') {
                    sh """
                    mvn clean package sonar:sonar \
                      -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                      -Dsonar.host.url=$SONAR_HOST_URL \
                      -Dsonar.login=$SONAR_TOKEN \
                      -Dsonar.ws.timeout=600
                    """
                }
            }
        }
        stage('Quality Gate Check') {
            steps {
                script {
                    echo "Waiting for SonarQube Quality Gate..."
                    def qualityGate = waitForQualityGate()
                    if (qualityGate.status != 'OK') {
                        error "Quality Gate failed! Stopping the build."
                    }
                    echo "Quality Gate passed! Proceeding..."
                }
            }
        }
        stage('Pre Deploy') {
            steps {
                script {
                    echo "Deploying Docker container..."
                    sh """
                    docker-compose down || true
                    docker rmi ${DOCKER_IMAGE_NAME}:latest || true
                    """
                }
            }
        }
        stage('Deploy'){
            steps{
                script {
                    echo "Deploying Docker container..."
                    sh """
                        docker-compose up -d
                    """
                }
            }
        }
    }
} */