# SaaS Multi-Tenant Backend – Spring Boot

A production-ready **SaaS backend system** built using **Spring Boot, PostgreSQL, JWT Authentication, and Docker**.

This project demonstrates a **multi-tenant SaaS architecture** where multiple companies can register and manage their own users securely.

---

## 🚀 Tech Stack

* Java 17
* Spring Boot 3
* Spring Security
* JWT Authentication
* PostgreSQL
* Hibernate / JPA
* Docker
* Swagger OpenAPI

---

## 📦 Features

* Company (Tenant) Registration
* User Authentication with JWT
* Role Based Access Control
* Multi-Tenant Architecture
* Secure REST APIs
* Swagger API Documentation
* Dockerized Deployment

---

## 🗄 Database Entities

* Company
* Users
* Subscription
* Roles

---

## ⚙️ Running the Project

### 1️⃣ Clone the repository

```
git clone https://github.com/yourusername/saas-springboot-backend.git
```

### 2️⃣ Navigate to project

```
cd backend
```

### 3️⃣ Build project

```
mvn clean package -DskipTests
```

### 4️⃣ Run using Docker

```
docker compose up --build
```

Application will start on:

```
http://localhost:8081
```

---

## 📘 API Documentation

Swagger UI:

```
http://localhost:8081/swagger-ui/index.html
```

---

## 🔐 Authentication

Login API returns a **JWT token**.

Use token in headers:

```
Authorization: Bearer YOUR_TOKEN
```

---

## 🧪 Example APIs

### Register Company

```
POST /auth/register
```

### Login

```
POST /auth/login
```

### Create User

```
POST /users
```

---

## 🐳 Docker Services

* Backend Service
* PostgreSQL Database

---

## 👨‍💻 Author

Mukhtar Alam
Java Backend Developer | Spring Boot | Microservices | SaaS Systems

backend
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.mukhtar.saas
│   │   │       ├── config
│   │   │       ├── controller
│   │   │       ├── service
│   │   │       ├── repository
│   │   │       ├── entity
│   │   │       ├── security
│   │   │       └── dto
│   │
│   └── resources
│       └── application.yml
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md


