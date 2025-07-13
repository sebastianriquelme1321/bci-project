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
- **Contacto:** BCI Team (soporte@bci.cl)

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

## Funcionalidades interactivas

### Probar endpoints
1. Haz clic en cualquier endpoint para expandirlo
2. Presiona el botón "Try it out"
3. Completa los parámetros requeridos
4. Ejecuta la petición con "Execute"
5. Ve la respuesta completa con headers y body

### Autorización
Para endpoints que requieren autenticación:
1. Obtén un token del endpoint de sign-up o login
2. Úsalo en el header "token" para peticiones autenticadas

## Configuración adicional

### Personalizar la documentación

La configuración de Swagger se encuentra en:
```
src/main/java/cl/bci/ejercicio/config/SwaggerConfig.java
```

### Agregar más documentación

Para documentar nuevos endpoints:
1. Agrega `@Api` a la clase del controlador
2. Usa `@ApiOperation` para describir métodos
3. Incluye `@ApiParam` para parámetros
4. Agrega `@ApiModel` y `@ApiModelProperty` a DTOs

### Configuración avanzada

Puedes personalizar:
- Información de contacto
- Licencias
- Servidores base
- Configuraciones de seguridad
- Filtros de endpoints

## Resolución de problemas

### La documentación no carga
1. Verifica que la aplicación esté ejecutándose en el puerto 8080
2. Asegúrate de acceder a la URL correcta
3. Revisa los logs por errores de configuración

### Endpoints no aparecen
1. Verifica que los controladores estén en el paquete `cl.bci.ejercicio.controller`
2. Confirma que las rutas coincidan con el patrón `/v1/bci.*`
3. Asegúrate de que las anotaciones de Swagger estén presentes

### Errores de validación
1. Revisa que todos los DTOs tengan las anotaciones correctas
2. Verifica la sintaxis de las expresiones regulares
3. Confirma que los ejemplos en la documentación sean válidos

## Beneficios de usar Swagger

1. **Documentación automática:** Se actualiza con el código
2. **Interfaz interactiva:** Permite probar la API directamente
3. **Estandarización:** Sigue el estándar OpenAPI
4. **Colaboración:** Facilita el trabajo entre equipos
5. **Validación:** Ayuda a detectar inconsistencias en la API 