## 🛒 E-Commerce Microservices Platform

> A **production-ready Spring Boot microservices project** demonstrating Clean Architecture, CI/CD pipelines, Dockerized deployments, fault tolerance, and secure REST APIs.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![CI/CD](https://img.shields.io/badge/GitHub%20Actions-CI/CD-success)

---

## 📌 Features

* ✅ Microservices Architecture
* ✅ Clean Architecture & DDD
* ✅ Spring Cloud Config Server
* ✅ Eureka Service Discovery
* ✅ API Gateway (Centralized Routing)
* ✅ JWT Authentication & Authorization
* ✅ Circuit Breaker (Resilience4j)
* ✅ MongoDB & PostgreSQL
* ✅ Docker & Docker Compose
* ✅ CI/CD using GitHub Actions
* ✅ Swagger / OpenAPI Documentation

---

## 🧱 Clean Architecture Overview

Each service follows **Clean Architecture**:

```
Controller → Service → Domain → Repository
```

### Why Clean Architecture?

* Loose coupling
* High testability
* Easy scalability
* Clear separation of concerns

**Layers:**

* **Controller Layer** → REST APIs
* **Service Layer** → Business Logic
* **Domain Layer** → Core Models
* **Repository Layer** → DB Access

---

## 🏗 Architecture Diagrams

### Monolithic vs Microservices

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/1%2AaSdnOJNT2UoiaAhy-vuV_Q.png)

![Image](https://miro.medium.com/1%2Axu1Ge_Cew0DHdSU6ETcpLQ.png)

### Microservices Architecture Used in This Project

![Image](https://spring.io/img/extra/microservices-6.svg)

![Image](https://miro.medium.com/v2/resize%3Afit%3A2000/1%2AFjeBIfFHRDzb8OSckxSN7g.png)

---

## 🗂 Services Overview

| Service          | Description         | Database   |
| ---------------- | ------------------- | ---------- |
| customer-service | Customer management | MongoDB    |
| product-service  | Product catalog     | PostgreSQL |
| order-service    | Order processing    | PostgreSQL |
| payment-service  | Payment handling    | PostgreSQL |
| api-gateway      | Routing & security  | —          |
| config-server    | Centralized config  | Git        |
| discovery-server | Service registry    | —          |

---

## 🗄 Database Design (ERD)

![Image](https://landing.moqups.com/img/templates/diagrams/erd/ecommerce-database-diagram.png)

![Image](https://microservices.io/i/databaseperservice.png)

---

## 🔐 Security

* JWT Authentication
* Role-based authorization
* Secured endpoints via API Gateway
* OpenAPI Security Scheme (`Bearer Token`)

---

## 📄 API Documentation (Swagger)

Each service exposes Swagger UI:

| Service          | Swagger URL                                                                    |
| ---------------- | ------------------------------------------------------------------------------ |
| Customer Service | [http://localhost:8090/swagger-ui.html](http://localhost:8090/swagger-ui.html) |
| Product Service  | [http://localhost:8091/swagger-ui.html](http://localhost:8091/swagger-ui.html) |
| Order Service    | [http://localhost:8092/swagger-ui.html](http://localhost:8092/swagger-ui.html) |

---

## 🐳 Docker & Deployment

```bash
docker-compose up -d
```

Includes:

* MongoDB
* PostgreSQL
* MailDev
* All microservices
* API Gateway

---

## 🔄 CI/CD Pipeline

* GitHub Actions
* Build & Test
* Docker Image Creation
* Versioned Artifacts
* Ready for EC2 / Cloud Deployment

---

## 🧪 Testing Strategy

* Unit Tests: JUnit + Mockito
* Layer-focused tests (Service-level)
* CI-friendly (no DB, no containers)
* Integration tests optional via Testcontainers

---

## 📈 Why This Project?

✔ Real-world microservices design
✔ Production-grade best practices
✔ CI/CD & DevOps ready
✔ Resume + Interview ready

---
