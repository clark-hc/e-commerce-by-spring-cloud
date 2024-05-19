# Zipkin Server

This module provides the Zipkin Server in the e-commerce application.

## Prerequisites

- Docker

##  Running

docker rm zipkin-server

docker run --name zipkin-server --network e-commerce-for-spring-cloud-network -p 9411:9411 openzipkin/zipkin:latest