pipeline {
    agent any
    environment {
        HOME = "${env.WORKSPACE}"
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/HenryTheSecond/homestaywithme.git'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        
        success {
            echo 'Build successful!'
        }
        
        unstable {
            echo 'Build unstable.'
        }
        
        failure {
            echo 'Build failed!'
        }
    }
}
