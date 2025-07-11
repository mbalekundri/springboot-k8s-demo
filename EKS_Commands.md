# https://www.youtube.com/watch?v=mVSFHgItaa4

# Create EKS Cluster  --version 1.28 and  --region us-east-2  --node-type=t2.small

    eksctl create cluster --name Demo-Eks-cluster --version 1.31 --nodes=1 --node-type=t2.small --region ap-south-1

# Configure kubectl to Use the EKS Cluster

    aws eks --region us-east-2 update-kubeconfig --name Demo-Eks-cluster```

# Delete cluster
    eksctl delete cluster Demo-Eks-cluster


1. aws sts get-caller-identity
2. aws eks list-clusters --region ap-south-1