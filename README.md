# selenium-grid on Container [![pages-build-deployment](https://github.com/thananauto/selenium-grid-k8s/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/thananauto/selenium-grid-k8s/actions/workflows/pages/pages-build-deployment)
This is the sample UI selenium scripts to do the execution on grid premises, the grid could be setup either in Docker or Kubernetes

## Tools and Libraries
- Selenide
- Junit5
- Gitworkflow
- Docker
- Kubernetes
  
## Overview
This repository contains resources and configurations to deploy Selenium Grid on a Kubernetes cluster. Selenium Grid allows you to distribute your tests on multiple machines, improving parallelization and scalability. Kubernetes, a powerful container orchestration platform, is used here to manage the deployment and scaling of Selenium Grid components.
You can set up the same grid in docker-compose well, here the github action workflow was setup the execute for every push and PR to `main`

## Prerequisites
Before deploying Selenium Grid on Kubernetes, ensure that you have the following prerequisites set up:

A running Kubernetes cluster.
* `kubectl` configured to access the cluster.
* Docker installed for building custom Docker images (if needed).
* Docker-compose

## Dockerfile details
This Dockerfile creates a lightweight Alpine Linux-based image with OpenJDK 11 and Maven installed, suitable for building and running Java applications. And also the instruction was to set to copy all source to code to build the image
### Usage
1. Build Docker Image
```bash
docker build -t your-image-name .
```
2. Run Docker Container
```bash
docker run -it -rm -v `pwd`/output:/app/target  your-image-name
```
By default, the container is configured to run the Maven test goal when started. Feel free to customize the Dockerfile as needed. You can modify the base image, install additional dependencies, or adjust the entry point and default command

## Selenium Grid Docker Compose Configuration

This Docker Compose configuration sets up a Selenium Grid environment with multiple browser nodes using Docker containers. The configuration includes services for the application, Selenium Hub, and different browser nodes (Chrome, Edge, Firefox).

### Services
  1. app
 * `Build`: Build the application using the Dockerfile in the current directory.
 * `Container Name`: app_test
 * `Volumes`: Map the local ./output directory to /app/target in the container.
 * `Networks`: Connect to the "grid" network.
 * `Command`: Run the "test" command.
 * `Links`: Connect to the Selenium Hub service.
 * `Depends On`: Wait for the Selenium Hub service to be healthy before starting.
  
  2. `chrome`, `edge`, `firefox`
  * `Image`: Use pre-built Selenium browser images (Chrome, Edge, Firefox).
  * `Container Name`: node-chrome, node-edge, node-firefox
  * `Shared Memory Size`: Set shared memory size to 2GB.
  * `Depends On`: Wait for the Selenium Hub service to be healthy before starting.
  * `Environment Variables`: Configure Selenium node properties and connection details.
  * `Networks`: Connect to the "grid" network.
  
  3. selenium-hub
  * `Image`: Use the Selenium Hub image.
  * `Container Name`: selenium-hub
  * `Ports`: Expose ports 4442, 4443, and 4444 for communication.
  * `Networks`: Connect to the "grid" network.
  * `Healthcheck`: Verify the health of the Selenium Hub using a curl command.
  
  4. Networks
        grid
       * Driver: Bridge
          - Name: grid-driver

### Usage
1. Build and Run the Selenium Grid
```bash
docker-compose up -d
```
2. Access the Selenium Grid Console
Navigate to http://localhost:4444/grid/console in your web browser.

3. Run Tests in the Application Container
```bash
docker exec -it app_test mvn test
```
Customization
Feel free to customize the Docker Compose configuration to suit your specific requirements, such as adjusting browser node configurations, adding more nodes, or modifying application dependencies.

## Kubernetes Selenium Grid configuration
Follow these steps to deploy Selenium Grid on your Kubernetes cluster:

1. Clone this repository:
```bash
git clone https://github.com/thananauto/selenium-grid-k8s.git
cd selenium-grid-k8s
```

2. Modify the configuration files as needed. You may need to adjust the number of replicas, resources, or other settings based on your requirements.
Deploy Selenium Grid using kubectl:

