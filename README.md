# Servicio ms-users

Este es el microservicio ms-users para la gestión de las entidades Persona y Cliente en el sistema financiero.

## Descripción

El servicio ms-users implementa una arquitectura en capas y maneja las operaciones CRUD para las entidades Persona y Cliente. Utiliza Spring Boot con JPA para la persistencia de datos y Kafka para la comunicación asíncrona con otros servicios.

## Tecnologías

- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- PostgreSQL
- Kafka
- Gradle
- Docker

## Configuración

### Prerrequisitos

- Java 21 instalado
- Docker y Docker Compose
- Gradle (incluido en el proyecto)

### Base de Datos

1. Inicia PostgreSQL y Kafka usando Docker Compose desde el directorio raíz del proyecto:
   ```bash
   docker-compose up -d
   ```

2. El servicio se conectará automáticamente a la base de datos PostgreSQL configurada.

### Ejecución

1. Ejecuta la aplicación:
   ```bash
   ./gradlew bootRun
   ```

2. La aplicación estará disponible en: http://localhost:8080

## Documentación de la API

La documentación de la API está disponible en Swagger UI: http://localhost:8080/swagger-ui.html

## Endpoints

### Persona

- **GET /personas** - Obtener todas las personas
- **GET /personas/{identificacion}** - Obtener persona por identificación
- **POST /personas** - Crear una nueva persona
- **PUT /personas/{identificacion}** - Actualizar persona
- **DELETE /personas/{identificacion}** - Eliminar persona

### Cliente

- **GET /clientes** - Obtener todos los clientes
- **GET /clientes/{clienteId}** - Obtener cliente por ID
- **GET /clientes/estado/{estado}** - Obtener clientes por estado
- **POST /clientes** - Crear un nuevo cliente
- **PUT /clientes/{clienteId}** - Actualizar cliente
- **DELETE /clientes/{clienteId}** - Eliminar cliente

## Perfiles de Configuración

- **dev**: Utiliza base de datos PostgreSQL para desarrollo
- **test**: Utiliza base de datos H2 en memoria para pruebas

Para ejecutar con un perfil específico:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

## Pruebas

Ejecuta las pruebas unitarias e de integración:
```bash
./gradlew test
```

## Eventos

El servicio publica eventos a través de Kafka cuando se crean clientes:
- **ClienteCreatedEvent**: Se publica cuando se crea un nuevo cliente

## Estructura del Proyecto

```
src/main/java/com/sofka/ms_users/
├── controller/     # Controladores REST
├── dto/           # Objetos de transferencia de datos
├── event/         # Eventos para comunicación asíncrona
├── exception/     # Manejo de excepciones
├── model/         # Entidades JPA
├── repository/    # Repositorios de datos
└── service/       # Lógica de negocio
```

## Despliegue

Para desplegar en producción, asegúrate de:
1. Configurar las variables de entorno para la base de datos
2. Usar HTTPS
3. Configurar autenticación JWT si es necesario
4. Ajustar los límites de recursos según sea necesario