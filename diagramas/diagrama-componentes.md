# Diagrama de Componentes - BCI Project API REST

Este diagrama muestra la arquitectura de componentes del sistema BCI Project API REST, incluyendo las diferentes capas y sus relaciones.

## Descripción de Componentes

### Capa de Presentación
- **UserController**: Controlador REST que maneja las peticiones HTTP
- **GlobalExceptionHandler**: Maneja las excepciones globalmente
- **SwaggerConfig**: Configuración para documentación de API

### Capa de Servicios
- **UserService**: Contiene la lógica de negocio para gestión de usuarios
- **JwtService**: Maneja la generación y validación de tokens JWT

### Capa de Persistencia
- **UserRepository**: Interfaz de acceso a datos usando Spring Data JPA

### Capa de Entidades
- **User**: Entidad JPA que representa un usuario
- **Phone**: Entidad JPA que representa un teléfono

### DTOs
- **SignUpRequestDto**: DTO para solicitud de registro
- **SignUpResponseDto**: DTO para respuesta de registro
- **UserResponseDto**: DTO para respuesta de usuario
- **PhoneDto**: DTO para teléfono

### Utilidades
- **AESUtil**: Utilidad para encriptación de contraseñas
- **UserMapper**: Mapeo entre entidades User y DTOs
- **PhoneMapper**: Mapeo entre entidades Phone y DTOs

```mermaid
graph TB
    subgraph "Capa de Presentación"
        UC[UserController<br/>REST Controller]
        EH[GlobalExceptionHandler<br/>Exception Handler]
        SC[SwaggerConfig<br/>API Documentation]
    end
    
    subgraph "Capa de Servicios"
        US[UserService<br/>Business Logic]
        JS[JwtService<br/>Token Management]
    end
    
    subgraph "Capa de Persistencia"
        UR[UserRepository<br/>Data Access]
    end
    
    subgraph "Capa de Entidades"
        UE[User<br/>JPA Entity]
        PE[Phone<br/>JPA Entity]
    end
    
    subgraph "DTOs"
        SRD[SignUpRequestDto<br/>Request DTO]
        SRPD[SignUpResponseDto<br/>Response DTO]
        URD[UserResponseDto<br/>Response DTO]
        PD[PhoneDto<br/>Phone DTO]
    end
    
    subgraph "Utilidades"
        AES[AESUtil<br/>Encryption Utility]
        UM[UserMapper<br/>Object Mapping]
        PM[PhoneMapper<br/>Object Mapping]
    end
    
    subgraph "Base de Datos"
        H2[(H2 Database<br/>In-Memory)]
    end
    
    UC -->|"llama"| US
    UC -->|"usa"| SRD
    UC -->|"retorna"| SRPD
    UC -->|"retorna"| URD
    UC -->|"maneja errores"| EH
    
    US -->|"genera token"| JS
    US -->|"accede datos"| UR
    US -->|"encripta"| AES
    US -->|"mapea objetos"| UM
    US -->|"mapea teléfonos"| PM
    
    UR -->|"persistencia"| H2
    UR -->|"mapea entidad"| UE
    
    UE -->|"relación 1:N"| PE
    
    PM -->|"mapea"| PD
    UM -->|"mapea"| UE
    
    style UC fill:#e1f5fe
    style US fill:#e8f5e8
    style UR fill:#fff3e0
    style H2 fill:#fce4ec
```

## Patrones de Arquitectura

El sistema sigue una arquitectura en capas (Layered Architecture):

1. **Capa de Presentación**: Maneja la interfaz REST y documentación
2. **Capa de Servicios**: Contiene la lógica de negocio
3. **Capa de Persistencia**: Acceso a datos
4. **Capa de Entidades**: Modelo de dominio

## Tecnologías Utilizadas

- **Spring Boot 2.5.14**: Framework principal
- **Spring Data JPA**: Para persistencia
- **H2 Database**: Base de datos en memoria
- **JWT**: Para autenticación
- **Swagger/OpenAPI**: Para documentación
- **Lombok**: Para reducir código repetitivo 