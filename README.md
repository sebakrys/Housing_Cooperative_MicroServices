# Housing Cooperative Management System

## Project Description

This project is a housing cooperative management system based on microservices architecture. The application enables the management of buildings, premises, tenants, meter readings, and bill generation. It also supports PDF bill generation and offers email notifications and a user interface in its advanced versions.

## Features

- **Management of buildings, premises, and tenants** (CRUD)
- **Meter readings**: electricity, gas, water (hot, cold), sewage, heating
- **Bill issuance and management**
- **PDF bill generation**
- **User authentication and roles**:
  - Administrator
  - Manager
  - Tenant
- **Service registration in Eureka**
- **Request routing through Gateway with load balancing**
- **Validation of key data fields**

### Additional Features:
- **Email notifications** using asynchronous communication between services (Notification Service)
- **User interface** for registration, login, and basic views (e.g., building list)

## System Architecture

The system consists of the following microservices:
1. **Eureka Server** - service registration
2. **API Gateway** - load balancing, request routing
3. **BudMiesz Service** - management of buildings and premises
4. **Mieszkancy Service** - management of tenants
5. **MieszkancyZarzadcy Service** - management of managers
6. **PDF Service** - PDF bill generation
7. **Notification Service** (optional) - email notifications

## Technologies

- **Backend**: Java, Spring Boot, Spring Cloud (Eureka, Gateway)
- **Database**: PostgreSQL / MongoDB
- **Frontend**: Angular (optional)
- **Version control**: GitLab

## Setup Instructions

### Requirements

- Java 17+
- Maven
- PostgreSQL
- Docker (optional)



#STATUS
```
https://drive.google.com/file/d/1kJ0EVnnp95BDB1iPOW6q3_b9Sz18h2E6/view?usp=sharing
```
