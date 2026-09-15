pipeline {

    agent any

    environment {
        APP_NAME = 'account-service'
        IMAGE_NAME = 'account-service'
        APP_PORT = '8086'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    docker build \
                        -t ${IMAGE_NAME}:${BUILD_NUMBER} .
                '''
            }
        }

        stage('Stop Existing Container') {
            steps {
                sh '''
                    docker stop ${APP_NAME} || true
                    docker rm ${APP_NAME} || true
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker run -d \
                        --name ${APP_NAME} \
                        -p ${APP_PORT}:8080 \
                        ${IMAGE_NAME}:${BUILD_NUMBER}
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                    sleep 10
                    curl --fail http://localhost:${APP_PORT}/actuator/health
                '''
            }
        }
    }

    post {
        success {
            echo 'Spring Boot application deployed successfully'
        }

        failure {
            echo 'Deployment failed'
        }
    }
}