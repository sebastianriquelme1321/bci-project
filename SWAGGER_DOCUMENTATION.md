# Documentación de API con Swagger

## Introducción

Este proyecto incluye documentación automática de la API REST utilizando Swagger/OpenAPI. La documentación proporciona una interfaz interactiva para explorar y probar todos los endpoints disponibles.

## Cómo acceder a la documentación

### 1. Iniciar la aplicación

Primero, ejecuta la aplicación Spring Boot:

```bash
./gradlew bootRun
```

O usando el JAR compilado:

```bash
java -jar build/libs/bci-project-0.0.1-SNAPSHOT.jar
```

### 2. Acceder a Swagger UI

Una vez que la aplicación esté en funcionamiento, puedes acceder a la documentación interactiva en:

**URL principal de Swagger UI:**
```
http://localhost:8080/swagger-ui/
```

**URL alternativa:**
```
http://localhost:8080/swagger-ui/index.html
```

### 3. Documentación JSON de la API

Para obtener la especificación completa de la API en formato JSON:

```
http://localhost:8080/v2/api-docs
```

## Características de la documentación

### Información general
- **Título:** BCI API REST
- **Versión:** 1.0.0
- **Descripción:** API REST para gestión de usuarios del proyecto BCI

### Endpoints documentados

#### 1. POST /v1/bci/sign-up
- **Descripción:** Registrar nuevo usuario
- **Parámetros:** SignUpRequestDto (en el body)
- **Respuesta exitosa:** 201 Created con SignUpResponseDto
- **Validaciones:**
  - Email único y formato válido
  - Contraseña con 1 mayúscula, 2 números, 8-12 caracteres
  - Nombre obligatorio

#### 2. POST /v1/bci/login
- **Descripción:** Login de usuario
- **Parámetros:** Token JWT (en el header)
- **Respuesta exitosa:** 200 OK con UserResponseDto
- **Validaciones:**
  - Token JWT válido
  - Usuario existente

### Modelos de datos documentados

Todos los DTOs incluyen:
- **SignUpRequestDto:** Datos para registro de usuario
- **SignUpResponseDto:** Respuesta del registro exitoso
- **UserResponseDto:** Información completa del usuario
- **PhoneDto:** Información de teléfono
- **ApiErrorResponseDto:** Formato de errores

### Ejemplos de uso

La documentación de Swagger incluye:
- Ejemplos de peticiones para cada endpoint
- Formatos esperados de datos
- Códigos de respuesta posibles
- Mensajes de error detallados
- Esquemas de datos completos
