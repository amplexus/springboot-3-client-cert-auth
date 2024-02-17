# OVERVIEW

This page describes how to get the client app running on kubernetes using `kind`.

# SETUP

First, download and install kind:

```
curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.22.0/kind-linux-amd64 
chmod +x ./kind 
sudo mv ./kind /usr/local/bin
```

Next, create a cluster:

```
./k8s-create-kind-cluster.sh
```

# WORKFLOW

## CREATE DOCKER IMAGE

```
cd client
docker rmi springboot-client:1.0
./build-docker-image.sh
```

## IMPORT DOCKER IMAGE INTO KIND CLUSTER

```
./k8s-import-image-to-cluster.sh
```

## DEPLOY APP

```
./k8s-deploy-client.sh
```

# CLEANIP

```
./k8s-destroy-cluster.sh
```
