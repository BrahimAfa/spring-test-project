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
        errorMsg("ERROR: ${err.message}!")
    }

}

def runSonarScan() {
        withMaven(maven: 'Maven') {
            sh"""
            mvn clean verify sonar:sonar -Dsonar.projectKey=ds-ensa-test -Dsonar.host.url=http://46.101.76.79:9000 -Dsonar.login=760330b521b7dec8932f5925af9012b7c85c6990
            """
        }
}

def errorMsg(String text) {
    echo "\u001B[1m\033[31m${text}\u001B[m"
    currentBuild.result = "FAILED"
    error(text)
}

def success(String text) {
    echo "\u001B[1m\033[32m${text}\u001B[m"
}

pipeline {
    agent { label 'dev' }
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
