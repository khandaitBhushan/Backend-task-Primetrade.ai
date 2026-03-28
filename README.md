# Full-Stack Backend Intern Assignment

This project is a scalable REST API built with Java Spring Boot, MySQL, and a React frontend for task management, featuring JWT-based Authentication and Role-Based Access Control.

## Key Features
- **User Authentication**: Secure signup and login with hashed passwords and JWT.
- **Role-Based Access**: `ROLE_USER` and `ROLE_ADMIN` segregation.
- **Task Management**: CRUD operations for tasks (Tasks have statuses: PENDING, IN_PROGRESS, COMPLETED). Admins can view all Tasks, whereas Users can only manage their own Tasks.
- **Security**: JWT token validation, password encryption via bcrypt.

## Tech Stack
- **Backend**: Java 17, Spring Boot 3.2.x, Spring Security, Spring Data JPA, JWT.
- **Database**: MySQL.
- **Frontend**: React (Vite), TailwindCSS, Axios, React Router.

## API Documentation
The APIs are natively documented using Swagger UI.
`http://localhost:8080/swagger-ui.html`

## Running Locally

### Backend Setup
1. Navigate to the `/backend` folder.
2. Ensure you have MySQL running on port 3306 with credentials: `root` / `pass@123`. The database `intern_db` will be auto-created due to native configuration.
3. Build the backend using Maven: `./mvnw clean install` (or import in IDE and run).
4. Run the Spring Boot application `BackendApplication`. (Runs on port 8080).

### Frontend Setup
1. Navigate to the `/frontend` folder.
2. Install dependencies: `npm install`
3. Run the development server: `npm run dev`
4. Visit `http://localhost:5173`. Make sure the backend is fully booted first.

## Scalability & Security Note
To handle increased traffic or an expanded feature set in the future, the application can scale across multiple dimensions:
- **Stateless Authentication**: By using JWT, the application is already capable of horizontally scaling across multiple API instances.
- **Microservices Migration**: Features (like Auth and Tasks) could securely be segregated into microservices operating independently with API gateways handling routing and load balancing.
- **Caching**: Implementing a caching layer utilizing Redis for frequent GET API calls (like fetching user tasks) to significantly boost endpoint performance.
- **Load Balancing & Docker**: Containerizing the Spring Boot and React tiers utilizing Docker, enabling vertical horizontal scaling using Kubernetes/ECS along with load balancing for request clustering.
- **NoSQL / Read-Replicas**: Distributing the database loads via a Master-Slave replica.
