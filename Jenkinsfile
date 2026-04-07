pipeline {
    agent {
        label 'prod'
    }

    environment {
        IMAGE_NAME = "babugudageri/calculator:${GIT_COMMIT}"
        KUBE_DEPLOYMENT = "calculator-deployment"
        KUBE_CONTAINER = "calculator"
    }

    tools {
        jdk 'java-17'
        maven 'maven'
    }

    stages {

        stage('Git-Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/basavarajgudageri07/springboot-Calculator-application.git'
            }
        }

        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t ${IMAGE_NAME} .'
            }
        }

        stage('DockerHub Login') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: 'Docker_Credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )]) {
                        sh '''
                        echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USER" --password-stdin
                        '''
                    }
                }
            }
        }

        stage('Docker Push') {
            steps {
                sh 'docker push ${IMAGE_NAME}'
            }
        }
        stage('Deploy to Kubernetes') {
           steps {
              sh '''
              sed -i "s|IMAGE_PLACEHOLDER|${IMAGE_NAME}|g" Deployment.yaml
              kubectl apply -f Deployment.yaml
              kubectl apply -f Svc.yaml
              '''
            }
        }
        
    }
}
post {

    success {
        echo "✅ Deployment & Service Applied Successfully!"
        sh '''
        echo "Pods:"
        kubectl get pods

        echo "Services:"
        kubectl get svc
        '''
    }

    failure {
        echo "❌ Deployment Failed!"
        sh '''
        kubectl get pods
        kubectl get svc
        '''
    }

    always {
        echo "📌 Pipeline execution completed"
    }

}
