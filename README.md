# BCI Project

Proyecto base de Spring Boot 2.5.14 con Gradle 7.4 y Java 11.

## Requisitos

- Java 11 o superior
- No es necesario instalar Gradle (se incluye el wrapper)

## Estructura del Proyecto

```
bci-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── cl/
│   │   │       └── bci/
│   │   │           └── ejercicio/
│   │   │               ├── BciProjectApplication.java
│   │   │               ├── config/
│   │   │               │   └── SecurityConfig.java
│   │   │               └── controller/
│   │   │                   └── HelloController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── cl/
│               └── bci/
│                   └── ejercicio/
│                       └── BciProjectApplicationTests.java
├── gradle/
│   └── wrapper/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
└── gradlew.bat
```

## Dependencias Incluidas

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Security
- H2 Database (para desarrollo)
- Lombok
- Spring Boot DevTools

## Comandos Útiles

### Ejecutar la aplicación
```bash
# En Windows
gradlew.bat bootRun

# En Linux/Mac
./gradlew bootRun
```

### Ejecutar pruebas
```bash
# En Windows
gradlew.bat test

# En Linux/Mac
./gradlew test
```

### Construir el proyecto
```bash
# En Windows
gradlew.bat build

# En Linux/Mac
./gradlew build
```

### Limpiar el proyecto
```bash
# En Windows
gradlew.bat clean

# En Linux/Mac
./gradlew clean
```

## Configuración

La aplicación está configurada para:
- Ejecutarse en el puerto 8080
- Usar base de datos H2 en memoria
- Mostrar queries SQL en los logs
- Consola H2 habilitada en `/h2-console`

## Endpoints Disponibles

### Endpoints del Sistema de Usuarios
- `POST /api/sign-up` - Registro de nuevos usuarios
- `POST /api/login` - Autenticación de usuarios existentes

### Endpoints de Desarrollo
- `GET /h2-console` - Consola de base de datos H2

## Estructura de Datos

### Sign-Up Request
```json
{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "Hunter12",
  "phones": [
    {
      "number": 87650009,
      "citycode": 7,
      "contrycode": "25"
    }
  ]
}
```

### Login Request
```json
{
  "email": "juan@rodriguez.org",
  "password": "Hunter12"
}
```

### User Response
```json
{
  "id": "uuid-generado",
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "phones": [
    {
      "number": 87650009,
      "citycode": 7,
      "contrycode": "25"
    }
  ],
  "created": "Nov 16, 2021 12:51:43 PM",
  "lastLogin": "Nov 16, 2021 12:51:43 PM",
  "token": "jwt-token-generado",
  "isActive": true
}
```

### Error Response
```json
{
  "timestamp": "Nov 16, 2021 12:51:43 PM",
  "codigo": 400,
  "detail": "Mensaje de error específico"
}
```

## Validaciones

### Email
- Debe seguir el formato: `aaaaaaa@undominio.algo`
- Expresión regular: `^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$`

### Password
- Debe tener una mayúscula
- Debe tener letras minúsculas
- Debe tener exactamente 2 números (no necesariamente consecutivos)
- Longitud mínima: 8 caracteres
- Longitud máxima: 12 caracteres
- Expresión regular: `^(?=.*[a-z])(?=.*[A-Z])(?=.*\d{2})[a-zA-Z\d]{8,12}$`

## Funcionalidades Implementadas

1. **Registro de Usuarios** (`/api/sign-up`)
   - Validación de formato de email y contraseña
   - Verificación de email único
   - Encriptación de contraseña con BCrypt
   - Generación de token JWT
   - Persistencia en base de datos H2

2. **Autenticación** (`/api/login`)
   - Validación de credenciales
   - Verificación de usuario activo
   - Generación de nuevo token JWT
   - Actualización de lastLogin

3. **Manejo de Errores**
   - Respuestas de error en formato JSON
   - Validaciones de entrada
   - Mensajes de error específicos

4. **Seguridad**
   - Tokens JWT para autenticación
   - Contraseñas encriptadas
   - Configuración de seguridad Spring

## Notas

- La configuración de seguridad permite acceso público a los endpoints de ejemplo
- La base de datos H2 se recrea en cada inicio de la aplicación
- Los logs están configurados en nivel DEBUG para el paquete `cl.bci` 