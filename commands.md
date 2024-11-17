# Docker commands
# Create an account in https://hub.docker.com/
1. docker version  (If not working then login to docker hub by docker login)
2. docker build -t rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT .
3. docker run -it -p 8080:8080 rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT