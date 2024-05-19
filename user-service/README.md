# User Service

This module provides the User Service in the e-commerce application.

## Prerequisites

- Java 8 or later
- Maven

## Building and Running

docker rm user-service

docker build -t user-service .

docker run --name user-service --network e-commerce-for-spring-cloud-network -p 8083:8083 -e EUREKA_SERVER=http://eureka-server:8761/eureka user-service:latest