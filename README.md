# Servicio ms-users

Microservicio encargado de gestionar las entidades **Persona** y **Cliente** dentro del dominio financiero. Implementa una arquitectura en capas con Spring Boot, persiste en PostgreSQL mediante Spring Data JPA y publica eventos en Kafka para notificar a otros servicios.

## Tecnologías Principales
- Java 21 · Spring Boot 3.5.6
- Spring Web + Validation + Data JPA
- PostgreSQL (prod/dev) · H2 (tests)
- Spring Cloud Stream + Kafka
- Gradle · Docker

## Configuración y Puesta en Marcha
### Prerrequisitos
- Java 21
- Docker / Docker Compose

### Ejecución Local (perfil `dev`)
```bash
cd ms-users
./gradlew bootRun --args='--spring.profiles.active=dev'
```
La API quedará disponible en `http://localhost:8080` y la documentación en `http://localhost:8080/swagger-ui.html`.

### Ejecución con Docker (perfil `docker`)
1. Empaqueta el servicio:
   ```bash
   cd ms-users && ./gradlew bootJar
   ```
2. Desde la raíz del repositorio levanta toda la plataforma (PostgreSQL, Kafka, ms-users y ms-transactions):
   ```bash
   docker compose build
   docker compose up -d
   ```
   Los servicios usan la red interna (`postgres`, `kafka`) definida en `docker-compose.yml` y cargan el perfil `docker` automáticamente.
3. Si necesitas inspeccionar o recrear el esquema manualmente, utiliza `BaseDatos.sql` ubicado en la raíz del repositorio.

### Variables y Perfiles Disponibles
- `dev` (por defecto): PostgreSQL local (`localhost:5432`) con credenciales `postgres/secret`.
- `docker`: apuntan a los hosts de los contenedores (`postgres:5432`, `kafka:9092`).

## Endpoints Principales
### Personas
- `GET /personas`
- `GET /personas/{identificacion}`
- `POST /personas`
- `PUT /personas/{identificacion}`
- `DELETE /personas/{identificacion}`

### Clientes
- `GET /clientes`
- `GET /clientes/{clienteId}`
- `GET /clientes/estado/{estado}`
- `POST /clientes`
- `PUT /clientes/{clienteId}`
- `DELETE /clientes/{clienteId}`

## Eventos y Comunicación Asíncrona
- `ClienteCreatedEvent`: se publica en el *binding* `clienteCreated-out-0` (Kafka). El ms-transactions crea cuentas iniciales al consumir este evento.
- La publicación usa `StreamBridge` y lanza error si Kafka está caído; al estar el método envuelto en una transacción JPA, el registro del cliente se revierte ante un fallo en la mensajería.
- El binder define `auto.create.topics=true` para generar `cliente-created-topic` si no existe.

## Pruebas
Ejecuta todas las pruebas (unitarias, de controlador e integración):
```bash
cd ms-users
./gradlew test
```
El perfil `test` se aplica automáticamente durante el ciclo de pruebas.

## Estructura del Proyecto
```
src/main/java/com/sofka/ms_users/
├── controller       # Entradas REST
├── dto              # DTO de entrada/salida
├── event            # Eventos publicados
├── exception        # Manejo global de errores
├── model            # Entidades JPA (Persona, Cliente)
├── repository       # Repositorios Spring Data
└── service          # Reglas de negocio y orquestación
```