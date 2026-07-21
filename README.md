# 🛒 E-Commerce Backend API

A production-style **E-Commerce Backend Application** built using **Spring Boot** following enterprise development practices.

The project demonstrates secure authentication, REST APIs, database design, caching, Docker containerization, and scalable backend architecture.

---

# 🚀 Tech Stack

### Backend
- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

### Database
- PostgreSQL 17

### Cache
- Redis 7

### Authentication & Security
- JWT Authentication
- Refresh Token
- Role-Based Authorization
- OAuth2 Login (Google & GitHub)

### DevOps
- Docker
- Docker Compose
- Multi-stage Docker Build

### Tools
- IntelliJ IDEA
- Postman
- pgAdmin 4
- RedisInsight
- Git
- GitHub

---

# 📁 Project Structure

```
Ecommerce
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.vikash.Ecommerce
│   │   │       ├── config
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       ├── entity
│   │   │       ├── exception
│   │   │       ├── mapper
│   │   │       ├── repository
│   │   │       ├── security
│   │   │       ├── service
│   │   │       └── util
│   │   │
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── application-prod.yml
│   │
│   └── test
│
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── .gitignore
├── .env.example
├── README.md
└── pom.xml
```

---

# ✨ Features

## Authentication

- User Registration
- User Login
- JWT Authentication
- Refresh Token
- Secure Password Encryption
- Role-Based Authorization
- OAuth2 Login (Google)
- OAuth2 Login (GitHub)

---

## Category Module

- Create Category
- Update Category
- Delete Category
- Get Category by ID
- Get All Categories
- Pagination
- Sorting
- Redis Caching

---

## Product Module

- Product CRUD
- Category Mapping
- Pagination
- Sorting

---

## Cart Module

- Add Product to Cart
- Remove Product
- Update Quantity

---

## Review Module

- Product Reviews

---

## Database

- PostgreSQL
- JPA Relationships
- Entity Validation
- DTO Pattern
- Global Exception Handling

---

## Redis Cache

- Spring Cache
- @Cacheable
- @CacheEvict
- Automatic Cache Management

---

## Docker

Dockerized application with

- Spring Boot Container
- PostgreSQL Container
- Redis Container

Persistent Docker Volumes

- postgres_data
- redis_data

---

# 🛠️ API Features

- RESTful APIs
- Layered Architecture
- DTO Mapping
- Exception Handling
- Validation
- Pagination
- Sorting
- Cache Management
- Authentication
- Authorization

---

# 🐳 Run with Docker

## 1. Clone Repository

```bash
git clone https://github.com/vikashwin/Enterprise-Ecommerce-Backend.git

cd Ecommerce
```

---

## 2. Create Environment File

Copy

```
.env.example
```

Rename it to

```
.env
```

Update the values according to your system.

---

## 3. Build & Run

```bash
docker compose up --build
```

---

## 4. Stop Containers

```bash
docker compose down
```

---

## 5. Remove Containers and Volumes

```bash
docker compose down -v
```

---

# 🌐 Default URLs

Application

```
http://localhost:8081/v1
```

Swagger UI

```
http://localhost:8081/v1/swagger-ui/index.html
```

---

# 💾 Database

Docker PostgreSQL

```
Host : localhost
Port : 5432
Database : Ecommerce
Username : postgres
Password : (Configured in .env)
```

Use **pgAdmin 4** to connect.

---

# ⚡ Redis

Docker Redis

```
Host : localhost
Port : 6379
```

Use **RedisInsight** to monitor cache.

---

# 🔐 Environment Variables

The project uses environment variables for sensitive information.

Examples include:

- PostgreSQL Credentials
- Redis Configuration
- JWT Secret
- OAuth Client IDs
- OAuth Client Secrets

Create a `.env` file using `.env.example`.

**Never commit your `.env` file to GitHub.**

---

# 📌 Learning Progress

## ✅ Completed

- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- DTO Pattern
- Entity Mapping
- Validation
- Exception Handling
- JWT Authentication
- Refresh Token
- OAuth2 Authentication
- Redis Cache
- Docker
- Docker Compose
- Multi-stage Docker Build
- Environment Variables

---

## 🚧 Currently Working On

- Order Module
- Payment Integration
- Email Verification
- File Upload
- Unit Testing
- Integration Testing
- CI/CD Pipeline
- Kubernetes Deployment

---

# 📸 Screenshots

You can add screenshots inside the `images/` directory.

Example:

- Login API
- Swagger UI
- pgAdmin
- RedisInsight
- Docker Desktop
- Postman Collection

---

# 🤝 Contributing

Contributions, issues, and feature requests are welcome.

1. Fork the repository
2. Create your feature branch

```bash
git checkout -b feature/NewFeature
```

3. Commit your changes

```bash
git commit -m "Add new feature"
```

4. Push

```bash
git push origin feature/NewFeature
```

5. Create a Pull Request

---

# 📜 License

This project is created for learning purposes and personal portfolio development.

---

# 👨‍💻 Author

**Vikash Gupta**

- 💼 Backend Developer
- ☕ Java & Spring Boot Enthusiast
- 🚀 Passionate about Backend Development, System Design, and Cloud Technologies

---

## ⭐ If you like this project

Give this repository a **Star ⭐** on GitHub.