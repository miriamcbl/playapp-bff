pipeline {
    agent any
    
    environment{
        SONAR_TOKEN = credentials('sonarcloud-token')
        DOCKER_HUB_USERNAME = credentials('docker-hub-username')
        DOCKER_HUB_PASSWORD = credentials('docker-hub-token')
        DOCKER_HUB_REPOSITORY = 'playapp_back'
        DOCKER_IMAGE_TAG = 'latest'
    }
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "MAVEN_HOME"
    }

    stages {
        stage('Checkout') {
            steps {
                // Clonar el repositorio de GitHub
                git branch: 'main', url: 'https://github.com/miriamcbl/playapp-bff.git'
            }
        }
        stage('Maven Build') {
            steps {
                script {
                    // Aquí se llama al job de Jenkins 'playapp' para hacer la build ya declarada ahí
                    // Por ejemplo:
                    //build job: 'playapp', wait: true
                    sh 'mvn clean package'
                    sh 'mvn install'
                }
            }
        }
        stage('SonarCloud Verify') {
            steps {
                // Verificar el estado del Quality Gate
                echo 'Verifying Sonar quailty gate status'
                script {
                    def qualityGateUrl = "https://sonarcloud.io/api/qualitygates/project_status?projectKey=miriamcbl_playapp-bff"
                    def response = httpRequest(
                        url: qualityGateUrl,
                        validResponseCodes: '200'
                    )
                    
                    def status = readJSON text: response.content
                    
                    if (status.projectStatus.status == 'ERROR') {
                        def conditions = status.projectStatus.conditions
                        def errorConditions = conditions.findAll { it.status == 'ERROR' }
                        
                        def errorMessages = errorConditions.collect { "- ${it.metricKey}: ${it.status}" }
                        def errorMessage = "Quality Gate failed with the following conditions:\n${errorMessages.join('\n')}"
                        
                        error errorMessage
                    }
                }
                
            }
        }
        stage('Publish Version') {
            steps {
                script {
                    echo 'Publishing new version and creating and pushing tag in GitHub'
                    //def version = params.VERSION
                    // Obtener la versión actual del proyecto
                    def currentVersion = getCurrentVersion()
                    // Incrementar la versión
                    def version = incrementVersion(currentVersion)
                    // Actualizar la versión en el archivo pom.xml
                    sh "mvn versions:set -DnewVersion=${version}"
                    // Agregar los archivos al área de preparación
                    sh "git add ."
                    // Realizar commit
                    sh "git commit -am 'Jenkins: actualización de la versión a ${version}'"
                    // Configurar la rama ascendente antes de realizar el push
                    withCredentials([string(credentialsId: 'personal-access-token-github', variable: 'TOKEN')]) {
                        def gitPushCommand = "git push --set-upstream https://$TOKEN@github.com/miriamcbl/playapp-bff.git main"
                        def pushResult = sh(script: gitPushCommand, returnStatus: true)
                        if (pushResult == 0) {
                            echo "Push successful"
                        } else {
                            error "Failed to push changes"
                        }
                    }
                    // Crear tag con la versión
                    sh "git tag -a v${version} -m 'Versión ${version}'"
                    def gitPushCommand = 'git push --tags'
                    def pushResult = sh(script: gitPushCommand, returnStatus: true)
                    if (pushResult == 0) {
                            echo "Push successful"
                    } else {
                            error "Failed to push changes"
                    }
                }
            }
        }
        stage("Build Docker Image"){
            steps {
                script {
                    echo 'Building and puhsing docker image - Playapp'
                    sh 'ls -l target/*jar'
                    sh "docker build --build-arg JAR_FILE=target/*.jar -t ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG} ."
                    sh "docker tag ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG} ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG}"
                    sh "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                    sh "docker push ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG}"
                }
            }
        }
        stage("Deploy") {
            steps {
                script {
                    echo 'Deploying image to EC2 - Playapp'
                    def dockerRunCmd = "docker run -p 8080:8080 -d ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG}"
                    sshagent(['ssh-keys-rsa']) {
                         // Inicia sesión en Docker Hub
                        //sh "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                        //sh "docker pull docker.io/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG}"
                        //sh "docker save ${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG} | ssh -o StrictHostKeyChecking=no ec2-user@13.39.48.168 'docker load'"
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@15.188.57.129 '${dockerRunCmd}'"
                    }
                }
            }
        }
    }
}

// Función para obtener la versión actual del proyecto desde el archivo pom.xml
def getCurrentVersion() {
    def currentVersion = sh(script: 'mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true).trim()
    return currentVersion
}
// Función para incrementar la versión en uno
def incrementVersion(currentVersion) {
    // Obtener los componentes de la versión actual
    def matcher = (currentVersion =~ /(\d+)\.(\d+)\.(\d+)/)
    def x1 = matcher[0][1].toInteger()
    def x2 = matcher[0][2].toInteger()
    def x3 = matcher[0][3].toInteger()

    // Incrementar los componentes según la regla
    if (x3 < 10) {
        x3++
    } else {
        x3 = 1
        if (x2 < 10) {
            x2++
        } else {
            x2 = 1
            if (x1 < 100) {
                x1++
            } else {
                error "La versión ha alcanzado el límite máximo (100.10.10)"
            }
        }
    }
    // Construir la versión incrementada
    return "${x1}.${x2}.${x3}"
}