# ms-users Service

This is the ms-users microservice for managing Persona and Cliente entities.

## Setup

1. Start PostgreSQL and Kafka using Docker Compose:
   ```bash
   docker-compose up -d
   ```

2. Run the application:
   ```bash
   ./gradlew bootRun
   ```

## API Documentation

Swagger UI is available at: http://localhost:8080/swagger-ui.html

## Endpoints

- **GET /personas** - Get all personas
- **GET /personas/{identificacion}** - Get persona by ID
- **POST /personas** - Create a new persona
- **PUT /personas/{identificacion}** - Update persona
- **DELETE /personas/{identificacion}** - Delete persona

- **GET /clientes** - Get all clientes
- **GET /clientes/{clienteId}** - Get cliente by ID
- **GET /clientes/estado/{estado}** - Get clientes by estado
- **POST /clientes** - Create a new cliente
- **PUT /clientes/{clienteId}** - Update cliente
- **DELETE /clientes/{clienteId}** - Delete cliente

## Profiles

- **dev**: Uses PostgreSQL database
- **test**: Uses H2 in-memory database for testing
