# A Docker commands
# Create an account in https://hub.docker.com/
1. docker version  (If not working then login to docker hub by docker login)
2. docker build -t rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT .
   docker build -t rbalekundri/springboot-k8s-demo:1.1 .
3. docker run -it -p 8080:8080 rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT
   docker run -d -p 8080:8080 rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT
   docker run -d -p 8080:8080 rbalekundri/springboot-k8s-demo:1.1
4. docker ps -a
5. docker stop <container-id>
6. docker rm <container-id>
7. docker rmi rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT

