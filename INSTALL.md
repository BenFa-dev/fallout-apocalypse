# 📦 Installation Guide — Apocalypse: The Fall

This document explains how to install and run the project locally.  

---

## 1. Prerequisites

Make sure you have the following installed on your system:

- [Node.js 20+](https://nodejs.org/)
- [Angular CLI](https://angular.dev/tools/cli)
- [Java 24](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL 17+](https://www.postgresql.org/)
- [Apache Kafka](https://kafka.apache.org/)
- [Keycloak](https://www.keycloak.org/)
- [Docker & Docker Compose](https://docs.docker.com/)
---

## 2. Clone the Repository

```bash
git clone git@github.com:BenFa-dev/fallout-apocalypse.git
```

---

## 3. Manual Installation

Check credentials in **docker-compose.yml** files.

### Docker-compose 

Needed to start database, keycloak and Kafka.

```bash
cd config
docker-compose up -d
```

### Database (PostgreSQL)

Make sure PostgreSQL is running (default: `localhost:5432`).

Create the database if necessary : **apocalypse_the_fall_db**.

### Keycloak

Make sure Keycloak is running (default: `localhost:8081`).

### Frontend (Angular 20)

```bash
cd frontend
npm install
npm start
```
👉 The frontend runs on: **http://localhost:4200**


### Backend (Spring Boot 3)

```bash
cd backend
./mvnw spring-boot:run
```
👉 The backend API runs on: **http://localhost:8080**
