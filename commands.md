# A) Docker commands
# Create an account in https://hub.docker.com/ & docker login
# apt -y update and apt install -y
2. docker version  (If not working then login to docker hub by docker login)
2. docker build -t rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT .
   docker build -t rbalekundri/springboot-k8s-demo:1.1 .
   docker build -t rbalekundri/springboot-k8s-demo:latest .
   docker build -t rbalekundri/springboot-k8s-demo:version-1.0 .

3. docker run -it -p 8080:8080 rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT
   docker run -d -p 8080:8080 rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT
   docker run -d -p 8080:8080 rbalekundri/springboot-k8s-demo:1.1
   # To give a container an explicit name using the --name flag, use the run command
   docker run -d -p 8080:8080 rbalekundri/springboot-k8s-demo:1.1 --name ROHIT_K8s
4. docker ps -a
5. docker logs <container-id>
   docker logs -f <container-id>
6. docker stop <container-id>
7. docker pause <container-id>
8. docker restart happy_wilbur
8. docker kill <container-id>
7. docker rm <container-id>
8. docker rmi rbalekundri/springboot-k8s-demo:1.0.0-SNAPSHOT
   # You can't remove an image if a container is still using the image
9. docker push rbalekundri/springboot-k8s-demo:1.1  (docker push rbalekundri/springboot-k8s-demo:tagname)
10. docker run -d -e APP_ENV=prod -p 8080:8080 rbalekundri/springboot-k8s-demo:1.4
    docker push rbalekundri/springboot-k8s-demo:1.4
# Docker Volumes
11. docker volume create (Docker creates and manages the new volume by running)
12. docker stats

# B) K8s commands
# Kubernetes clusters
# Kubernetes is based on clusters. Instead of having a single virtual machine (VM), it uses several machines working as one. 
# These VMs are called nodes. Kubernetes is a cluster-based orchestrator. It provides your application with several benefits, 
# such as availability, monitoring, scaling, and rolling updates.

# Reference: https://kubernetes.io/docs/reference/kubectl/cheatsheet/
# Reference: https://kubernetes.io/docs/tutorials/kubernetes-basics/
1. kubectl version
2. kubectl apply -f k8s-manifest.yml
3. kubectl get deployments (takes time)
4. kubectl get pods  -o wide (takes time)
   wget <EXTERNAL_POD_IP>
5. sudo kubectl get nodes -o wide (To fetch extra information from the API server)
6. kubectl get svc -o wide
   sudo kubectl get services -o wide --all-namespaces
7. kubectl get namespaces
8. kubectl get namespaces <namespace_name>
9. kubectl get pods -n <namespace_name>
10. To scale the number of replicas in your deployment, run the kubectl scale command. 
    You specify the number of replicas you need and the name of the deployment.
    kubectl scale --replicas=3 deployments/nginx  (Use sudo kubectl get deployments  to get deployments name)
    After that execute to check no of pods: kubectl get pods -o wide
    Dynamically scaling container count up or down
    kubectl scale --replicas=1 deployments/nginx
11. kubectl delete -f k8s-manifest.yml

#  C) Azure Kubernetes cluster create using CLI
#  Reference: 
1. az aks install-cli
2. az login
3. az account set --subscription $subscriptionId
3. az group create --name myResourceGroup --location southindia    northeurope
4. az aks create --resource-group myResourceGroup --name myAKSCluster --node-count 2 --enable-addons monitoring --generate-ssh-keys
   # SSH key files '/home/rohit/.ssh/id_rsa' and '/home/rohit/.ssh/id_rsa.pub' have been generated under ~/.ssh to allow SSH access to the VM.
5. az aks get-credentials --resource-group myResourceGroup --name myAKSCluster
   ## ACR integration to be done
6. az acr create --resource-group <your-resource-group> --name <your-container-registry> --sku Basic
7. az acr login --name <your-container-registry>

8. docker build -t movie-catalogue-service .
9. for i in movie-catalogue reviews user-management; do cd $i; ./mvnw clean package; docker build -t $i-service .; cd -; done
10. Push images
ACR_NAME="<your-acr-name>.azurecr.io"
for i in movie-catalogue reviews user-management; do
   # Tag the Docker image for Azure Container Registry
   docker tag $i-service $ACR_NAME/$i-service:latest
   # Push the Docker image to Azure Container Registry
   docker push $ACR_NAME/$i-service:latest
   done
11. kubectl apply -f all-microservices.yaml