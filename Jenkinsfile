pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'mvn clean install'
		bat 'python ../scriptMaven.py'
	     	archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true 
            }
        }
    }
}
