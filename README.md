# BCI Project - API REST de Gestión de Usuarios

API REST desarrollada con Spring Boot 2.5.14, Gradle 7.4 y Java 11 para la gestión de usuarios con autenticación JWT.

## 🚀 Características Principales

- ✅ API REST completa para gestión de usuarios
- ✅ Autenticación con tokens JWT
- ✅ Validaciones avanzadas de email y contraseña
- ✅ Documentación automática con Swagger/OpenAPI
- ✅ Tests unitarios con 100% de cobertura
- ✅ JavaDoc completo en todas las clases
- ✅ Manejo global de excepciones
- ✅ Base de datos H2 en memoria

## 📋 Requisitos

- Java 11 o superior
- No es necesario instalar Gradle (se incluye Gradle Wrapper 7.4)

## 🏗️ Estructura del Proyecto

```
bci-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── cl/bci/ejercicio/
│   │   │       ├── BciProjectApplication.java
│   │   │       ├── config/
│   │   │       │   └── SwaggerConfig.java
│   │   │       ├── controller/
│   │   │       │   └── UserController.java
│   │   │       ├── dto/
│   │   │       │   ├── SignUpRequestDto.java
│   │   │       │   ├── SignUpResponseDto.java
│   │   │       │   ├── UserResponseDto.java
│   │   │       │   ├── PhoneDto.java
│   │   │       │   └── ApiErrorResponseDto.java
│   │   │       ├── entity/
│   │   │       │   ├── User.java
│   │   │       │   └── Phone.java
│   │   │       ├── exception/
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   ├── UserAlReadyExist.java
│   │   │       │   └── UserNotFoundException.java
│   │   │       ├── repository/
│   │   │       │   └── UserRepository.java
│   │   │       ├── service/
│   │   │       │   ├── UserService.java
│   │   │       │   └── JwtService.java
│   │   │       └── utils/
│   │   │           ├── UserMapper.java
│   │   │           └── PhoneMapper.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── cl/bci/ejercicio/
│               ├── controller/
│               ├── service/
│               ├── exception/
│               └── utils/
├── build/
│   └── reports/
│       ├── tests/
│       └── jacoco/
├── SWAGGER_DOCUMENTATION.md
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
└── gradlew.bat
```

## 📦 Dependencias Incluidas

### Core Dependencies
- Spring Boot Starter Web 2.5.14
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- H2 Database (en memoria)
- Lombok 1.18.30

### Security & JWT
- JJWT 0.9.1 (JSON Web Token)

### Documentation
- Swagger/SpringFox 3.0.0
- Swagger UI 3.0.0

### Testing
- Spring Boot Starter Test
- JUnit 5.8.2
- Mockito
- Jacoco (cobertura de código)

## 🚀 Inicio Rápido

### 1. Clonar el repositorio
```bash
git clone <repository-url>
cd bci-project
```

### 2. Ejecutar la aplicación
```bash
# En Windows
gradlew.bat bootRun

# En Linux/Mac
./gradlew bootRun
```

### 3. Acceder a la documentación
Una vez iniciada la aplicación, accede a:
- **Swagger UI:** http://localhost:8080/swagger-ui/
- **H2 Console:** http://localhost:8080/h2-console

## 🛠️ Comandos Útiles

### Desarrollo
```bash
# Ejecutar la aplicación
./gradlew bootRun

# Construir el proyecto
./gradlew build

# Limpiar el proyecto
./gradlew clean

# Ejecutar en modo continuo
./gradlew bootRun --continuous
```

### Testing y Calidad
```bash
# Ejecutar todos los tests
./gradlew test

# Generar reporte de cobertura
./gradlew test jacocoTestReport

# Ejecutar tests con reporte
./gradlew clean test jacocoTestReport
```

### Ver Reportes
- **Tests:** `build/reports/tests/test/index.html`
- **Cobertura:** `build/reports/jacoco/test/html/index.html`

## 🌐 Endpoints de la API

### Registro de Usuario
```http
POST /v1/bci/sign-up
Content-Type: application/json

{
  "name": "Juan Pérez",
  "email": "juan.perez@bci.cl",
  "password": "Password12",
  "phones": [
    {
      "number": 123456789,
      "citycode": 1,
      "contrycode": "57"
    }
  ]
}
```

### Login de Usuario
```http
POST /v1/bci/login
token: <jwt-token>
```

## 📊 Estructura de Datos

### SignUpRequestDto
```json
{
  "name": "Juan Pérez",
  "email": "juan.perez@bci.cl",
  "password": "Password12",
  "phones": [
    {
      "number": 123456789,
      "citycode": 1,
      "contrycode": "57"
    }
  ]
}
```

