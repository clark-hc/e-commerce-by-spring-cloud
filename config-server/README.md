# Config Server

This module provides the Config Server in the e-commerce application.

## Prerequisites

- Java 8 or later
- Maven

## Building and Running

docker rm config-server

docker build -t config-server .

docker run --name config-server --network e-commerce-for-spring-cloud-network -p 8888:8888 -e EUREKA_SERVER=http://eureka-server:8761/eureka config-server:latest