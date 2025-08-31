# Apocalypse: The Fall

[![Angular](https://img.shields.io/badge/Angular-20-DD0031?logo=angular&logoColor=white)](https://angular.dev/) [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot) [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/) [![Kafka](https://img.shields.io/badge/Kafka-Event%20Streaming-231F20?logo=apachekafka&logoColor=white)](https://kafka.apache.org/) [![License: GPL](https://img.shields.io/badge/License-GNU%20GPL-blue)](LICENSE)

> A development project to explore the latest features of **Angular 20** through a post-apocalyptic game inspired by
*Fallout*.
>
> The backend is powered by **Spring Boot 3**.

---

## 🚀 Quickstart

For installation instructions, see [INSTALL.md](INSTALL.md).

---

## 🛠 Tech Stack

### Frontend

- **Angular 20**
- **NgRx 20**: State management / Signal Store
- **Phaser 3**: 2D game engine

### Backend

- ** Java / Kotlin **
- **Spring Boot 3**
- **JPA + Hibernate**
- **PostgreSQL**
- **Kafka**: Event streaming (planned)
- **Spring AI**: AI-powered content generation (planned)

### Authentication

- **Keycloak**: Authentication & Authorization

---

## 🎮 Current Status

**Technical**

- ✅ Landing page with Keycloak authentication
- ✅ Login / logout
- ✅ Phaser basic integration
- ✅ Kafka + WebSocket (poc)
- ❌ Loading optimizations: Dexie? IndexedDB
- 🚧 More granular store splitting
    - No more massive Character Inventory
    - PERKS rules update
- ❌ Backend modularization (Core / API)
- ❌ Rework of the rules system (SPECIAL, Skills, Perks), fully DB-driven?
- 🚧 Java to kotlin migration

**Features**

- ✅ Basic world map
- ✅ Basic character creation
- ✅ Player movement
    - ✅ Fog of war
- ✅ Inventory
- 🚧 Character sheet (skills, perks, stats, etc.)

---

## 📄 License

This project is licensed under the GNU GPL.  
