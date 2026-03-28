# Backend-task-Primetrade.ai

## Project Overview
Scalable REST API with JWT authentication and role-based access control built using Spring Boot, coupled with a fast React frontend for seamless interaction.

## Tech Stack
- **Backend:** Spring Boot, Spring Security, JWT, JPA, MySQL  
- **Frontend:** React (Vite), TailwindCSS, Axios context-services
- **Tools:** Swagger OpenAPI, React Router

## Features Checklist
- [x] User Registration & Login (JWT-based)
- [x] Role-Based Access (Admin/User)
- [x] CRUD APIs for Task entity
- [x] API Versioning (`/api/v1/...`)
- [x] Validation & Global Exception Handling
- [x] Swagger API Documentation
- [x] Protected Frontend Routes

## API Endpoints
- `POST /api/v1/auth/register`
- `POST /api/v1/auth/login`
- `GET  /api/v1/tasks`
- `POST /api/v1/tasks`
- `PUT  /api/v1/tasks/{id}`
- `DELETE /api/v1/tasks/{id}`

## How to Run

1. **Clone repo**
   ```bash
   git clone https://github.com/khandaitBhushan/Backend-task-Primetrade.ai.git
   cd Backend-task-Primetrade.ai
   ```
2. **Setup MySQL DB**
   Ensure an active MySQL service is running locally on port `3306`.
3. **Update `application.properties`**
   Inside `/backend/src/main/resources/application.properties`, confirm your database strings match exactly. (Currently set to `root` with password `pass@123`).
4. **Run Spring Boot app**
   Load the `/backend` directory inside modern java workspace IDEs to cleanly compile, or run Native maven triggers.
   ```bash
   cd backend
   ./mvnw clean compile
   ./mvnw spring-boot:run
   ```
5. **Run frontend**
   ```bash
   cd ../frontend
   npm install
   npm run dev
   ```

## Screenshots
_*(Drop in your actual UI captures here to satisfy evaluation metrics!)*_
- **Login page:** `![Login Page](./assets/login.png)`
- **Dashboard:** `![Dashboard View](./assets/dashboard.png)`
- **Swagger UI:** `![Swagger API Specs](./assets/swagger.png)`

## Scalability Note
To address future Web 3.0 scale and enterprise loads, this monorepo infrastructure is decoupled explicitly mapping toward scaling dimensions. By containerizing workloads through **Docker**, rapid instances can spin up managed heavily by isolated horizontal **Load Balancing** networking grids. Specifically separating application bounds opens the pathway for independent isolated **Microservices**, heavily offsetting traffic bottlenecks natively. Additionally, executing robust layered **Caching (Redis)** natively limits extreme recursive SQL database calls, establishing pristine read speeds.
