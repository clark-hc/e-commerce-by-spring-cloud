# Eureka Server

This module provides the Eureka Server for service discovery and registration in the e-commerce application.

## Prerequisites

- Java 8 or later
- Maven

## Building and Running

1. Navigate to the `eureka-server` directory.
2. Build the project using Maven: `mvn clean install`
3. Run the Eureka Server: `mvn spring-boot:run`

The Eureka Server will start running on `http://localhost:8761`.

## Configuration

The Eureka Server can be configured in the `application.yaml` and `bootstrap.yaml` files located in the `src/main/resources` directory.

`application.yaml`:
- `server.port`: The port on which the Eureka Server will run (default: 8761).
- `eureka.instance.hostname`: The hostname of the Eureka Server instance.
- `eureka.client.registerWithEureka`: Whether the Eureka Server should register itself as a client (default: false).
- `eureka.client.fetchRegistry`: Whether the Eureka Server should fetch registry information from other peers (default: false).
- `eureka.client.serviceUrl.defaultZone`: The URL of the Eureka Server cluster, if running in a cluster mode.

`bootstrap.yaml`:
- `spring.cloud.config.uri`: The URL of the Spring Cloud Config Server, if using centralized configuration management.
- `spring.cloud.config.name`: The name of the configuration file to fetch from the Spring Cloud Config Server.

## Usage

Other microservices in the e-commerce application should register themselves with the Eureka Server by adding the following configuration to their `application.yaml` or `bootstrap.yaml` files:

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

The Eureka Server provides a web interface at `http://localhost:8761` where you can view the registered services and their instances.



docker network create e-commerce-for-spring-cloud-network



docker rm eureka-server .

docker build -t eureka-server .

docker run --name eureka-server --network e-commerce-for-spring-cloud-network -p 8761:8761 eureka-server:latest