# El Torneo Galáctico - Backend API (Galactic Repository)

Esta es la API RESTful diseñada para gestionar el Gran Torneo Galáctico. Este microservicio se encarga de la persistencia de datos, el registro de especies contendientes, la lógica de simulación de combates y la generación del ranking oficial del torneo.

## Tecnologías y Arquitectura
Este proyecto ha sido desarrollado siguiendo buenas prácticas de arquitectura limpia y principios SOLID.

* **Framework:** Java 21 + Spring Boot 3.x
* **Base de Datos:** PostgreSQL (Relacional)
* **ORM:** Spring Data JPA / Hibernate
* **Construcción:** Maven Wrapper
* **Contenedores:** Docker y Docker Compose (Multi-stage build)
* **Testing:** JUnit 5 y Mockito

---

## Requisitos Previos

Para ejecutar este proyecto de la forma más rápida y limpia, solo necesitas tener instalado:
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) (o Docker Engine + Docker Compose).

*(Opcional para desarrollo manual sin Docker: Java 21 y PostgreSQL).*

---

## Instrucciones de Instalación y Ejecución

### Opción A: Ejecución mediante Docker (Recomendado)
El proyecto cuenta con un entorno Dockerizado completo que levanta tanto la base de datos PostgreSQL como la API de Spring Boot de forma automática, sin necesidad de instalar Java en tu máquina local.

1. Clona este repositorio:
   ```bash
   git clone [https://github.com/AnthonyDOM164/galactic-repository-api.git](https://github.com/AnthonyDOM164/galactic-repository-api.git)
   cd galactic-repository-api
   ```
2. Ejecuta Docker Compose:
   ```bash
   docker-compose up --build
   ```
Nota: La primera vez descargará las imágenes de Java y Postgres, y compilará el proyecto usando el Dockerfile.local (Multi-stage).
3. La API estará disponible en: http://localhost:8080

**Opción B: Ejecución Manual (Local)**

Si prefieres correrlo sin Docker:
- Asegúrate de tener una instancia de PostgreSQL corriendo en el puerto 5432 con una base de datos llamada galactic-tournament-api.
- Ejecuta el wrapper de Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
**Endpoints Principales (API REST)**

La API expone los siguientes recursos principales bajo el prefijo `/api/v1/species`:

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `GET` | `/` | Lista todas las especies galácticas registradas. |
| `POST` | `/` | Registra una nueva especie (Requiere JSON con nombre, nivel de poder y habilidad). |
| `POST` | `/combat` | Inicia un combate entre dos especies enviando sus IDs. Registra la victoria. |
| `GET` | `/ranking` | Devuelve el ranking del torneo ordenado descendentemente por victorias. |

## Pruebas Unitarias
Se han implementado pruebas unitarias para garantizar la calidad e integridad de la lógica de negocio y los controladores.
Para ejecutar la suite de pruebas localmente:
   ```bash
   ./mvnw test
   ```
## Integración con Frontend
Este backend está diseñado para ser consumido por el cliente Angular del Torneo Galáctico.
- Repositorio del Frontend: https://github.com/AnthonyDOM164/galactic-repository-ui.git
