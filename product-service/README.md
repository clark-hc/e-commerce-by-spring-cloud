# Product Service

This module provides the Product Service in the e-commerce application.

## Prerequisites

- Java 8 or later
- Maven

## Building and Running

docker rm product-service

docker build -t product-service .

docker run --name product-service --network e-commerce-for-spring-cloud-network -p 8081:8081 -e EUREKA_SERVER=http://eureka-server:8761/eureka product-service:latest