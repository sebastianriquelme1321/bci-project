# BCI Project - API REST de Gestión de Usuarios

API REST desarrollada con Spring Boot 2.5.14, Gradle 7.4 y Java 11 para la gestión de usuarios con autenticación JWT.


## 📋 Requisitos

- Java 11 o superior
- No es necesario instalar Gradle (se incluye Gradle Wrapper 7.4)

## 🚀 Inicio Rápido

### 1. Clonar y ejecutar
```bash
git clone <repository-url>
cd bci-project
./gradlew bootRun
```

### 2. Acceder a la documentación
- **🔍 Swagger UI:** http://localhost:8080/swagger-ui/
- **🗄️ H2 Console:** http://localhost:8080/h2-console

## 📦 Dependencias Principales

- Spring Boot 2.5.14 (Web, JPA, Validation)
- H2 Database, Lombok, JJWT 0.9.1
- Swagger/OpenAPI 3.0.0
- JUnit 5.8.2, Mockito, Jacoco

## 🛠️ Comandos Útiles

```bash
# Ejecutar aplicación
./gradlew bootRun

# Ejecutar tests y generar reportes
./gradlew test jacocoTestReport

# Construir proyecto
./gradlew build
```

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

## 📚 Documentación

### Swagger/OpenAPI
- **Interfaz interactiva:** http://localhost:8080/swagger-ui/
- **Documentación completa:** `SWAGGER_DOCUMENTATION.md`

### Diagramas UML
- **📁 Carpeta de diagramas:** `diagramas/`
- **🏗️ Diagrama de Componentes:** `diagramas/diagrama-componentes.md`
- **🔄 Diagrama de Secuencia:** `diagramas/diagrama-secuencia.md`
- **📖 Documentación de diagramas:** `diagramas/README.md`

### JavaDoc
Todas las clases incluyen documentación JavaDoc completa con descripción, parámetros y ejemplos.

## ⚙️ Configuración

### Base de Datos H2
- **URL:** `jdbc:h2:mem:testdb` | **Usuario:** `sa` | **Contraseña:** (vacía)
- **Consola:** http://localhost:8080/h2-console

### JWT
- **Expiración:** 8 horas | **Algoritmo:** HS512
- **Secret:** Configurable via `jwt.secret`

## 🚨 Códigos de Respuesta

- **200:** Login exitoso
- **201:** Usuario registrado
- **400:** Datos inválidos o email existente
- **404:** Usuario no encontrado
- **500:** Error del servidor