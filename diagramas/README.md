# Diagramas UML - BCI Project API REST

Esta carpeta contiene los diagramas UML del proyecto BCI Project API REST, cumpliendo con los estándares UML solicitados en las instrucciones del proyecto.

## 📁 Contenido

### 1. Diagrama de Componentes
- **Archivo**: `diagrama-componentes.md`
- **Descripción**: Muestra la arquitectura general del sistema con todas las capas y componentes
- **Estándar**: UML 2.0 - Component Diagram
- **Tecnología**: Mermaid Diagram

### 2. Diagrama de Secuencia
- **Archivo**: `diagrama-secuencia.md`
- **Descripción**: Ilustra el flujo de interacción durante el registro de usuarios
- **Estándar**: UML 2.0 - Sequence Diagram
- **Tecnología**: Mermaid Diagram

## 🎯 Propósito

Estos diagramas proporcionan:

- **Visión arquitectónica**: Estructura general del sistema
- **Flujo de procesos**: Secuencia de operaciones para casos de uso principales
- **Documentación técnica**: Referencia para desarrolladores
- **Cumplimiento de estándares**: Diagramas UML según especificaciones

## 📋 Especificaciones UML

### Diagrama de Componentes
- **Tipo**: UML Component Diagram
- **Elementos**: Componentes, Interfaces, Dependencias
- **Notación**: Estándar UML 2.0
- **Alcance**: Sistema completo

### Diagrama de Secuencia  
- **Tipo**: UML Sequence Diagram
- **Elementos**: Participantes, Mensajes, Activaciones, Fragmentos
- **Notación**: Estándar UML 2.0
- **Alcance**: Proceso de registro de usuario

## 🔧 Cómo Visualizar

### Opción 1: GitHub/GitLab
Los diagramas se renderizan automáticamente en plataformas que soporten Mermaid:
- GitHub
- GitLab
- Editores con soporte Mermaid

### Opción 2: Editores Locales
- Visual Studio Code con extensión Mermaid
- IntelliJ IDEA con plugin Mermaid
- Cualquier editor con soporte Markdown + Mermaid

### Opción 3: Herramientas Online
- [Mermaid Live Editor](https://mermaid.live/)
- [GitHub Mermaid](https://github.blog/2022-02-14-include-diagrams-markdown-files-mermaid/)

## 🏗️ Arquitectura Documentada

### Capas del Sistema
1. **Presentación**: Controllers, Exception Handlers, Configuration
2. **Servicios**: Business Logic, JWT Management
3. **Persistencia**: Repositories, Database Access
4. **Entidades**: JPA Entities, Domain Models
5. **Utilidades**: Mappers, Encryption, Helper Classes

### Patrones Implementados
- **Layered Architecture**: Separación en capas
- **DTO Pattern**: Transferencia de datos
- **Repository Pattern**: Acceso a datos
- **Service Layer**: Lógica de negocio
- **Exception Handling**: Manejo centralizado de errores

## 📚 Referencias

- [UML 2.0 Specification](https://www.omg.org/spec/UML/2.0/)
- [Mermaid Documentation](https://mermaid-js.github.io/mermaid/)
- [Spring Boot Architecture](https://spring.io/projects/spring-boot)

---

**Autor**: BCI Team  
**Fecha**: Diciembre 2024  
**Versión**: 1.0.0 