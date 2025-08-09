// Define the entire pipeline
pipeline {
    // Define the agent where the pipeline will run
    // 'any' means Jenkins will pick any available agent
    agent any
    tools {
            maven 'Maven 3.9.11'
        }


    // Define environment variables, especially for sensitive data using Jenkins Credentials
//     environment {
//         // Replace 'deploy-server-ssh-key' with the ID of your SSH credential in Jenkins
//         // This makes the private key available to the SSH Agent
//         DEPLOY_SERVER_SSH_CREDENTIALS = credentials('deploy-server-ssh-key')
//         DEPLOY_USER = 'deployuser' // The user on your deployment server
//         DEPLOY_HOST = 'your_server_ip_or_domain' // Your deployment server's IP or domain
//
//         // Define paths on the remote server
//         BACKEND_REMOTE_DIR = '/opt/portfolio-backend'
//         FRONTEND_REMOTE_DIR = '/var/www/portfolio-frontend'
//         BACKEND_JAR_NAME = 'portfolio-backend-0.0.1-SNAPSHOT.jar' // Match your actual JAR name
//         BACKEND_PROPERTIES_NAME = 'application.properties' // Ensure this is also copied
//     }

    // Define the stages of your CI/CD pipeline
    stages {
        // Stage 1: Checkout Source Code
        stage('Checkout') {
            steps {
                script {
                    // Checkout your Git repository
                    // Replace 'main' with your branch name
                    // If your repo is private, replace 'your-git-credentials' with the ID of your Git credential
                    git branch: 'main', url: 'https://github.com/althafshaik0992/My-Website.git'
                }
            }
        }

        // Stage 2: Build Spring Boot Backend
        stage('Build Backend') {
            steps {
                script {

                                             // Build the Spring Boot application using Maven
                                             // The 'bat' step is used here for a Windows-based agent.
                     bat "mvn clean install -DskipTests"
                }
            }
        }
    }

    // Post-build actions (optional)
    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Build successful!'
            // Add notifications here (e.g., email, Slack)
        }
        failure {
            echo 'Build failed!'
            // Add notifications here
        }
    }
}