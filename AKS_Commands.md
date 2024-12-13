# Reference: https://learn.microsoft.com/en-us/training/modules/aks-deploy-container-app/
az account show
# change the active subscription using the subscription ID
az account set --subscription "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"

1. export RESOURCE_GROUP=rg-contoso-video
2. export CLUSTER_NAME=aks-contoso-video
3. export LOCATION=southindia / southeastasia
4. az group create --name=$RESOURCE_GROUP --location=$LOCATION
5. Create AKS cluster
   az aks create --resource-group $RESOURCE_GROUP --name $CLUSTER_NAME --node-count 2 --generate-ssh-keys --node-vm-size Standard_B2s --network-plugin azure --windows-admin-username localadmin
   password: LocalAdmin123@AksPoc
6. Run the az aks nodepool add command to add another node pool that uses the Windows operating system
   az aks nodepool add --resource-group $RESOURCE_GROUP --cluster-name $CLUSTER_NAME 
   --name uspool --node-count 2 --node-vm-size Standard_B2s --os-type Windows    
7. Link your Kubernetes cluster with kubectl by running the following command in Cloud Shell.
   az aks get-credentials --name $CLUSTER_NAME --resource-group $RESOURCE_GROUP
8. kubectl apply -f k8s-manifest.yml
9. Confirm configurations are correct
   kubectl get nodes
9. kubectl config delete-context aks-contoso-video
10. kubectl delete -f k8s-manifest.yml

# Linux commands to edit save 
1. vi fileName
2. Paste code
3. Press Esc
4. Type :wq
5. Press Enter

# Linux commands to edit quit without save
1. vi fileName
2. Paste code
3. Press Esc
4. Type :q!
5. Press Enter