def call() {
    node('workstation') {

        if(env.cibuild == "java") {
            stage('Build') {
                sh 'mvn package'
            }
        }
        if(env.cibuild == "golang") {
            stage('Build') {
                sh 'go get'
                sh 'go build'
            }
        }


        stage('Unit Tests') {
            echo 'Unit Tests'
        }

        stage('Code Analysis') {
            echo 'Code Analysis'
            // sh 'sonar-scanner -Dsonar.host.url=http://172.31.30.244:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=cart'
        }

        stage('Security Scans') {
            echo 'Security Scans'
        }


        if(env.TAG_NAME ==~ ".*") {
            stage('Publish A Article') {
                echo 'Publish A Article'
                }
            }

    }

}
