pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
		sh 'python ../scriptMaven.py'
	     	archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true 
            }
        }
    }
}