### SignUpResponseDto
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "created": "2023-12-07T10:30:00",
  "lastLogin": "2023-12-07T10:30:00",
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "isActive": true
}
```

### UserResponseDto
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Juan Pérez",
  "email": "juan.perez@bci.cl",
  "phones": [
    {
      "number": 123456789,
      "citycode": 1,
      "contrycode": "57"
    }
  ],
  "created": "2023-12-07T10:30:00",
  "lastLogin": "2023-12-07T15:45:00",
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "isActive": true,
  "password": "Password12"
}
```

### Error Response
```json
{
  "timestamp": "2023-12-07T10:30:00.000Z",
  "codigo": 400,
  "detail": "Ya existe un usuario activo registrado con el mismo email"
}
```

## ✅ Validaciones

### Email
- Formato válido de email
- Debe ser único en el sistema
- Expresión regular: `^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$`

### Contraseña
- **Exactamente 1 letra mayúscula**
- **Exactamente 2 números** (no necesariamente consecutivos)
- **Letras minúsculas**
- **Longitud:** entre 8 y 12 caracteres
- Expresión regular: `^(?=(?:[^A-Z]*[A-Z]){1}[^A-Z]*$)(?=(?:[^\\d]*\\d){2}[^\\d]*$)[A-Za-z\\d]{8,12}$`

#### Ejemplos válidos:
- `Password12` ✅
- `Testpass12` ✅  
- `Mypass123` ✅

#### Ejemplos inválidos:
- `password12` ❌ (sin mayúscula)
- `PASSWORD12` ❌ (sin minúscula)
- `Password1` ❌ (solo 1 número)
- `Password123` ❌ (3 números)

## 🧪 Testing

### Cobertura de Código
El proyecto incluye tests unitarios completos con **100% de cobertura** siguiendo el patrón **AAA (Arrange-Act-Assert)**:

- **UserServiceTest:** Tests del servicio de usuarios
- **UserControllerTest:** Tests del controlador REST
- **JwtServiceTest:** Tests del servicio JWT
- **GlobalExceptionHandlerTest:** Tests del manejo de excepciones
- **UserMapperTest:** Tests del mapper de usuarios
- **PhoneMapperTest:** Tests del mapper de teléfonos
- **ExceptionTests:** Tests de las excepciones customizadas

### Ejecutar Tests
```bash
# Ejecutar todos los tests
./gradlew test

# Ver reporte de cobertura
./gradlew jacocoTestReport
# Abrir: build/reports/jacoco/test/html/index.html
```

## 📚 Documentación

### Swagger/OpenAPI
La API está completamente documentada con Swagger:
- **URL:** http://localhost:8080/swagger-ui/
- **JSON:** http://localhost:8080/v2/api-docs

Ver `SWAGGER_DOCUMENTATION.md` para más detalles.

### JavaDoc
Todas las clases incluyen documentación JavaDoc completa:
- Descripción de clases y métodos
- Parámetros y valores de retorno
- Excepciones lanzadas
- Ejemplos de uso

## 🏗️ Arquitectura

### Patrón de Capas
- **Controller:** Endpoints REST con validaciones
- **Service:** Lógica de negocio
- **Repository:** Acceso a datos
- **DTO:** Objetos de transferencia
- **Entity:** Entidades JPA
- **Utils:** Mappers y utilidades

### Patrones Implementados
- **Repository Pattern:** Para acceso a datos
- **DTO Pattern:** Para transferencia de datos
- **Builder Pattern:** Para construcción de objetos
- **Exception Handler:** Para manejo centralizado de errores

## ⚙️ Configuración

### Base de Datos H2
- **URL:** `jdbc:h2:mem:testdb`
- **Usuario:** `sa`
- **Contraseña:** (vacía)
- **Consola:** http://localhost:8080/h2-console

### JWT
- **Secret:** Configurable via `jwt.secret`
- **Expiración:** 24 horas (configurable via `jwt.expiration`)
- **Algoritmo:** HS512

### Puerto
- **Puerto por defecto:** 8080
- **Configurable via:** `server.port`

## 🚨 Manejo de Errores

### Códigos de Respuesta
- **200:** OK - Login exitoso
- **201:** Created - Usuario registrado
- **400:** Bad Request - Datos inválidos o email existente
- **404:** Not Found - Usuario no encontrado
- **500:** Internal Server Error - Error del servidor

### Excepciones Manejadas
- `UserAlReadyExist`: Email ya registrado
- `UserNotFoundException`: Usuario no encontrado
- `MethodArgumentNotValidException`: Validaciones fallidas
- `ConstraintViolationException`: Restricciones violadas
- `Exception`: Errores genéricos

## 📝 Notas Importantes

- La base de datos H2 se recrea en cada reinicio
- Los tokens JWT expiran en 24 horas
- Todas las contraseñas se almacenan en texto plano (para propósitos de demostración)
- La aplicación está configurada para desarrollo, no para producción
- Los tests utilizan mocks para aislar las capas
- La documentación de Swagger se genera automáticamente del código 