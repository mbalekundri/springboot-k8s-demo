# A) Docker commands
# Create an account in https://hub.docker.com/ & docker login
1. docker version  (If not working then login to docker hub by docker login)
2. docker build -t rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT .
   docker build -t rbalekundri/springboot-k8s-demo:1.1 .
3. docker run -it -p 8080:8080 rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT
   docker run -d -p 8080:8080 rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT
   docker run -d -p 8080:8080 rbalekundri/springboot-k8s-demo:1.1
4. docker ps -a
5. docker logs <container-id>
   docker logs -f <container-id>
6. docker stop <container-id>
7. docker rm <container-id>
8. docker rmi rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT
9. docker push rbalekundri/springboot-k8s-demo:1.1  (docker push rbalekundri/springboot-k8s-demo:tagname)
10. docker run -d -e APP_ENV=prod -p 8080:8080 rbalekundri/springboot-k8s-demo:1.4
    docker push rbalekundri/springboot-k8s-demo:1.4

# B) K8s commands
# Reference: https://kubernetes.io/docs/reference/kubectl/cheatsheet/
# Reference: https://kubernetes.io/docs/tutorials/kubernetes-basics/
1. kubectl version
2. kubectl apply -f k8s-manifest.yml
3. kubectl get deployments (takes time)
4. kubectl get pods  (takes time)
5. kubectl get svc
6. kubectl get namespaces
7. kubectl get namespaces <namespace_name>
8. kubectl get pods -n <namespace_name>
9. kubectl delete -f k8s-manifest.yml

