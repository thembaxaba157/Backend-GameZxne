# 🎮 GameZxne

[![Java](https://img.shields.io/badge/Java-17+-brightgreen.svg)](https://www.oracle.com/java/)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-blue.svg)](https://spring.io/projects/spring-boot)  
[![MySQL](https://img.shields.io/badge/MySQL-Running%20in%20Docker-00758f?logo=mysql)](https://www.mysql.com/)  
[![WebSocket](https://img.shields.io/badge/WebSocket-STOMP%20+%20SockJS-ff69b4)](https://stomp-js.github.io/)  
[![Security](https://img.shields.io/badge/Security-JWT%20Auth-critical)](https://jwt.io)  
[![CI/CD](https://img.shields.io/github/actions/workflow/status/thembaxaba157/Backend-GameZxne/main.yml?branch=main&label=GitHub%20Actions&logo=github)](https://github.com/thembaxaba157/Backend-GameZxne/actions)  
[![Versioning](https://img.shields.io/badge/Semantic%20Release-Automated-blueviolet)](https://semantic-release.gitbook.io/)

> ⚠️ **Project Status:** This project is actively under development. Core gameplay and authentication features are functional, but additional enhancements are ongoing.

---

## Overview

**GameZxne** is a multiplayer gaming backend platform built in **Java** using **Spring Boot**. It supports real-time gameplay using **WebSockets**, secure authentication via **JWT**, and a full REST API for managing users, games, and players.

It is intended to serve as the **backend for a multiplayer Rock-Paper-Scissors game**, with future extensibility for other turn-based games.

---

## Key Features

- ✅ **User Registration & Login** using JWT
- ✅ **Game Lobby Management** (Create, Join, Update, Delete)
- ✅ **WebSocket Real-Time Messaging** (STOMP over SockJS)
- ✅ **Round-based Game Logic** with Score Calculation
- ✅ **Role-based Access Control** via Spring Security
- ✅ **MySQL Database** managed with Docker Compose
- ✅ **RESTful API** with DTO patterns
- ✅ **Semantic Versioning & GitHub Actions CI/CD**

---

## ⚙️ Technology Stack

| Layer              | Technology                        |
|-------------------|------------------------------------|
| Language           | Java 17                           |
| Framework          | Spring Boot 3.2                   |
| Build Tool         | Maven                             |
| Security           | Spring Security + JWT             |
| WebSockets         | STOMP + SockJS                    |
| Database           | MySQL 8 (Dockerized)              |
| CI/CD              | GitHub Actions + Semantic Release |
| Versioning         | Semantic Versioning               |
| Testing (Planned)  | JUnit 5 + Mockito (In Progress)   |

---

## 🗃️ Project Structure

```
com.game.gamezxne
│
├── auth         # Authentication logic (JWT, UserModel, Login/Register)
├── rps          # Rock-Paper-Scissors game logic (controllers, services, models)
├── websocket    # WebSocket config and STOMP interception
├── exceptions   # Custom exceptions and global error handling
├── resources
│   ├── application.properties  # MySQL DB config
│   └── docker-compose.yml      # MySQL container
└── GameZxneApplication.java    # Entry point
```

---

## Setup & Run

### Prerequisites

- Java 17+
- Maven
- Docker & Docker Compose

### 🐳 Start MySQL with Docker

```bash
docker-compose up -d
```

### ▶️ Run the Application

```bash
mvn spring-boot:run
```

Application will start on: `http://localhost:8080`

### 🔒 Auth Endpoints

```http
POST /auth/register     → Register user
POST /auth/login        → Login and receive JWT token
```

---

## 📡 WebSocket Communication

- **Endpoint:** `/ws`
- **Topic Subscription Format:** `/topic/messages/{username}`
- **STOMP Broker:** In-memory
- **Authentication:** JWT token is passed via URL:  
  `ws://localhost:8080/ws?token=YOUR_JWT_TOKEN`

---

## 📘 API Summary

| Method | Endpoint                | Description                        |
|--------|-------------------------|------------------------------------|
| POST   | `/auth/register`        | Create a new user                  |
| POST   | `/auth/login`           | Login and get JWT                  |
| GET    | `/lobby`                | List all lobbies                   |
| POST   | `/lobby`                | Create a game lobby                |
| PUT    | `/lobby/join/{id}`      | Join a lobby                       |
| POST   | `/lobby/move`           | Make a move in the game            |
| GET    | `/players/load`         | Load player info (by JWT username) |
| POST   | `/players/create`       | Create new player entity           |

More endpoints available in `GameController` and `PlayerController`.

---

## Testing & CI/CD

### GitHub Actions

- Builds, tests, and runs Maven on every push to `main`
- Launches Dockerized MySQL for integration
- Uses Semantic Release to:
  - Bump version in `pom.xml`
  - Push GitHub release notes

### Unit Tests (Planned)

- Will use **JUnit 5** and **Mockito**
- CI step already in place

---

## 📈 Versioning

This project uses **Semantic Versioning** and automatic changelog generation:

```bash
npm install -D semantic-release
npx semantic-release
```

See `.releaserc.yml` and GitHub Action for setup.

---

## Future Improvements

- Full test coverage (unit + integration)
- Frontend (React or JavaFX) integration
- Admin panel for game moderation
- Redis caching for real-time data
- Leaderboards and stats tracking
- Graceful WebSocket disconnect and reconnect support

---

## Contributing

```bash
# 1. Fork the repo
# 2. Create a branch
git checkout -b feat/my-feature
# 3. Make your changes and commit
git commit -m "feat: add my feature"
# 4. Push and open a PR
git push origin feat/my-feature
```

Contributions, issues, and feature requests are welcome.

---

## 📝 License

This project is licensed under the **MIT License**.

---

## Acknowledgements

- Spring Boot community & STOMP/WebSocket guides
- JWT.io for authentication principles
- The Java ecosystem for its powerful backend tooling

---

## Repository

[https://github.com/thembaxaba157/Backend-GameZxne](https://github.com/thembaxaba157/Backend-GameZxne)


