pipeline {
    stages {
        stage('Clone') {
            steps {
                checkout scm
            }
        }
        stage('build') {
                    steps {
                        sh 'mvn clean -U deploy'
                    }
         }
         
         stage('docker buld') {
                    steps {
                        sh 'docker build'
                    }
         }
    }
}