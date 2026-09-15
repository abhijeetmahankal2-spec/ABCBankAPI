pipeline {

    agent any

    environment {
        APP_NAME = 'account-service'
        IMAGE_NAME = 'account-service'
        APP_PORT = '8081'
    }

    stages {

        stage('Build & Test') {
            steps {
                bat 'gradlew.bat clean build'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:%BUILD_NUMBER% .'
            }
        }

        stage('Stop Existing Container') {
            steps {
                bat 'docker stop %APP_NAME% || exit /b 0'
                bat 'docker rm %APP_NAME% || exit /b 0'
            }
        }

        stage('Deploy') {
            steps {
                bat 'docker run -d --name %APP_NAME% -p %APP_PORT%:8080 %IMAGE_NAME%:%BUILD_NUMBER%'
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'docker ps --filter "name=%APP_NAME%"'
            }
        }
    }

    post {
        success {
            echo 'Deployment successful'
        }

        failure {
            echo 'Deployment failed'
        }
    }
}