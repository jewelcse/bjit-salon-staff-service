pipeline {
    agent any
    stages {
        stage('Build') {
            steps{
                bat './mvnw clean install package'
            }
        }
        stage('Test') {
                steps{
                        bat './mvnw test'
                    }
                }

    }
}