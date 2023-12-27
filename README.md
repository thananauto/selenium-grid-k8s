# selenium-grid on Kubernetes, Docker-compose
UI framework demonstrated wit Selenoid + Junit5 + ExtentReport + Dockerfile + gitworkflow + Grid in Kubernetes

## Overview
This repository contains resources and configurations to deploy Selenium Grid on a Kubernetes cluster. Selenium Grid allows you to distribute your tests on multiple machines, improving parallelization and scalability. Kubernetes, a powerful container orchestration platform, is used here to manage the deployment and scaling of Selenium Grid components.

## Prerequisites
Before deploying Selenium Grid on Kubernetes, ensure that you have the following prerequisites set up:

A running Kubernetes cluster.
* `kubectl` configured to access the cluster.
* Helm installed on your local machine.
* Docker installed for building custom Docker images (if needed).
  
## Getting Started
Follow these steps to deploy Selenium Grid on your Kubernetes cluster:

1. Clone this repository:
```bash
git clone https://github.com/thananauto/selenium-grid-k8s.git
cd selenium-grid-k8s
```

2. Modify the configuration files as needed. You may need to adjust the number of replicas, resources, or other settings based on your requirements.
Deploy Selenium Grid using kubectl:

```bash
kubectl create -f deployment/
kubectl create -f services/
```
3. Monitor the deployment using kubectl:
```bash
kubectl get all
```
Ensure that all `deployments`, `services` and `pods` are up and running.

4.Accessing Selenium Grid
Once the deployment is successful, you can access the Selenium Grid console through the exposed service. By default, the service is set to `NodePort`. Retrieve the NodePort assigned to the service using:
```bash
kubectl get service selenium-grid
```
Access the Selenium Grid console by navigating to `http://<Node-IP>:<NodePort>` in your web browser.

5. Scaling Selenium Grid
You can scale the Selenium Grid nodes by updating the deployment replicas on the deployment yaml file or else you can edit using kubectl command
```bash
kubectl edit pods <pod-name> --replicas <desired-replica-count>
```
6. Customization
Feel free to customize the Kubernetes resources to suit your specific requirements. For example, you can modify resource requests, limits, or use custom Docker images for the Selenium nodes.

7. Cleanup
When you're done testing or using Selenium Grid, you can delete the deployment using kubectl:

```bash
Copy code
kubectl delete <service-name> or <deployment-name>
```
This will remove all resources created by the Selenium Grid deployment.

## Troubleshooting
If you encounter any issues during deployment, refer to the troubleshooting section in the documentation or inspect the pod logs for more details.

## Contributing
Contributions are welcome! If you find issues or have improvements, please open a GitHub issue or submit a pull request.

Happy testing with Selenium Grid on Kubernetes!

For test HTML result visit this website [here](https://thananauto.github.io/selenium-grid-k8s/)
