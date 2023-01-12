pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'mvn clean install'
				bat 'cd ..'
				bat 'python scriptMaven.py'
            }
        }
    }
}
