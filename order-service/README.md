# Order Service

This module provides the Order Service in the e-commerce application.

## Prerequisites

- Java 8 or later
- Maven

## Building and Running

docker rm order-service

docker build -t order-service .

docker run \
  --name order-service \
  --network e-commerce-for-spring-cloud-network \
  -p 8082:8082 \
  -e CONFIG_SERVER=http://config-server:8888 \
  -e EUREKA_SERVER=http://eureka-server:8761/eureka \
  -e ZIPKIN_SERVER=http://zipkin-server:9411 \
  order-service:latest