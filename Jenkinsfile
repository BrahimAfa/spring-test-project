pipeline {
    agent any
    stages {

        stage ("Initialize Environment") {
            steps {
                initializeBuild()
            }
        }
        stage ("Build Application") {
            steps {
                buildApplication()
            }

        }
        stage ("Generate Sonar Report") {
           steps{
                runSonarScan()
           }
        }
        stage("Cleanup") {
            steps {
                cleanup()
            }
        }
        stage ("Docker Build") {
            steps {
                dockerBuild()
            }
        }
    }
}

def codeCheckout() {
    sh "pwd"
    sh "ls"
}
def buildApplication() {
    try {
        sh 'mvn clean install'
    }
    catch (err) {
        echo "Build Application Failed"
        throw err
    }

}

def runSonarScan() {
     try {
        sh "mvn clean verify sonar:sonar -Dsonar.projectKey=ds-ensa-test -Dsonar.host.url=http://46.101.76.79:9000 -Dsonar.login=760330b521b7dec8932f5925af9012b7c85c6990"
    }
    catch (err) {
        echo "Run Sonar Scan Failed"
        throw err
    }

}

 def initializeBuild() {
    try {
        sh "docker-compose -f docker-compose-test.yml up -d"
    }
    catch (err) {
        echo "Initialize Environment"
        throw err
    }
 }

 def cleanup() {
    try {
        sh "docker-compose -f docker-compose-test.yml down -v"
    }
    catch (err) {
        echo "cleanup failed"
        throw err
    }
 }

def dockerBuild(){
   try {
        sh "docker-compose build app"
        sh "docker-compose up -d"
    }
    catch (err) {
        echo "Docker Build Failed"
        throw err
    }
}