```bash
kubectl create namespace -n keda # separate namespace keda is needed if we going to use Grid autoscaling functionality
kubectl create -f grid-k8/deployment/
kubectl create -f grid-k8/services/
```
For **KEDA** installation in controlplane refer [here](https://keda.sh/docs/1.5/deploy/#yaml)

3. Monitor the deployment using kubectl:
```bash
kubectl get all
```
Ensure that all `deployments`, `services` and `pods` are up and running.

4. Accessing Selenium Grid, Once the deployment is successful, you can access the Selenium Grid console through the exposed service. By default, the service is set to `NodePort`. Retrieve the NodePort assigned to the service using:
```bash
kubectl get service selenium-hub
```
Access the Selenium Grid console by navigating to `http://<Node-IP>:<NodePort>` in your web browser.

5. Scaling Selenium Grid
You can scale the Selenium Grid nodes by updating the deployment replicas on the deployment yaml file or else you can edit using kubectl command
```bash
kubectl edit pods <pod-name> --replicas <desired-replica-count>
```
6. Execute UI test job
```bash
kubectl create -f grid-k8/jobs
```
7. Customization
Feel free to customize the Kubernetes resources to suit your specific requirements. For example, you can modify resource requests, limits, or use custom Docker images for the Selenium nodes.

8. Cleanup
When you're done testing or using Selenium Grid, you can delete the deployment using kubectl:
```bash
Copy code
kubectl delete <service-name> or <deployment-name>
```
This will remove all resources created by the Selenium Grid deployment.

**Note:** Currently we set the replica as 1 across all browser deployment yaml files, but in real time this could be increase based on the request load to `selenium-hub`, this could be addressed by Auto-scaling feature of kubernetes after setup default resources
```bash
kubectl create -f grid-k8s/scale
```
for more information refer [here](https://www.selenium.dev/blog/2022/scaling-grid-with-keda/)

### Troubleshooting
If you encounter any issues during deployment, refer to the troubleshooting section in the documentation or inspect the pod logs for more details.


## GitHub Actions Workflow

This GitHub Actions workflow is designed to execute UI tests on the main branch. It utilizes Docker containers, sets up a Selenium Grid environment, and deploys test results to GitHub Pages.

### Workflow Overview
The workflow is triggered on every push to the main branch and pull requests targeting the main branch. It performs the following steps:

1. **Checkout Repository:**
* Check out the repository to the runner

2. **Set up JDK 17:**
* Use JDK 17 provided by the Temurin distribution.
* Cache Maven dependencies.

3. **Docker Cache:**
* Utilize Docker layer caching for improved build performance.
  
4. **Setup Hub and Nodes:**
* Build and start Docker containers defined in the docker-compose.yml file.
* Create a Selenium Grid environment with Hub and Nodes.

5. **Wait for Test Containers:**
* Execute a script `(wait_for_exit.sh)` to wait for the test containers to exit before proceeding.
  
6. **Deploy Test Output:**
If the workflow is triggered on a push to the main branch:
Deploy test output reports to GitHub Pages using the `peaceiris/actions-gh-pages` action.

7. **Stop the Grid Setup:**
Always run this step to stop and clean up the Selenium Grid setup.

### How to Use
1. Push changes to the main branch or create pull requests targeting the main branch.
2. View test reports on GitHub Pages if the push is directly to main.
   
### Customization
Modify the docker-compose.yml file to adjust the Selenium Grid setup or add/remove containers.
Update the script or steps for waiting on test containers based on specific requirements.

### Permissions
This workflow requires specific permissions to write to the repository and GitHub Pages.

* id-token: Write permissions for authentication.
* pages: Write permissions to deploy test reports to GitHub Pages.
* contents: Write permissions to the repository.

For test HTML result visit this website [here](https://thananauto.github.io/selenium-grid-k8s/)

## Contributing
Contributions are welcome! If you find issues or have improvements, please open a GitHub issue or submit a pull request.

Happy testing with Selenium Grid on Kubernetes!


## License
This project is licensed under the MIT [License](https://chat.openai.com/c/LICENSE.md).

