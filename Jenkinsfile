pipeline {
    agent any
    stages {
        stage('Build') {
            steps{
                bat 'mvn clean install package'
            }
        }
        stage('Test') {
                steps{
                        bat '.mvn test'
                    }
                }

    }
}