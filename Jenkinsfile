// Define the agent where the pipeline will run
// 'any' means Jenkins will pick any available agent
agent any

// Define environment variables, especially for sensitive data using Jenkins Credentials
environment {
    // Replace 'deploy-server-ssh-key' with the ID of your SSH credential in Jenkins
    // This makes the private key available to the SSH Agent
    DEPLOY_SERVER_SSH_CREDENTIALS = credentials('deploy-server-ssh-key')
    DEPLOY_USER = 'deployuser' // The user on your deployment server
    DEPLOY_HOST = 'your_server_ip_or_domain' // Your deployment server's IP or domain

    // Define paths on the remote server
    BACKEND_REMOTE_DIR = '/opt/portfolio-backend'
    FRONTEND_REMOTE_DIR = '/var/www/portfolio-frontend'
    BACKEND_JAR_NAME = 'portfolio-backend-0.0.1-SNAPSHOT.jar' // Match your actual JAR name
    BACKEND_PROPERTIES_NAME = 'application.properties' // Ensure this is also copied
}

// Define the stages of your CI/CD pipeline
stages {
    // Stage 1: Checkout Source Code
    stage('Checkout') {
        steps {
            script {
                // Checkout your Git repository
                // Replace 'main' with your branch name
                // If your repo is private, replace 'your-git-credentials' with the ID of your Git credential
                git branch: 'main', url: 'https://github.com/althafshaik0992/My-Website.git', credentialsId: 'your-git-credentials'
            }
        }
    }

    // Stage 2: Build Spring Boot Backend
    stage('Build Backend') {
        steps {
            script {
                // Build the Spring Boot application using Maven
                // -DskipTests is used here to speed up the build, but you should run tests in a dedicated 'Test' stage
                sh "mvn clean install -DskipTests"
            }
        }
    }

    // Stage 3: Deploy Backend
    stage('Deploy Backend') {
        steps {
            // Use sshagent to securely use your SSH private key
            // The ID 'deploy-server-ssh-key' must match the Jenkins credential ID
            sshagent(credentials: ['deploy-server-ssh-key']) {
                script {
                    echo "Deploying backend to ${DEPLOY_HOST}..."

                    // 1. Stop the existing backend service
                    sh "ssh ${DEPLOY_USER}@${DEPLOY_HOST} 'sudo systemctl stop portfolio-backend.service || true'" // '|| true' prevents failure if service isn't running

                    // 2. Remove old JAR and properties (optional, but good for clean deployments)
                    sh "ssh ${DEPLOY_USER}@${DEPLOY_HOST} 'rm -f ${BACKEND_REMOTE_DIR}/${BACKEND_JAR_NAME}'"
                    sh "ssh ${DEPLOY_USER}@${DEPLOY_HOST} 'rm -f ${BACKEND_REMOTE_DIR}/${BACKEND_PROPERTIES_NAME}'"


                    // 3. Copy the new JAR to the remote server
                    sh "scp target/${BACKEND_JAR_NAME} ${DEPLOY_USER}@${DEPLOY_HOST}:${BACKEND_REMOTE_DIR}/"
                    // Copy application.properties as well
                    sh "scp src/main/resources/${BACKEND_PROPERTIES_NAME} ${DEPLOY_USER}@${DEPLOY_HOST}:${BACKEND_REMOTE_DIR}/"

                    // 4. Start the backend service
                    sh "ssh ${DEPLOY_USER}@${DEPLOY_HOST} 'sudo systemctl start portfolio-backend.service'"
                    sh "ssh ${DEPLOY_USER}@${DEPLOY_HOST} 'sudo systemctl status portfolio-backend.service'" // Check status
                    echo "Backend deployed and started."
                }
            }
        }
    }

    // Stage 4: Deploy Frontend
    stage('Deploy Frontend') {
        steps {
            sshagent(credentials: ['deploy-server-ssh-key']) {
                script {
                    echo "Deploying frontend to ${DEPLOY_HOST}..."

                    // 1. Clean old frontend files (optional, but good for clean deployments)
                    sh "ssh ${DEPLOY_USER}@${DEPLOY_HOST} 'sudo rm -rf ${FRONTEND_REMOTE_DIR}/*'"

                    // 2. Copy all frontend files (your index.html, CSS, JS)
                    // Assuming index.html is in the root of your repo
                    sh "scp -r index.html ${DEPLOY_USER}@${DEPLOY_HOST}:${FRONTEND_REMOTE_DIR}/"
                    // If you have other static assets (css, js folders), copy them too:
                    // sh "scp -r css/ ${DEPLOY_USER}@${DEPLOY_HOST}:${FRONTEND_REMOTE_DIR}/"
                    // sh "scp -r js/ ${DEPLOY_USER}@${DEPLOY_HOST}:${FRONTEND_REMOTE_DIR}/"
                    // Note: For a more complex frontend (React, Vue), you'd build it here and copy the 'build' or 'dist' folder.

                    // 3. Reload Nginx to pick up new files
                    sh "ssh ${DEPLOY_USER}@${DEPLOY_HOST} 'sudo systemctl reload nginx'"
                    echo "Frontend deployed and Nginx reloaded."
                }
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
        echo 'Deployment successful!'
        // Add notifications here (e.g., email, Slack)
    }
    failure {
        echo 'Deployment failed!'
        // Add notifications here
    }
}
