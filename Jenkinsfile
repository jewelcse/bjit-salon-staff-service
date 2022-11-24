pipeline {
    agent any
    stages {
        stage('Build') {
            steps{
                sh './mvnw clean install package'
            }
        }
        stage('Test') {
                    steps{
                        sh './mvnw test'
                    }
                }

    }
}