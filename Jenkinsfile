pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "husainmk/email-generator"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Java Build') {
            steps {
                sh '''
                    rm -rf out
                    mkdir -p out
                    javac -d out src/emailgenerator/*.java
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    docker build -t $DOCKER_IMAGE:$BUILD_NUMBER .
                    docker tag $DOCKER_IMAGE:$BUILD_NUMBER $DOCKER_IMAGE:latest
                '''
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_TOKEN'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_TOKEN" | docker login \
                            -u "$DOCKER_USER" --password-stdin

                        docker push $DOCKER_IMAGE:$BUILD_NUMBER
                        docker push $DOCKER_IMAGE:latest

                        docker logout
                    '''
                }
            }
        }

        stage('Deploy on EC2') {
            steps {
                sh '''
                    docker rm -f email-generator || true

                    docker run -d \
                        --name email-generator \
                        --restart unless-stopped \
                        $DOCKER_IMAGE:latest

                    docker ps -a
                '''
            }
        }
    }

    post {
        success {
            echo 'Email Generator deployment successful!'
        }

        failure {
            echo 'Deployment failed. Check Jenkins console output.'
        }
    }
}
