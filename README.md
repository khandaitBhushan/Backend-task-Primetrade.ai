# PrimeTrade.ai - Backend Developer Intern Task

A robust, enterprise-grade, scalable REST API supporting JWT authentication, Role-Based Access Control (RBAC), and full stateless CRUD management. Built using modern Java Spring Boot with a connected React UI, designed specifically to meet the high-throughput architecture demands of Web3 trading intelligence.

## 🚀 Tech Stack Highlights
* **Core Backend**: Java 17, Spring Boot 3.x, Spring Data JPA
* **Security & Auth**: Spring Security, JWT (JSON Web Tokens), BCrypt Password Hashing
* **Database**: MySQL 
* **Frontend UI**: React (Vite), TailwindCSS, Axios Interceptors, Context API
* **API Documentation**: OpenAPI / Swagger UI

## ✨ Core Implemented Features
- **User Authentication**: Secure `/api/v1/auth/register` and `/login` endpoints utilizing stateless JWT access tokens.
- **Role-Based Routing**: Strict hierarchical API authorization isolating logic between `ROLE_ADMIN` (global privileges) and `ROLE_USER` (localized bounds).
- **Secondary Entity CRUD Operations**: Implemented `Task` entity endpoints (GET, POST, PUT, DELETE) handling user-specific task routing safely restricting cross-account data leaks. 
- **Global Error Handling**: Centralized exception mapping via `@ControllerAdvice` managing input validation failures smoothly and cleanly.
- **Dynamic API Versioning**: All controllers routed explicitly on `/api/v1/*`.
- **Integrated React UI**: A fully functional React interface bridging strictly to the APIs, complete with protected dashboards preventing unauthenticated access. 

## 🌐 API Endpoints (Swagger UI) 
The API is completely documented natively using Swagger. Once the backend boots up, you can interact with every endpoint interactively at:
👉 `http://localhost:8080/swagger-ui.html`

### Quick API Map:
| HTTP Method | Endpoint | Description | Auth Required |
| ----------- | ----------- | ----------- | ----------- |
| `POST` | `/api/v1/auth/register` | Register new user account | No |
| `POST` | `/api/v1/auth/login` | Authenticate & return JWT | No |
| `GET`  | `/api/v1/tasks` | Fetch tasks (All for Admin, User-specific for User) | Yes (JWT) |
| `POST` | `/api/v1/tasks` | Create a new task | Yes (JWT) |
| `PUT`  | `/api/v1/tasks/{id}` | Update an existing task | Yes (JWT) |
| `DELETE` | `/api/v1/tasks/{id}` | Permanently delete a task | Yes (JWT) |

## 🛠️ How to Boot & Run Locally

### 1. Database Configuration
Ensure you have MySQL installed and running natively on port `3306`.
A database named `intern_db` will automatically initialize upon booting the backend.
- **User**: `root`
- **Password**: `pass@123`
*(If your credentials differ, update the `spring.datasource` strings located inside `/backend/src/main/resources/application.properties` prior to booting)*.

### 2. Backend Boot (Spring Boot)
Open the `/backend` directory within your terminal. The Maven wrapper is provided for zero-dependency execution.
```bash
cd backend
./mvnw clean compile
./mvnw spring-boot:run
```
*(The backend process will spin up and securely bind to port `8080`)*

### 3. Frontend UI Boot (React)
Open a new terminal tab, routing to the `/frontend` directory. 
```bash
cd frontend
npm install
npm run dev
```
*(The UI server will spin up on port `5173`. Open your browser to `http://localhost:5173`)*

## 📈 Scalability & Architecture Note (Evaluation Metrics)
Designing for the immense load throughput required in crypto/Web3 networks mandates scalable horizons. This codebase is fully adapted and prepared for massive scaling:
1. **Stateless JWT Protocols**: By persisting no session data locally on the Spring Server, this monolithic repository can be safely split and scaled **horizontally** utilizing Kubernetes pods alongside any cloud Load Balancer.
2. **Microservices Ready**: The code strictly isolates logic into `/entity`, `/service` and `/controller` packages, drastically simplifying breaking apart the `Auth` system and the `Tasks` system into totally independent, segregated microservices routed through an API Gateway.
3. **Caching Integrations**: Integrating extreme-speed caching layers via **Redis** would fundamentally boost GET `/tasks` fetching performance preventing sequential bottlenecking onto the MySQL layer entirely.
4. **Docker Containerization**: The React and Spring layers are isolated into dedicated module definitions, cleanly allowing isolated Docker image formulation for isolated EC2 cluster deployments.
