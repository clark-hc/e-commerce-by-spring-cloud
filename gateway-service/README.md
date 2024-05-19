# Gateway Service

This module provides the Gateway Service in the e-commerce application.

## Prerequisites

- Java 8 or later
- Maven

## Building and Running

docker rm gateway-service

docker build -t gateway-service .

docker run --name gateway-service --network e-commerce-for-spring-cloud-network -p 8080:8080 -e EUREKA_SERVER=http://eureka-server:8761/eureka gateway-service:latest