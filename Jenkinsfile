def codeCheckout() {
    cleanWs()
    checkout scm
    success("Workspace clean and code checkout.")
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
    withSonarQubeEnv(credentialsId: 'Sonarqube') {
        withMaven(maven: 'Maven') {
            sh"""
            mvn sonar:sonar -Dsonar.projectKey=adexam -Dsonar.organization=javid141moazan -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=07ba28e79449a72466fbb76370d4d178c7f90363
            """
        }
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

node('master') {
    timeout (time: 1, unit: 'HOURS') {
        ansiColor('xterm') {
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
}