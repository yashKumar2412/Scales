# Scales

Scales is a dynamic load balancer built with Spring Boot, designed to manage and route traffic efficiently across backend servers. It consumes heartbeat messages from the servers at a configurable schedule to obtain healthy URLs to forward the requests.

## Features

- **Dynamic Server Discovery**: Scales dynamically updates the list of backend servers through consumption of heartbeat messages from a message queue.
- **Load Balancing Algorithm**: Supports Round-Robin algorithm.
- **Health Checks**: Automatically removes unhealthy servers based on missed heartbeats.

## Technologies Used
- Java: JDK 17
- Maven: 3.4.1

## Setup
1. Common Steps:
    - Setup a SQS queue on AWS. Update the configuration files for both Scales and server app to point to the correct SQS URL.
    - Update scheduler-related configuration (initial delay and fixed rate)for both apps.
    - Create appropriate IAM Roles and Security Groups for both apps.
2. Your Server Application:
    - Update your server code to send a heartbeat message to the SQS queue containing the instance's IP and port (refer [Scales-Test-App](https://github.com/yashKumar2412/Scales-Test-App) for EC2 instance example).
3. Scales:
    - Select your algorithm of choice (ROUND-ROBIN, RANDOM) and update the application.properties file.
    - To run the application locally, run the following commands: 
        ```code
        mvn clean install
        mvn spring-boot:run
        ```
      Send the appropriate request to http://localhost:8080 instead of your server's IP.
    - To run the application on a server, use Docker.
      ```code
      mvn clean install
      docker build -t scales-app .
      docker tag scales-app:latest your-dockerhub-username/scales-app:latest
      docker push your-dockerhub-username/scales-app:latest
      ```
      Use this image to deploy to a server like AWS EC2.

## Future Improvements
- Expand Scales to other messaging queues (RabbitMQ, Kafka).
- Add options for more load balancing algorithms.
- Expand test-app with ECS and Lambda examples for better documentation examples.