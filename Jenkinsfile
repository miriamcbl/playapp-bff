pipeline {
    agent any
    
    // Parametrización de la pipeline
    parameters {
        string(name: 'VERSION', defaultValue: '1.0.0', description: 'Introduzca la versión')
    }    
    environment{
    	  // Declaracion de variables de entorno
        SONAR_TOKEN = credentials('sonarcloud-token')
        DOCKER_HUB_USERNAME = credentials('docker-hub-username')
        DOCKER_HUB_PASSWORD = credentials('docker-hub-token')
        DOCKER_HUB_REPOSITORY = 'playapp_back'
        DOCKER_IMAGE_TAG = 'latest'
        PLAYAPP_EC2 = credentials('playapp_ec2')
        OPENAI_API_KEY = credentials('openai-api-key')
        ACCUWEATHER_API_KEY = credentials('accuweather-api-key')        
    }
    tools {
        // Utiliza maven instalado en la maquina
        maven "MAVEN_HOME"
    }

    stages {
        stage('Checkout') {
            steps {
           	echo 'Cloning git repo'
                // Clonar el repositorio de GitHub
                git branch: 'main', url: 'https://github.com/miriamcbl/playapp-bff.git'
            }
        }
        stage('Maven Build') {
            steps {
                script {
		    		echo 'Building project with mvn'
                    sh 'mvn verify'
                }
            }
        }
        stage('SonarCloud Verify') {
            steps {
                // Verificar el estado del Quality Gate                
                script {
        	    echo 'Verifying Sonar quailty gate status'
                    def qualityGateUrl = "https://sonarcloud.io/api/qualitygates/project_status?projectKey=miriamcbl_playapp-bff"
                    // llamada http especificando el codigo que se considera valido
                    def response = httpRequest(url: qualityGateUrl, validResponseCodes: '200')
                    // parse a json
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
		    		//sh "git fetch"
                    //sh "git tag -d \$(git tag -l)"
                    def version = params.VERSION
                    // Actualizar la versión en el archivo pom.xml
                    sh "mvn versions:set -DnewVersion=${version}"
                    // Agregar los archivos al área de preparación
                    sh "git add pom.xml"
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
                    sh "git tag -a ${version} -m 'Versión ${version}'"
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
        stage('Security properties'){
        	steps {
        		script {
        			echo 'Injecting the sensitive properties'		
		            def propertiesDir = "${WORKSPACE}/src/main/resources/application.properties"
					sh "chmod g+w ${propertiesDir}"
		            // Se lee el properties
		            def propertiesFile = readFile(propertiesDir)
		
		            // Se actualiza con las secrets 
		            propertiesFile = propertiesFile.replaceAll('spring.ai.openai.api-key: your_api_key', "spring.ai.openai.api-key: ${OPENAI_API_KEY}")
		            propertiesFile = propertiesFile.replaceAll('accuweather.apikey: your_api_key', "accuweather.apikey: ${ACCUWEATHER_API_KEY}")
		
		            // se escribe todo
		            writeFile file: propertiesDir, text: propertiesFile
		            sh '''
	                    cd ${WORKSPACE}/target
	                    rm *.jar
	                    rm *.jar.original
                	'''
		            sh 'mvn install'
        		}
        	}
        }
        stage("Build Docker Image"){
            steps {
                script {
                    echo 'Building and pushing docker image - Playapp'
                    sh 'ls -l target/*jar'
                    sh "docker build  --build-arg JAR_FILE=target/*.jar -t ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG} ."
                    //sh "docker tag ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG} ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG}"
                    sh "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                    sh "docker push ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG}"
                }
            }
        }
        stage("Deploy") {
            steps {
                script {
                    echo 'Deploying image to EC2 - Playapp'
                    def dockerRunCmd = "docker run --name playapp_backend -p 8090:8080 -d ${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG}"
                    def dockerLoginCmd = "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                    def dockerPullCmd = "docker pull docker.io/${DOCKER_HUB_USERNAME}/${DOCKER_HUB_REPOSITORY}:${DOCKER_IMAGE_TAG}"
                    def dockerStopCmd = "docker stop playapp_backend"
                    def dockerRmvCmd = "docker rm playapp_backend"
                    // conexion ssh al server playapp
                    sshagent(['ssh-keys-rsa']) {
                    	echo "Conectados a server playapp"
                        // Inicia sesión en Docker Hub
                        sh "ssh -o StrictHostKeyChecking=no ${PLAYAPP_EC2} '${dockerLoginCmd}'"
                        // Baja los últimos cambios subidos
                        sh "ssh -o StrictHostKeyChecking=no ${PLAYAPP_EC2} '${dockerPullCmd}'"
                        // Verificar si el contenedor está en ejecución antes de detenerlo
                        def checkContainerRunningCmd = "docker ps --filter name=playapp_backend --format {{.Names}}"
                        def checkContainerCreatedCmd = "docker ps -a --filter name=playapp_backend --format {{.Names}}"   
                        def checkExistsContainerRunning = sh(script: "ssh -o StrictHostKeyChecking=no ${PLAYAPP_EC2} '${checkContainerRunningCmd}'", returnStdout: true).trim()
                        def checkExistsContainerCreated = sh(script: "ssh -o StrictHostKeyChecking=no ${PLAYAPP_EC2} '${checkContainerCreatedCmd}'", returnStdout: true).trim()
                        if (checkExistsContainerRunning == 'playapp_backend') {
                            echo "Existe contenedor activo, se procede parar"
                            sh "ssh -o StrictHostKeyChecking=no ${PLAYAPP_EC2} '${dockerStopCmd}'"
                        }
                        if (checkExistsContainerCreated == 'playapp_backend'){
                        	echo "Existe contenedor creado, se elimina"
                            sh "ssh -o StrictHostKeyChecking=no ${PLAYAPP_EC2} '${dockerRmvCmd}'"
                        }
                        sh "ssh -o StrictHostKeyChecking=no ${PLAYAPP_EC2} '${dockerRunCmd}'"
                    }
                }
            }
        }
    }
}
