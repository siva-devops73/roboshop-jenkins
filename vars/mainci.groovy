def call() {
    node('workstation') {

        stage('code checkout') {
            if(env.TAG_NAME ==~ ".*") {
                env.gitbrname = "refs/tags/${env.TAG_NAME}"
            } else {
                env.gitbrname = "refs/tags/${env.BRANCH_NAME}"
            }
            checkout scm: [$class: 'GitSCM', userRemoteConfigs: [[url: 'https://github.com/siva-devops73/${envcomponent}']], branches: [[name: gitbrname]]], poll: false

        }


        if(env.cibuild == "java") {
            stage('Build') {
                sh 'mvn package'
            }
        }

        stage('Unit Tests') {
            echo 'Unit Tests'
            sh 'ls -ltr'
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
                if (env.cibuild == "nginx") {
                    sh 'zip -r ${component}-${TAG_NAME}.zip *'
                }
            }
        }

    }

}

