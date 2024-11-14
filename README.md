# Housing Cooperative Microservices

## Project Overview

Housing Cooperative Microservices is a distributed system based on microservices, designed to manage the operations of a housing cooperative. Each module in the system is responsible for a specific functionality, ensuring modularity and scalability.

## Project Structure

### Microservices
- **BudMiesz**: Module for managing buildings.
- **EmailNotification**: Handles email notifications.
- **JWTokens**: Manages JWT tokens for authentication and authorization.
- **Keycloak**: Integrates with Keycloak for identity and access management.
- **Mieszkancy**: Service for managing resident data.
- **MieszkancyZarzadcy**: Facilitates interactions between residents and managers.
- **PDF**: Generates PDF documents such as reports and invoices.
- **eurekaServer**: Service registry using Eureka.
- **gateway**: API gateway for routing requests between clients and microservices.

### Frontend
- **frontend**: The user interface of the application, providing access to system features.

### Auxiliary Files
- **Postman Collections**: API testing collections:
  - `BudMiesz.postman_collection.json`
  - `KeyCloak.postman_collection.json`
  - `MieszkancyZarzadcy.postman_collection.json`
  - `Mieszkancy_Users_Kujawa.postman_collection.json`

## Technologies Used

- **Java (Spring Boot)**: Core framework for most microservices.
- **Keycloak**: Identity and access management.
- **Eureka Server**: Service registry.
- **React**: Frontend framework.
- **PostgreSQL**: Relational database.
- **Docker**: Containerization.
- **Postman**: API testing and documentation.

## Installation and Setup

### Prerequisites
- **Java 17+**
- **Node.js 20+**
- **Docker & Docker Compose**
- **Postman** (optional for API testing)

# STATUS
```
https://drive.google.com/file/d/1kJ0EVnnp95BDB1iPOW6q3_b9Sz18h2E6/view?usp=sharing
```
