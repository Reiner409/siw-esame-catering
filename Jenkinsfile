pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
            bat 'mvn clean install'
	     	archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
//             environmentDashboard(addColumns: true, buildJob: '', buildNumber: '1.5', componentName: 'SPIL', data: [[columnName: 'Col1', contents: 'Column 1 contents'], [columnName: 'Col1', contents: 'Column 2 contents']], nameOfEnv: 'CAT', packageName: 'SPIL_PACKAGE')
            }
        }
    }
}
