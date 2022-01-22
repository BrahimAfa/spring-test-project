pipeline {
    agent {
        label 'linux'
    }
    stages {
        stage ("Code Checkout") {
            codeCheckout()
        }
        stage ("Build Application") {
            build()
        }
        stage ("Generate Sonar Report") {
            runSonarScan()
        }
    }
}

def codeCheckout() {
    sh "pwd"
    sh "ls"
}

def build() {
    try {
        withMaven(maven: 'Maven') {
            sh 'mvn clean install'
        }
    }
    catch (err) {
        echo "Build failed"
        throw err
    }

}

def runSonarScan() {
        withMaven(maven: 'Maven') {
            sh"""
            mvn clean verify sonar:sonar -Dsonar.projectKey=ds-ensa-test -Dsonar.host.url=http://46.101.76.79:9000 -Dsonar.login=760330b521b7dec8932f5925af9012b7c85c6990
            """
        }
}
