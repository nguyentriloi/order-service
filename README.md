# Spring Boot Application

## Overview

This is a **Spring Boot** based web application for [briefly describe what the application does]. It allows users to [brief description of key features or functionality of the application].

The application follows best practices for modern web development and uses various Spring ecosystem projects, such as Spring Boot, Spring Data JPA, and Spring Security.

## Table of Contents

1. [Technologies](#technologies)
2. [Features](#features)
3. [Prerequisites](#prerequisites)
4. [Getting Started](#getting-started)
5. [Build and Run](#build-and-run)
    - [Running with Maven](#running-with-maven)
6. [API Documentation](#api-documentation)

## Technologies

- **Spring Boot**: The main framework for building the application.
- **Spring Data JPA**: For interacting with databases.
- **Spring Security**: For authentication and authorization (OAuth2).
- **PostgreSQL Database**: The supported databases (depending on your configuration).
- **Liquibase**: For database migrations.
- **Maven**: For dependency management and building the application.

## Features

- User authentication and authorization using **OAuth2**.
- Order management (ordering, status tracking, payment).
  - Create order.
  - Change order status to ready, pickup, completed, failed
- Database migrations with **Liquibase**.
- RESTful APIs for interacting with the application.

## Prerequisites

Before you can build and run this application, ensure that you have the following installed:

- **Java 17** or later (Spring Boot 3.x requires JDK 17+).
- **Maven**: For building and running the application.
- **Terraform**: Set up infrastructure to run application.
- **Postman**: Postman to test the application’s API endpoints.

## Getting Started

### Run Terraform
You have to enter your client id, secret id and region to run Terraform
```bash
terraform init
terraform plan
terraform apply
```

## Build and Run

### Running with Maven

You can build the application using Maven:

```bash
mvn clean install
```

This will compile the project and generate a `.jar` file in the `target` directory.

### Build Docker Image

To package Spring Boot application into a container, use the following command:
var ecr_url: output from terraform

```bash
docker build -t coffee-shop/order .
aws ecr get-login-password --region ap-southeast-1 | docker login --username AWS --password-stdin ${accountId}.dkr.ecr.${region}.amazonaws.com
docker build -t coffee/order .
docker tag coffee/order:latest ${ecr_url}:latest
docker push 333740472250.dkr.ecr.ap-southeast-1.amazonaws.com/coffee/order:latest
```

Alternatively, if you want to run the JAR file directly:

```bash
java -jar target/app.jar
```

The application will be available at `http://localhost:8080` by default.

## API Documentation
We can receive alb_hostname = "cb-load-balancer-1952637199.ap-southeast-1.elb.amazonaws.com" in Terraform output.
We will import postman collection and change to ALB endpoint to test the api

- **POST** `/api/v1/order` - Customer create order.
- **PUT** `/api/v1/order/{id}/ready` - The Staff change status of order to ready for pickup.
- **PUT** `/api/v1/order/{id}/pickup` - The Staff change status of order to picked up.
- **PUT** `/api/v1/order/{id}/complete` - Change status of order to completed order when the customer confirm in the application.
