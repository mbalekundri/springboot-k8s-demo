# https://learn.microsoft.com/en-us/training/modules/aks-app-package-management-using-helm/3-exercise-set-up-the-environment
# In this exercise, you'll configure the environment for use throughout this module. You'll install and configure the following resources:
# (Clone the GitHub repository that contains the sample application)
# (Create an Azure resource group to hold the resources for the solution)
# (Create an Azure Container Registry &#40;ACR&#41; to store the container images)
# (Create an Azure Kubernetes Service &#40;AKS&#41; cluster to host the application)
# (Connect to the AKS cluster using the Azure CLI)
## Steps:
1. az account set --subscription <subscription-name>
2. git clone https://github.com/Azure-Samples/aks-store-demo.git
3. export RESOURCE_GROUP=rg-aks-store-demo
4. export AKS_CLUSTER_NAME=cluster-aks-store-demo
5. export LOCATION=southindia / southeastasia
6. az group create --name $RESOURCE_GROUP --location $LOCATION
7. export ACR_NAME=acr-aks-store-demo
8. az acr create --resource-group $RESOURCE_GROUP --name $ACR_NAME --sku Basic
9. az aks create --resource-group $RESOURCE_GROUP --name $AKS_CLUSTER_NAME --node-count 2 --attach-acr $ACR_NAME --generate-ssh-keys
10. az aks get-credentials --resource-group $RESOURCE_GROUP --name $AKS_CLUSTER_NAME
11. kubectl get nodes